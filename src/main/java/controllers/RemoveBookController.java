package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RemoveBookController {

    @FXML
    private TextField isbnField;

    @FXML
    public void handleRemoveBook() {
        String isbn = isbnField.getText();

        if (!isbn.isEmpty()) {
            System.out.println("Book Removed with ISBN: " + isbn);
        } else {
            System.out.println("ISBN field is required!");
        }
    }
}
