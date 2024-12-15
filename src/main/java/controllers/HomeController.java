package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;

public class HomeController {

    @FXML
    private Button addBookButton;

    @FXML
    private Button removeBookButton;

    @FXML
    private Button reservationButton;


    @FXML
    private TextField searchField;


    @FXML
    private ImageView book1Image;

    @FXML
    private ImageView book2Image;

    @FXML
    private ImageView book3Image;
    @FXML
    private ImageView book4Image;

    @FXML
    private ImageView book5Image;

    @FXML
    private ImageView book6Image;


    private Button loginButton;


    @FXML
    private ImageView logoImage;
    @FXML
    private ImageView profileImage;

    @FXML
    public void initialize() {
        loadBookImages();
        loadLogo();  InputStream imageStream = getClass().getResourceAsStream("/images/UserIcon.png");
        if (imageStream != null) {
            Image image = new Image(imageStream);
            profileImage.setImage(image);
        } else {
            System.out.println("Error: UserIcon.png not found in /images folder.");
        }
        Image image = new Image(getClass().getResourceAsStream("/images/UserIcon.png"));


        profileImage.setImage(image);

    }



    private void loadLogo() {
        try {
            logoImage.setImage(new Image(getClass().getResource("/images/logo.PNG").toExternalForm()));
        } catch (NullPointerException e) {
            System.out.println("Error: Logo image not found.");
        }
    }

    @FXML
    private void openProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ProfilePage.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Librarian Profile");
            stage.setScene(new Scene(root, 1000, 750));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private void loadBookImages() {
        try {
            book1Image.setImage(new Image(getClass().getResource("/images/book1.jpg").toExternalForm()));
            book2Image.setImage(new Image(getClass().getResource("/images/book2.jpg").toExternalForm()));
            book3Image.setImage(new Image(getClass().getResource("/images/book3.jpg").toExternalForm()));
            book4Image.setImage(new Image(getClass().getResource("/images/book4.jpg").toExternalForm()));
            book5Image.setImage(new Image(getClass().getResource("/images/book5.jpg").toExternalForm()));
            book6Image.setImage(new Image(getClass().getResource("/images/book6.jpg").toExternalForm()));
        } catch (NullPointerException e) {
            System.out.println("Error: Book images not found.");
        }
    }



    @FXML
    public void navigateToAddBook() {
        System.out.println("Navigating to Add Book Page");
    }

    @FXML
    public void navigateToRemoveBook() {
        System.out.println("Navigating to Remove Book Page");
    }

    @FXML
    public void navigateToReservation() {
        System.out.println("Navigating to Reservations Page");
    }
}
