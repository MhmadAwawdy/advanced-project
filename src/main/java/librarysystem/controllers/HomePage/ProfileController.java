package librarysystem.controllers.HomePage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfileController
{
    @FXML
    private ImageView profileImage;
    @FXML
    private Label detailsLabel;
    @FXML
    private Button logoutButton;

    @FXML
    public void initialize() {
        try {
            Image image = new Image(getClass().getResource("/Image/profile.png").toExternalForm());
            profileImage.setImage(image);
            Circle clip = new Circle(profileImage.getFitWidth() / 2, profileImage.getFitHeight() / 2, Math.min(profileImage.getFitWidth(), profileImage.getFitHeight()) / 2);
            profileImage.setClip(clip);
        } catch (NullPointerException e) {
            System.out.println("Error: Profile image not found.");
        }
    }

    @FXML
    private void logout(ActionEvent event) {
        System.out.println("Logged Out Successfully!");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Auth/logIn.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("LogIn");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchForm(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/HomePage/HomePageLibrarians.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Library Reservation System");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
