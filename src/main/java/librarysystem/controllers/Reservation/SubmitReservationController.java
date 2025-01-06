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
import librarysystem.utils.StageUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class SubmitReservationController
{
    @FXML
    private TextField searchField;
    @FXML
    private ListView<String> studentListView;
    @FXML
    private Button submitButton;
    @FXML
    private DatePicker dueDatePicker;

    private final StudentDAOImp studentDAO = new StudentDAOImp();
    private String bookName;
    private int bookID;

    public void setBookId(int bookId)
    {
        this.bookID = bookId;
        System.out.println("Book Id set: " + bookId);
    }
    public void setBookName(String bookName)
    {
        this.bookName = bookName;
        System.out.println("Book Name set: " + bookName);
    }
    public void switchForm(ActionEvent event)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Book/BookDetailsLibrarian.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Library Reservation System");
            StageUtil.setAppIcon(stage);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void searchStudents(ActionEvent actionEvent)
    {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty())
        {
            studentListView.setItems(FXCollections.observableArrayList("Please enter a name to search."));
            return;
        }
        List<Student> students = studentDAO.searchStudentsByName(keyword);
        if (students.isEmpty())
        {
            studentListView.setItems(FXCollections.observableArrayList("No students found."));
        }
        else
        {
            ObservableList<String> studentDetails = FXCollections.observableArrayList();
            for (Student student : students)
            {
                String studentInfo = "ID: " + student.getStudentID() + " - Name: " + student.getStudentName() + " - Phone: " + student.getStudentPhone();
                studentDetails.add(studentInfo);
            }
            studentListView.setItems(studentDetails);
        }
    }
    public void displayAllStudents()
    {
        List<Student> students = studentDAO.getAllStudents();
        if (students == null || students.isEmpty())
        {
            studentListView.setItems(FXCollections.observableArrayList("No students found."));
        }
        else
        {
            ObservableList<String> studentDetails = FXCollections.observableArrayList();
            for (Student student : students)
            {
                String studentInfo = "ID: " + student.getStudentID() + " - Name: " + student.getStudentName() + " - Phone: " + student.getStudentPhone();
                studentDetails.add(studentInfo);
            }
            studentListView.setItems(studentDetails);
        }
    }
    @FXML
    private void initialize()
    {
        submitButton.setDisable(true);
        studentListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty())
            {
                submitButton.setDisable(false);
            }
            else
            {
                submitButton.setDisable(true);
            }
        });
        displayAllStudents();
    }
    @FXML
    private void submitReservation(ActionEvent actionEvent)
    {
        String selectedStudentInfo = studentListView.getSelectionModel().getSelectedItem();
        if (selectedStudentInfo == null || selectedStudentInfo.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Student Not Selected");
            alert.setContentText("Please select a student to reserve.");
            alert.showAndWait();
            return;
        }
        String[] studentDetails = selectedStudentInfo.split(" - ");
        int studentId = Integer.parseInt(studentDetails[0].split(": ")[1]);
        String studentName = studentDetails[1].split(": ")[1]; // Extract student name

        String bookName = this.bookName;
        if (bookName == null || bookName.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Book Name Not Selected");
            alert.setContentText("Please select a book to reserve.");
            alert.showAndWait();
            return;
        }
        ReservationDAOImp reservationService = new ReservationDAOImp();
        Reservation existingReservation = reservationService.getReservationByBookName(bookName);

        if (existingReservation != null)
        {
            Alert reservedAlert = new Alert(Alert.AlertType.WARNING);
            reservedAlert.setTitle("Book Already Reserved");
            reservedAlert.setHeaderText("This book is already reserved.");
            reservedAlert.setContentText("Please choose another book or check the reservation details.");
            reservedAlert.showAndWait();
            return;
        }
        LocalDate dueDate = dueDatePicker.getValue();
        if (dueDate == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Due Date Missing");
            alert.setContentText("Please select a valid due date.");
            alert.showAndWait();
            return;
        }
        LocalDateTime dueDateTime = dueDate.atStartOfDay();
        LocalDateTime reservationDate = LocalDateTime.now();
        String status = "Reserved";
        boolean extended = false;

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Reservation");
        confirmationAlert.setHeaderText("Are you sure?");
        confirmationAlert.setContentText("Do you want to submit the reservation for:\n" + selectedStudentInfo);

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK)
        {
            System.out.println("Student Selected: " + selectedStudentInfo);
            BookDAO bookDAO = new BookDAOImp();
            Book book = bookDAO.getBookByName(bookName);
            if (book != null)
            {
                book.setStatus(Book.BookStatus.reserved);
                bookDAO.save(book);
            }
            else
            {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Book Not Found");
                errorAlert.setContentText("Please select a valid book.");
                errorAlert.showAndWait();
                return;
            }
            Reservation reservation = new Reservation();
            reservation.setStudentId(studentId);
            reservation.setStudentName(studentName);
            reservation.setBookName(bookName);
            reservation.setBookId(book.getId());
            reservation.setReservationDate(reservationDate);
            reservation.setDueDate(dueDateTime);
            reservation.setStatus(status);
            reservation.setExtended(extended);

            ReservationDAO reservationDAO = new ReservationDAOImp();
            reservationDAO.save(reservation);

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Reservation Successful");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Reservation submitted successfully for:\n" + studentName);
            successAlert.showAndWait();
        }
        else
        {
            Alert cancelAlert = new Alert(Alert.AlertType.INFORMATION);
            cancelAlert.setTitle("Reservation Cancelled");
            cancelAlert.setHeaderText(null);
            cancelAlert.setContentText("Reservation cancelled for:\n" + studentName);
            cancelAlert.showAndWait();
            System.out.println("Reservation cancelled.");
        }
    }
    public void clearFieldsUpdateBook()
    {
        bookName = null;
        bookID = 0;
        searchField.setText(null);
        studentListView.setItems(FXCollections.observableArrayList("Please select a book to reserve."));
        submitButton.setDisable(true);
        dueDatePicker.setValue(null);
        searchField.clear();
        studentListView.getSelectionModel().clearSelection();
        submitButton.setDisable(true);

    }
}