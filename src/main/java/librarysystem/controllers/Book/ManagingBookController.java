package librarysystem.controllers.Book;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import librarysystem.models.Book;
import librarysystem.models.BookStatus;
import librarysystem.models.services.BookDAOImp;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class ManagingBookController {

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
    private Button addBook_Back;
    @FXML
    private Button updateBook_Back;
    @FXML
    private ImageView imageView;
    @FXML
    private Hyperlink uploadImageLink;
    @FXML
    private Label titleErrorText;
    @FXML
    private Label authorErrorText;
    @FXML
    private Label typeErrorText;
    @FXML
    private Label dateErrorText;
    @FXML
    private Label statusErrorText;
    @FXML
    private TextField book_tittle;
    @FXML
    private TextField author_name;
    @FXML
    private TextField book_type;
    @FXML
    private TextField puplication_date;
    @FXML
    private TextField book_status;

    private byte[] bookImage;
    private final BookDAOImp bookDAOImp = new BookDAOImp();

    @FXML
    private void initialize() {
        addButton.setOnAction(event -> handleButtonClick(event));
        cancelButton.setOnAction(event -> handleButtonClick(event));
        uploadImageLink.setOnAction(event -> loadImage());
    }

    private void handleButtonClick(ActionEvent event) {
        String buttonId = ((Button) event.getSource()).getId();
        if ("addButton".equals(buttonId)) {
            addBook();
        } else if ("cancelButton".equals(buttonId)) {
            clearFields();
        }
    }

    @FXML
    private void loadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                bookImage = fileInputStream.readAllBytes();
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);
                fileInputStream.close();
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Image Error", "Failed to load the image.");
            }
        }
    }

    private void addBook() {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String type = typeField.getText().trim();
        String publishDateStr = dateField.getText().trim();
        String statusStr = statusField.getText().trim().toUpperCase();

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

        if (statusStr.isEmpty()) {
            showError(statusField, statusErrorText, "Status is required.");
            valid = false;
        } else {
            try {
                BookStatus.valueOf(statusStr);
                clearError(statusField, statusErrorText);
            } catch (IllegalArgumentException e) {
                showError(statusField, statusErrorText, "Status must be 'AVAILABLE' or 'UNAVAILABLE'.");
                valid = false;
            }
        }

        int publishDate = 0;
        if (publishDateStr.isEmpty()) {
            showError(dateField, dateErrorText, "Publish year is required.");
            valid = false;
        } else {
            try {
                publishDate = Integer.parseInt(publishDateStr);
                if (publishDate < 1000 || publishDate > 9999) {
                    showError(dateField, dateErrorText, "Please enter a valid year.");
                    valid = false;
                } else {
                    clearError(dateField, dateErrorText);
                }
            } catch (NumberFormatException e) {
                showError(dateField, dateErrorText, "Please enter a valid year.");
                valid = false;
            }
        }
        if(bookImage == null) {
            showAlert(Alert.AlertType.ERROR, " Image required.", "Book Image is required.");
            valid = false;
        }

        if (valid) {
            if (bookDAOImp.isBookExists(title, author)) {
                showAlert(Alert.AlertType.WARNING, "Duplicate Book", "Book already exists in the database.");
            } else {
                Book newBook = new Book();
                newBook.setTitle(title);
                newBook.setAuthor(author);
                newBook.setType(type);
                newBook.setPublishDate(publishDate);
                newBook.setStatus(BookStatus.valueOf(statusStr));
                newBook.setImage(bookImage);

                try {
                    bookDAOImp.save(newBook);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Book added successfully!");
                    clearFields();
                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to save the book! " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }


    private void showError(TextField field, Label errorLabel, String errorMessage) {
        if (errorLabel != null) {
            errorLabel.setText(errorMessage);
            errorLabel.setVisible(true);
        }
        field.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
    }

    private void clearError(TextField field, Label errorLabel) {
        if (errorLabel != null) {
            errorLabel.setVisible(false);
        }
        field.setStyle("-fx-border-color: none;");
    }

    private void clearFields() {
        titleField.clear();
        authorField.clear();
        typeField.clear();
        dateField.clear();
        statusField.clear();
        imageView.setImage(null);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == addBook_Back) {
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/HomePage/HomePageLibrarians.fxml")));
                Stage currentStage = (Stage) addBook_Back.getScene().getWindow();
                Scene scene = new Scene(root);
                currentStage.setScene(scene);
                currentStage.setTitle("Library Reservation System");
                currentStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (event.getSource() == updateBook_Back) {
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/HomePage/HomePageLibrarians.fxml")));
                Stage currentStage = (Stage) updateBook_Back.getScene().getWindow();
                Scene scene = new Scene(root);
                currentStage.setScene(scene);
                currentStage.setTitle("Library Reservation System");
                currentStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    private void onClickUpdateBook() {
        String title = book_tittle.getText().trim();
        String author = author_name.getText().trim();
        String type = book_type.getText().trim();
        String publishDateStr = puplication_date.getText().trim();
        String statusStr = book_status.getText().trim().toUpperCase();

        boolean valid = true;

        if (title.isEmpty() || author.isEmpty() || type.isEmpty() || publishDateStr.isEmpty() || statusStr.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "All fields are required.");
            valid = false;
        }

        int publishDate = 0;
        if (valid) {
            try {
                publishDate = Integer.parseInt(publishDateStr);
                if (publishDate < 1000 || publishDate > 9999) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Year", "Please enter a valid year.");
                    valid = false;
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Invalid Year", "Please enter a valid year.");
                valid = false;
            }
        }

        if (valid) {
            try {
                Book bookToUpdate = new Book();
                bookToUpdate.setTitle(title); // سيتم التعرف على الكتاب بناءً على العنوان
                bookToUpdate.setAuthor(author);
                bookToUpdate.setType(type);
                bookToUpdate.setPublishDate(publishDate);
                bookToUpdate.setStatus(BookStatus.valueOf(statusStr));

                boolean isUpdated = bookDAOImp.update(bookToUpdate);
                if (isUpdated) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Book updated successfully!");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to update the book.");
                }
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "An error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}