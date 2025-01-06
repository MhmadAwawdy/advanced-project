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
import librarysystem.controllers.HomePage.HomePageLibrariansController;
import librarysystem.models.Reservation;
import librarysystem.models.interfaces.ReservationDAO;
import librarysystem.models.services.ReservationDAOImp;
import librarysystem.utils.StageUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ExtendedReservationController
{
    @FXML
    private DatePicker dueDatePicker;
    @FXML
    private Button extendButton;
    @FXML
    private ListView<Reservation> reservedBooksListView;
    @FXML
    private TextField searchField;

    private final ReservationDAO reservationDAO = new ReservationDAOImp();
    private static int bookId;

    public void setBookid() throws IOException
    {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/HomePage/HomePageLibrarians.fxml"));
        Parent root = loader.load();
        HomePageLibrariansController controller = loader.getController();
        bookId = controller.Bookid;
    }
    @FXML
    public void initialize() throws IOException
    {
        setBookid();
        loadReservedBooks();
        extendButton.setDisable(true);
        reservedBooksListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            enableExtendButton();
        });
    }
    private void loadReservedBooks()
    {
        try
        {
            List<Reservation> allReservedBooks = reservationDAO.getReservedBooks();
            List<Reservation> filteredReservedBooks = allReservedBooks.stream().filter(reservation -> reservation.getBookId() == bookId).collect(Collectors.toList());
            ObservableList<Reservation> items = FXCollections.observableArrayList(filteredReservedBooks);

            reservedBooksListView.setItems(items);
            reservedBooksListView.setCellFactory(param -> new ListCell<Reservation>()
            {
                @Override
                protected void updateItem(Reservation reservation, boolean empty)
                {
                    super.updateItem(reservation, empty);
                    if (empty || reservation == null)
                    {
                        setText(null);
                    }
                    else
                    {
                        setText("Student Name: " + reservation.getStudentName() +
                                " // Status: " + reservation.getStatus() +
                                " // Reservation Date: " + reservation.getReservationDate() +
                                " // Due Date: " + reservation.getDueDate() +
                                " // Book Name: " + reservation.getBookName() +
                                " // Reservation ID: " + reservation.getReservationId() +
                                " // Book ID: " + reservation.getBookId() +
                                " // Student ID : " + reservation.getStudentId());
                    }
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
            showAlert("Error", "Could not load reserved books.");
        }
    }
    private void enableExtendButton()
    {
        extendButton.setDisable(reservedBooksListView.getSelectionModel().getSelectedItem() == null);
    }
    @FXML
    private void extendReservation(ActionEvent event)
    {
        LocalDate selectedDate = dueDatePicker.getValue();
        if (selectedDate == null)
        {
            showAlert("Error", "Please select a new due date.");
            return;
        }
        LocalDateTime newDueDate = selectedDate.atStartOfDay();
        Reservation selectedReservation = reservedBooksListView.getSelectionModel().getSelectedItem();
        if (selectedReservation == null)
        {
            showAlert("Error", "Please select a reservation from the list.");
            return;
        }
        try
        {
            selectedReservation.setDueDate(newDueDate);
            reservationDAO.update(selectedReservation);
            showAlert("Success", "Reservation extended successfully.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            showAlert("Error", "An unexpected error occurred while extending the reservation.");
        }
    }
    @FXML
    public void searchReservations(ActionEvent event)
    {
        String searchQuery = searchField.getText().trim();
        if (searchQuery.isEmpty()){
            showAlert("Error", "Please enter a search query.");
            return;
        }
        try
        {
            List<Reservation> reservations = reservationDAO.searchReservations(searchQuery);
            ObservableList<Reservation> items = FXCollections.observableArrayList(reservations);
            reservedBooksListView.setItems(items);
            if (reservations.isEmpty())
            {
                showAlert("No Results", "No reservations found for your search.");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            showAlert("Error", "An error occurred while searching for reservations.");
        }
    }
    public void switchForm(ActionEvent event)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Book/BookDetailsLibrarian.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Library Reservation System");
            StageUtil.setAppIcon(currentStage);
            currentStage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            showAlert("Error", "Unable to switch forms.");
        }
    }
    private void showAlert(String title, String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}