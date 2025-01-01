package librarysystem.controllers.Auth;

import librarysystem.models.Librarian;
import librarysystem.models.services.LibrarianDAOimp;
import librarysystem.utils.PasswordEncryption;
import librarysystem.utils.SessionManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LogInController implements Initializable {

    @FXML private TextField login_username;
    @FXML private PasswordField login_password;
    @FXML private CheckBox login_selectShowPassword;
    @FXML private TextField login_showPassword;
    @FXML private Button login_btn;
    @FXML private Hyperlink login_forgetPassword;
    @FXML private Label login_errorMessage;
    @FXML private Button login_Back;

    private final LibrarianDAOimp librarianDAOimp;

    public LogInController() {
        this.librarianDAOimp = new LibrarianDAOimp();
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == login_forgetPassword) {
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Auth/forgotPassword.fxml")));
                Stage currentStage = (Stage) login_forgetPassword.getScene().getWindow();
                Scene scene = new Scene(root);
                currentStage.setScene(scene);
                currentStage.setTitle("Forgot Password");
                currentStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == login_Back) {
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Auth/WelcomePage.fxml")));
                Stage currentStage = (Stage) login_Back.getScene().getWindow();
                Scene scene = new Scene(root);
                currentStage.setScene(scene);
                currentStage.setTitle("Library Reservation System");
                currentStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void login() {
        login_errorMessage.setVisible(false);
        login_errorMessage.setText("");

        if (login_username.getText().isEmpty() || login_password.getText().isEmpty()) {
            login_errorMessage.setText("Please enter username and password.");
            login_errorMessage.setVisible(true);
        } else {
            try {
                Librarian librarian = librarianDAOimp.findByUsername(login_username.getText());
                if (librarian != null) {
                    String storedHashedPassword = librarian.getPassword();
                    String enteredHashedPassword = PasswordEncryption.hashPassword(login_password.getText());

                    if (storedHashedPassword.equals(enteredHashedPassword)) {
                        // Set the logged-in user in SessionManager
                        SessionManager.setLoggedInLibrarian(librarian);

                        // Load the appropriate home page
                        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/HomePage/HomePageLibrarians.fxml")));
                        Stage stage = new Stage();
                        stage.setTitle("Home Library System");
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();

                        // Close the login window
                        login_btn.getScene().getWindow().hide();
                    } else {
                        login_errorMessage.setText("Incorrect username or password.");
                        login_errorMessage.setVisible(true);
                    }
                } else {
                    login_errorMessage.setText("Incorrect username or password.");
                    login_errorMessage.setVisible(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                login_errorMessage.setText("An error occurred during login. Please try again.");
                login_errorMessage.setVisible(true);
            }
        }
    }

    public void showPassword() {
        if (login_selectShowPassword.isSelected()) {
            login_showPassword.setText(login_password.getText());
            login_showPassword.setVisible(true);
            login_password.setVisible(false);
        } else {
            login_showPassword.setText(login_showPassword.getText());
            login_showPassword.setVisible(false);
            login_password.setVisible(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> login_btn.requestFocus());
    }
}
