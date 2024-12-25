package librarysystem.controllers.HomePage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePageClinteController {
    @FXML
    private ImageView imageView;

    @FXML
    private void handleImageClick(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Client/bookDetailsClient.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Book Details");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleMouseEnter(MouseEvent event) {
        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(5.0);
        shadow.setOffsetY(5.0);
        shadow.setRadius(10.0);
        shadow.setColor(javafx.scene.paint.Color.GRAY);
        imageView.setEffect(shadow);
    }

    @FXML
    private void handleMouseExit(MouseEvent event)
    {
        imageView.setEffect(null);
    }

    public void GoBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Auth/WelcomePage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Library Reservation System");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}