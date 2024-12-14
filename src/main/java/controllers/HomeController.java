package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class HomeController {

    @FXML
    private Button addBookButton;

    @FXML
    private Button removeBookButton;

    @FXML
    private Button reservationButton;

    @FXML
    private VBox menu;

    @FXML
    private TextField searchField;

    @FXML
    private GridPane booksGrid;

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

    @FXML
    private BorderPane rootPane;
    @FXML
    private Button menuButton;
    @FXML
    private Button loginButton;
    @FXML
    public void initialize() {

        loadBookImages();
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
    public void toggleMenu() {
        boolean isVisible = menu.isVisible();
        menu.setVisible(!isVisible);
        menu.setManaged(!isVisible);
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
