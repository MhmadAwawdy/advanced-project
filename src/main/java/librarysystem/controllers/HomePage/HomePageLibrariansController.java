package librarysystem.controllers.HomePage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class HomePageLibrariansController {
    @FXML
    private Button addBookButton;
    @FXML
    private Button updateBookButton;
    @FXML
    private Button removeBookButton;
    @FXML
    private Button bookOrderButton;
    @FXML
    private Button reservedBookButton;
    @FXML
    private Button addClientButton;
    @FXML
    private ImageView userIcon;
    @FXML
    private ImageView bookImage;

    @FXML
    private void initialize() {
        userIcon.setOnMouseClicked(event -> openProfile());
    }

    @FXML
    private void openProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/HomePage/ProfilePage.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) userIcon.getScene().getWindow();
            currentStage.setScene(new Scene(root, 1000, 750));
            currentStage.setTitle("Librarian Profile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void navigateToAddBook() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Book/addBook.fxml")));
            Stage currentStage = (Stage) addBookButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Add Book");
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void navigateToUpdateBook() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Book/update-book.fxml")));
            Stage currentStage = (Stage) updateBookButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Update Book");
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void navigateToRemoveBook() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Book/RemoveBookPage.fxml")));
            Stage currentStage = (Stage) removeBookButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Remove Book");
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void navigateToBookOrder() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/HomePage/BookReservedAndOrdered.fxml")));
            Stage currentStage = (Stage) bookOrderButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Order Book");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void navigateToReservedBookButton() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/HomePage/ReservedBooks.fxml")));
            Stage currentStage = (Stage) reservedBookButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Reserved books");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void navigateToAddClient() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/HomePage/AddNewClient.fxml")));
            Stage currentStage = (Stage) addClientButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Add Client");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBookImageClick(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Book/BookDetails.fxml")));
            Stage currentStage = (Stage) bookImage.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Book Details");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}