package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class ProfileController {

    @FXML
    private ImageView profileImage;

    @FXML
    private Label detailsLabel;

    @FXML
    private Button logoutButton;


    @FXML
    public void initialize() {
        try {

            Image image = new Image(getClass().getResource("/images/profile.png").toExternalForm());
            profileImage.setImage(image);


            Circle clip = new Circle(50, 50, 50); // x, y, radius
            profileImage.setClip(clip);

        } catch (NullPointerException e) {
            System.out.println("Error: Profile image not found.");
        }
    }

    @FXML
    private void logout() {
        System.out.println("Logged Out Successfully!");

    }
}
