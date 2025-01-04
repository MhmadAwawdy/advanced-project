package librarysystem.controllers.Reservation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import librarysystem.models.Book;
import librarysystem.models.Reservation;
import javafx.event.ActionEvent;
import librarysystem.models.interfaces.ReservationDAO;
import librarysystem.models.services.ReservationDAOImp;
import librarysystem.utils.HibernateUtil;
import org.hibernate.Session;

import java.io.IOException;
import java.util.List;

public class CancelReservationController {

    @FXML
    private Button cancelReservationButton;

    @FXML
    private ListView<Reservation> reservedBooksListView;

    private final ReservationDAO reservationDAO = new ReservationDAOImp();

    // Method to load reserved books from the database
    public void loadReservedBooks() {
        try {
            List<Reservation> reservedBooks = reservationDAO.getReservedBooks();
            ObservableList<Reservation> items = FXCollections.observableArrayList(reservedBooks);
            reservedBooksListView.setItems(items);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        // Step 1: Disable the cancel button initially
        cancelReservationButton.setDisable(true);

        // Step 2: Enable the cancel button when a reservation is selected
        reservedBooksListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            cancelReservationButton.setDisable(newValue == null);
        });

        // Step 3: Load the reserved books into the ListView
        loadReservedBooks();

        // Optional: Customize how reservations are displayed in the ListView
        reservedBooksListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Reservation reservation, boolean empty) {
                super.updateItem(reservation, empty);
                if (empty || reservation == null) {
                    setText(null);
                } else {
                    // Customize the display text for each reservation item
                    setText("Book ID: " + reservation.getBookId() +
                            " | Student: " + reservation.getStudentName() +
                            " | Due Date: " + reservation.getDueDate());
                }
            }
        });
    }


    @FXML
    private void cancelReservation(ActionEvent event) {
        // Get the selected reservation
        Reservation selectedReservation = reservedBooksListView.getSelectionModel().getSelectedItem();

        if (selectedReservation == null) {
            showAlert("Error", "No reservation selected.");
            return;
        }

        // Fetch the student name and due date from the selected reservation
        String studentName = selectedReservation.getStudentName(); // Assuming you have a getter for the student name
        String dueDate = selectedReservation.getDueDate().toString();

        // Show a confirmation pop-up
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Cancel Reservation");
        confirmationAlert.setHeaderText("Are you sure you want to cancel the reservation?");
        confirmationAlert.setContentText("Student: " + studentName + "\nDue Date: " + dueDate);

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Proceed with the cancellation
                cancelReservationInDatabase(selectedReservation);
            }
        });
    }

    private void cancelReservationInDatabase(Reservation reservation) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            // 1. Delete the reservation from the database
            session.createQuery("DELETE FROM Reservation WHERE id = :reservationId")
                    .setParameter("reservationId", reservation.getReservationId())
                    .executeUpdate();

            // 2. Set the book status to "available"
            Book book = session.get(Book.class, reservation.getBookId());
            if (book != null) {
                book.setStatus(Book.BookStatus.available);  // Set the book's status to available
                session.update(book);
            }

            session.getTransaction().commit();

            // Show success message and refresh the reserved books list
            showAlert("Success", "Reservation canceled successfully.");
            loadReservedBooks();  // Reload the list after cancellation
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while canceling the reservation.");
        }
    }

    // Switch to the previous form or another form (Book Details page in this case)
    public void switchForm(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Book/BookDetailsLibrarian.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Library Reservation System");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to switch forms.");
        }
    }

    // Utility method to show alerts to the user
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
