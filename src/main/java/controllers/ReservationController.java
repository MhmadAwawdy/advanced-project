package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ReservationController {

    @FXML
    private TextField bookTitleField;

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
}
