package librarysystem.controllers.Book;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Objects;

public class ManagingBookController {

    @FXML
    private TextField bookTitleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField isbnField;
    @FXML
    private Button addBook_Back;
    @FXML
    private Button updateBook_Back;

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
}