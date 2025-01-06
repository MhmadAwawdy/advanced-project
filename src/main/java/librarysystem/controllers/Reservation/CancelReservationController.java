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
import librarysystem.controllers.HomePage.HomePageLibrariansController;
import librarysystem.models.Book;
import librarysystem.models.Reservation;
import javafx.event.ActionEvent;
import librarysystem.models.interfaces.ReservationDAO;
import librarysystem.models.services.ReservationDAOImp;
import librarysystem.utils.HibernateUtil;
import librarysystem.utils.StageUtil;
import org.hibernate.Session;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class CancelReservationController {

    @FXML
    private Button cancelReservationButton;

    @FXML
    private ListView<Reservation> reservedBooksListView;
    @FXML
    private ListView<Reservation> rereservedBooksListView;

    private static int bookId;

    public void setBookid() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/HomePage/HomePageLibrarians.fxml"));
        Parent root = loader.load();
        HomePageLibrariansController controller = loader.getController();

        bookId = controller.Bookid;
        System.out.println("Book Id set: " + bookId);
    }

    private final ReservationDAO reservationDAO = new ReservationDAOImp();

    public void loadReservedBooks() {
        try {
            List<Reservation> allReservedBooks = reservationDAO.getReservedBooks();

            List<Reservation> filteredReservedBooks = allReservedBooks.stream()
                    .filter(reservation -> reservation.getBookId() == bookId)
                    .collect(Collectors.toList());

            ObservableList<Reservation> items = FXCollections.observableArrayList(filteredReservedBooks);
            reservedBooksListView.setItems(items);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadReservedBooks() {
        try {
            List<Reservation> reservedBooks = reservedBooksListView.getItems();
            ObservableList<Reservation> items = FXCollections.observableArrayList(reservedBooks);
            rereservedBooksListView.setItems(items);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() throws IOException {
        setBookid();
        cancelReservationButton.setDisable(true);

        reservedBooksListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            cancelReservationButton.setDisable(newValue == null);
        });

        loadReservedBooks();
        List<Reservation> allReservedBooks = reservationDAO.getReservedBooks();

        List<Reservation> filteredReservedBooks = allReservedBooks.stream()
                .filter(reservation -> reservation.getBookId() == bookId)
                .collect(Collectors.toList());

        ObservableList<Reservation> items = FXCollections.observableArrayList(filteredReservedBooks);
        reservedBooksListView.setItems(items);

        reservedBooksListView.setCellFactory(param -> new ListCell<Reservation>() {
            @Override
            protected void updateItem(Reservation reservation, boolean empty) {
                super.updateItem(reservation, empty);
                if (empty || reservation == null) {
                    setText(null);
                } else {
                    setText("Book ID: " + reservation.getBookId() +
                            " | Student: " + reservation.getStudentName() +
                            " | Due Date: " + reservation.getDueDate());
                }
            }
        });
    }

    @FXML
    private void cancelReservation(ActionEvent event) {
        Reservation selectedReservation = reservedBooksListView.getSelectionModel().getSelectedItem();

        if (selectedReservation == null) {
            showAlert("Error", "No reservation selected.");
            return;
        }

        String studentName = selectedReservation.getStudentName();
        String dueDate = selectedReservation.getDueDate().toString();

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Cancel Reservation");
        confirmationAlert.setHeaderText("Are you sure you want to cancel the reservation?");
        confirmationAlert.setContentText("Student: " + studentName + "\nDue Date: " + dueDate);

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                cancelReservationInDatabase(selectedReservation);
            }
        });
    }

    private void cancelReservationInDatabase(Reservation reservation) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            session.createQuery("DELETE FROM Reservation WHERE id = :reservationId")
                    .setParameter("reservationId", reservation.getReservationId())
                    .executeUpdate();

            Book book = session.get(Book.class, reservation.getBookId());
            if (book != null) {
                book.setStatus(Book.BookStatus.available);
                session.update(book);
            }

            session.getTransaction().commit();

            showAlert("Success", "Reservation canceled successfully.");
            loadReservedBooks();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while canceling the reservation.");
        }
    }

    public void switchForm(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Book/BookDetailsLibrarian.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Library Reservation System");
            StageUtil.setAppIcon(currentStage);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to switch forms.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
