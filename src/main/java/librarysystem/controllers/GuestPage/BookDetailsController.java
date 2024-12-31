package librarysystem.controllers.GuestPage;

import librarysystem.models.Book2;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class BookDetailsController {

    @FXML
    private Label bookTitleLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Label publishDateLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private ImageView bookImageView;

    private Book2 selectedBook;

    public void setBookDetails(Book2 book) {
        this.selectedBook = book;
        displayBookDetails();
    }

    private void displayBookDetails() {
        if (selectedBook != null) {
            bookTitleLabel.setText(selectedBook.getTitle());
            authorLabel.setText(selectedBook.getAuthor());
            publishDateLabel.setText(selectedBook.getPublishDate());
            statusLabel.setText(selectedBook.getType());

            if (selectedBook.getImage() != null && !selectedBook.getImage().isEmpty()) {
                try {
                    Image image = new Image(selectedBook.getImage());
                    bookImageView.setImage(image);
                } catch (Exception e) {
                    bookImageView.setImage(null);
                    System.out.println("Failed to load image: " + e.getMessage());
                }
            } else {
                bookImageView.setImage(null);
            }
        }
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GuestPage.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
