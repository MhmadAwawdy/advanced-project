package librarysystem.controllers.HomePage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import librarysystem.models.Librarian;
import librarysystem.utils.SessionManager;
import librarysystem.utils.StageUtil;

import java.io.IOException;
import java.util.Objects;

public class ProfileController
{
    @FXML
    public Label phoneLabel;
    @FXML
    public Label dateLabel;
    @FXML
    public Label roleLabel;
    @FXML
    private ImageView profileImage;
    @FXML
    private Label nameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    public void initialize()
    {

        Librarian librarian = SessionManager.getLoggedInLibrarian();

        if (librarian != null)
        {
            nameLabel.setText( librarian.getUsername());
            roleLabel.setText( librarian.getRole());
            emailLabel.setText( librarian.getEmail());
            phoneLabel.setText( librarian.getPhoneNumber());
            dateLabel.setText(String.valueOf(librarian.getDate()));

            try
            {
                Image image = new Image(Objects.requireNonNull(getClass().getResource("/Image/profile.png")).toExternalForm());
                profileImage.setImage(image);
            }
            catch (Exception e)
            {
                System.out.println("Profile image not found.");
            }
        }
        else
        {
            System.out.println("No logged-in librarian found.");
        }
    }
    public void switchForm(javafx.event.ActionEvent event)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/HomePage/HomePageLibrarians.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Library Reservation System");
            StageUtil.setAppIcon(stage);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void logout(javafx.event.ActionEvent event)
    {
        System.out.println("Logged Out Successfully!");
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Auth/logIn.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("LogIn");
            StageUtil.setAppIcon(stage);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}