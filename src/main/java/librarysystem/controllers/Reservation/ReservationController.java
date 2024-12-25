package librarysystem.controllers.Reservation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Objects;

public class ReservationController {
    @FXML
    private TextField bookTitleField;
    @FXML
    private Button reserveBook_Back;
    @FXML
    private TextField userField;

    @FXML
    public void handleReservation() {
        String bookTitle = bookTitleField.getText();
        String user = userField.getText();

        if (!bookTitle.isEmpty() && !user.isEmpty()) {
            System.out.println("Reservation Made for: " + bookTitle + " by " + user);
        } else {
            System.out.println("All fields are required!");
        }
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == reserveBook_Back) {
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Client/HomePageClinte.fxml")));
                Stage currentStage = (Stage) reserveBook_Back.getScene().getWindow();
                Scene scene = new Scene(root);
                currentStage.setScene(scene);
                currentStage.setTitle("Library Reservation System");
                currentStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
