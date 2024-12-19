package com.example.advanceproject;

import com.example.advanceproject.models.Book;
import com.example.advanceproject.models.services.BookDAOImp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class AddBookController {

    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField typeField;
    @FXML
    private TextField dateField;
    @FXML
    private TextField statusField;
    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Text titleErrorText;
    @FXML
    private Text authorErrorText;
    @FXML
    private Text typeErrorText;
    @FXML
    private Text dateErrorText;
    @FXML
    private Text statusErrorText;

    private BookDAOImp bookDAOImp;

    public AddBookController() {
        bookDAOImp = new BookDAOImp(); // تهيئة BookDAOImp
    }

    @FXML
    public void initialize() {

        addButton.setOnAction(event -> handleButtonClick(addButton.getId()));
        cancelButton.setOnAction(event -> handleButtonClick(cancelButton.getId()));
    }


    private void handleButtonClick(String buttonId) {
        if ("addButton".equals(buttonId)) {
            addBook();
        } else if ("cancelButton".equals(buttonId)) {
            clearFields();
        }
    }

    private void addBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        String type = typeField.getText();
        String publishDateStr = dateField.getText();
        String status = statusField.getText();

        boolean valid = true;

        if (title.isEmpty()) {
            showError(titleField, titleErrorText, "Title is required.");
            valid = false;
        } else {
            clearError(titleField, titleErrorText);
        }

        if (author.isEmpty()) {
            showError(authorField, authorErrorText, "Author is required.");
            valid = false;
        } else {
            clearError(authorField, authorErrorText);
        }

        if (type.isEmpty()) {
            showError(typeField, typeErrorText, "Type is required.");
            valid = false;
        } else {
            clearError(typeField, typeErrorText);
        }

        if (status.isEmpty()) {
            showError(statusField, statusErrorText, "Status is required.");
            valid = false;
        } else {
            clearError(statusField, statusErrorText);
        }

        int publishDate = 0;
        if (publishDateStr.isEmpty()) {
            showError(dateField, dateErrorText, "Publish year is required.");
            valid = false;
        } else {
            try {
                publishDate = Integer.parseInt(publishDateStr);
                if (publishDate < 1000 || publishDate > 9999) {
                    showError(dateField, dateErrorText, "Please enter a valid year (e.g. 2024).");
                    valid = false;
                } else {
                    clearError(dateField, dateErrorText);
                }
            } catch (NumberFormatException e) {
                showError(dateField, dateErrorText, "Please enter a valid year.");
                valid = false;
            }
        }


        if (valid) {
            Book newBook = new Book();
            newBook.setTitle(title);
            newBook.setAuthor(author);
            newBook.setType(type);
            newBook.setPublishDate(publishDate);
            newBook.setStatus(status);

            try {
                bookDAOImp.save(newBook);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Book added successfully!");
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to save the book. Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void showError(TextField field, Text errorText, String errorMessage) {
        errorText.setText(errorMessage);
        errorText.setVisible(true);
        field.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
    }


    private void clearError(TextField field, Text errorText) {
        errorText.setVisible(false);
        field.setStyle("-fx-border-color: none;");
    }

    private void clearFields() {
        titleField.clear();
        authorField.clear();
        typeField.clear();
        dateField.clear();
        statusField.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
