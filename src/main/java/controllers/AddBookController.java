package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddBookController {

    @FXML
    private TextField bookTitleField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField isbnField;

    @FXML
    public void handleAddBook() {
        String bookTitle = bookTitleField.getText();
        String author = authorField.getText();
        String isbn = isbnField.getText();

        if (!bookTitle.isEmpty() && !author.isEmpty() && !isbn.isEmpty()) {
            System.out.println("Book Added: " + bookTitle + " by " + author + " (ISBN: " + isbn + ")");
        } else {
            System.out.println("All fields are required!");
        }
    }
}
