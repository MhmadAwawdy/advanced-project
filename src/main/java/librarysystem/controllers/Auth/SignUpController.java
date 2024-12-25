package librarysystem.controllers.Auth;

import librarysystem.utils.PasswordEncryption;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import librarysystem.models.User;
import librarysystem.models.services.UserDAOimp;

import java.io.IOException;

public class SignUpController {

    @FXML
    private TextField signup_email;
    @FXML
    private TextField signup_username;
    @FXML
    private PasswordField signup_password;
    @FXML
    private PasswordField signup_cPassword;
    @FXML
    private TextField signup_phone;
    @FXML
    private Button signup_loginAccount;
    @FXML
    private Label signup_errorMessage;

    private final UserDAOimp userDAOImp;

    public SignUpController() {
        this.userDAOImp = new UserDAOimp();
    }

    @FXML
    public void register() {
        signup_errorMessage.setVisible(false);
        if (!validateInput()) {
            return; }
        try {
            if (userDAOImp.findByUsername(signup_username.getText()) != null) {
                displayError(signup_username.getText() + " is already taken");
                return;
            }

            User user = new User();
            user.setEmail(signup_email.getText());
            user.setUsername(signup_username.getText());
            user.setPhoneNumber(signup_phone.getText());
            user.setPassword(PasswordEncryption.hashPassword(signup_password.getText()));
            user.setDate(new java.sql.Date(System.currentTimeMillis()));

            userDAOImp.save(user);

            signup_errorMessage.setText("Registered Successfully!");
            signup_errorMessage.setTextFill(Paint.valueOf("green"));
            signup_errorMessage.setVisible(true);
            clearFields();

        } catch (Exception e) {
            e.printStackTrace();
            displayError("An error occurred while registering. Please try again.");
        }
    }

    private boolean validateInput() {
        if (signup_email.getText().isEmpty() || signup_username.getText().isEmpty() || signup_phone.getText().isEmpty()
                || signup_password.getText().isEmpty() || signup_cPassword.getText().isEmpty()) {
            displayError("All fields are necessary to be filled");
            return false;
        }

        if (!signup_password.getText().equals(signup_cPassword.getText())) {
            displayError("Passwords do not match");
            return false;
        }

        if (signup_password.getText().length() < 8) {
            displayError("Password must be at least 8 characters");
            return false;
        }

        if (!signup_phone.getText().matches("\\d{10,15}")) {
            displayError("Invalid phone number");
            return false;
        }

        return true;
    }

    private void displayError(String message) {
        signup_errorMessage.setText(message);
        signup_errorMessage.setTextFill(Paint.valueOf("red"));
        signup_errorMessage.setVisible(true);
    }

    private void clearFields() {
        signup_email.clear();
        signup_username.clear();
        signup_password.clear();
        signup_phone.clear();
        signup_cPassword.clear();
    }

    @FXML
    public void switchForm(ActionEvent event) throws IOException {
        Stage stage = (Stage) signup_loginAccount.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Auth/logIn.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Login");
        stage.show();
    }
}
