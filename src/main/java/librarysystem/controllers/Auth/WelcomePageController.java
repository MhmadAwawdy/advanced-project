package librarysystem.controllers.Auth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import librarysystem.utils.StageUtil;

import java.io.IOException;
import java.util.Objects;

public class WelcomePageController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void Guestbtn(ActionEvent event) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Client/HomePageClient.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setTitle("Library Reservation System");
            StageUtil.setAppIcon(stage);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void Librariansbtn(ActionEvent event) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Auth/logIn.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setTitle("LogIn");
            StageUtil.setAppIcon(stage);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
