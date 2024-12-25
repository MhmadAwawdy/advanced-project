package librarysystem.controllers.Auth;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class ResetPasswordController {
@FXML
private Button BackResetPassword;

    @FXML
    void handleBackClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Auth/forgotPassword.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BackResetPassword.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(" Forgot Password ");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
