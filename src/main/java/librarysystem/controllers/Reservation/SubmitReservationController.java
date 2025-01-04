package librarysystem.controllers.Reservation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import librarysystem.models.Book;
import librarysystem.models.Reservation;
import librarysystem.models.Student;
import librarysystem.models.interfaces.BookDAO;
import librarysystem.models.interfaces.ReservationDAO;
import librarysystem.models.services.BookDAOImp;
import librarysystem.models.services.ReservationDAOImp;
import librarysystem.models.services.StudentDAOImp;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class SubmitReservationController {

    @FXML
    private TextField searchField;

    @FXML
    private ListView<String> studentListView;

    @FXML
    private Button submitButton; // Reference to Submit button

    @FXML
    private DatePicker dueDatePicker;  // Reference to the DatePicker for due date

    private final StudentDAOImp studentDAO = new StudentDAOImp();
    private String bookName;  // Declare bookName here

    private int bookID;  // Declare bookName here

    // Method to set the book name when passed from another controller
    public void setBookId(int bookId) {
        this.bookID = bookId;
        System.out.println("Book Id set: " + bookId);
    }
    // Method to set the book name when passed from another controller
    public void setBookName(String bookName) {
        this.bookName = bookName;
        System.out.println("Book Name set: " + bookName);


    }

    // Method to switch forms (navigate to a different page)
    public void switchForm(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Book/BookDetailsLibrarian.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Library Reservation System");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to search for students by name
    public void searchStudents(ActionEvent actionEvent) {
        String keyword = searchField.getText().trim();

        // Validate input
        if (keyword.isEmpty()) {
            studentListView.setItems(FXCollections.observableArrayList("Please enter a name to search."));
            return;
        }

        // Search for students
        List<Student> students = studentDAO.searchStudentsByName(keyword);

        // Check if students are found
        if (students.isEmpty()) {
            studentListView.setItems(FXCollections.observableArrayList("No students found."));
        } else {
            // Populate the ListView with student details (ID, Name, Phone)
            ObservableList<String> studentDetails = FXCollections.observableArrayList();
            for (Student student : students) {
                String studentInfo = "ID: " + student.getStudentID() + " - Name: " + student.getStudentName() + " - Phone: " + student.getStudentPhone();
                studentDetails.add(studentInfo);
            }
            studentListView.setItems(studentDetails);
        }
    }

    // Method to fetch and display all students
    public void displayAllStudents() {
        // Fetch all students from the database
        List<Student> students = studentDAO.getAllStudents();

        // Check if students are found
        if (students == null || students.isEmpty()) {
            studentListView.setItems(FXCollections.observableArrayList("No students found."));
        } else {
            // Populate the ListView with student details (ID, Name, Phone)
            ObservableList<String> studentDetails = FXCollections.observableArrayList();
            for (Student student : students) {
                String studentInfo = "ID: " + student.getStudentID() + " - Name: " + student.getStudentName() + " - Phone: " + student.getStudentPhone();
                studentDetails.add(studentInfo);
            }
            studentListView.setItems(studentDetails);
        }
    }

    @FXML
    private void initialize() {
        // Initially disable the Submit button
        submitButton.setDisable(true);

        // Enable the Submit button when a student is selected from the ListView
        studentListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                submitButton.setDisable(false); // Enable the Submit button
            } else {
                submitButton.setDisable(true); // Disable the Submit button if no student is selected
            }
        });

        // Load all students when the form is initialized
        displayAllStudents();
    }

    @FXML
    private void submitReservation(ActionEvent actionEvent) {
        // Get selected student info
        String selectedStudentInfo = studentListView.getSelectionModel().getSelectedItem();

        if (selectedStudentInfo == null || selectedStudentInfo.isEmpty()) {
            System.out.println("Error: No student selected.");
            return; // Stop execution if no student is selected
        }

        // Extract student details
        String[] studentDetails = selectedStudentInfo.split(" - ");
        int studentId = Integer.parseInt(studentDetails[0].split(": ")[1]);
        String studentName = studentDetails[1].split(": ")[1]; // Extract student name

        // Ensure bookName is set correctly
        String bookName = this.bookName;
        if (bookName == null || bookName.isEmpty()) {
            System.out.println("Error: Book name is null or empty.");
            return; // Stop execution if book name is missing
        }

        // Check if the book is already reserved
        ReservationDAOImp reservationService = new ReservationDAOImp();
        Reservation existingReservation = reservationService.getReservationByBookName(bookName);

        if (existingReservation != null) {
            // Show alert if the book is already reserved
            Alert reservedAlert = new Alert(Alert.AlertType.WARNING);
            reservedAlert.setTitle("Book Already Reserved");
            reservedAlert.setHeaderText("This book is already reserved.");
            reservedAlert.setContentText("Please choose another book or check the reservation details.");
            reservedAlert.showAndWait();
            return; // Stop further execution
        }

        // Get the selected due date
        LocalDate dueDate = dueDatePicker.getValue();
        if (dueDate == null) {
            // Show alert if no due date is selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Due Date Missing");
            alert.setContentText("Please select a valid due date.");
            alert.showAndWait();
            return;
        }

        LocalDateTime dueDateTime = dueDate.atStartOfDay(); // Set due date time to start of day
        LocalDateTime reservationDate = LocalDateTime.now(); // Current date and time
        String status = "Reserved"; // Default reservation status
        boolean extended = false; // Extension flag

        // Show confirmation alert
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Reservation");
        confirmationAlert.setHeaderText("Are you sure?");
        confirmationAlert.setContentText("Do you want to submit the reservation for:\n" + selectedStudentInfo);

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User confirmed the reservation
            System.out.println("Student Selected: " + selectedStudentInfo);

            // Update book status to RESERVED
            BookDAO bookDAO = new BookDAOImp();
            Book book = bookDAO.getBookByName(bookName); // Fetch the book by its name
            if (book != null) {
                book.setStatus(Book.BookStatus.reserved); // Update book status
                bookDAO.save(book); // Save the updated book
            } else {
                System.out.println("Error: Book not found.");
                return; // Stop if the book doesn't exist
            }

            // Create a new Reservation object
            Reservation reservation = new Reservation();
            reservation.setStudentId(studentId);
            reservation.setStudentName(studentName); // Store student name
            reservation.setBookName(bookName); // Set book name
            reservation.setBookId(book.getId()); // Fetch the book ID
            reservation.setReservationDate(reservationDate);
            reservation.setDueDate(dueDateTime);
            reservation.setStatus(status);
            reservation.setExtended(extended);

            // Save the reservation
            ReservationDAO reservationDAO = new ReservationDAOImp();
            reservationDAO.save(reservation);

            // Show success alert
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Reservation Successful");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Reservation submitted successfully for:\n" + studentName);
            successAlert.showAndWait();

        } else {
            // User cancelled the reservation
            System.out.println("Reservation cancelled.");
        }
    }

}
