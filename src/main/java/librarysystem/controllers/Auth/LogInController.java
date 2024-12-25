package librarysystem.controllers.Auth;

import librarysystem.utils.PasswordEncryption;
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
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class LogInController implements Initializable {
    @FXML
    private Button login_btn;
    @FXML
    private Button login_createAccount;
    @FXML
    private Hyperlink login_forgetPassword;
    @FXML
    private PasswordField login_password;
    @FXML
    private CheckBox login_selectShowPassword;
    @FXML
    private TextField login_showPassword;
    @FXML
    private TextField login_username;
    @FXML
    private Label login_errorMessage;
    @FXML
    private Button login_Back;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;


    public Connection connectDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect;
            connect = DriverManager.getConnection("jdbc:mysql://108.181.157.249:10055/librarysystem", "admin", "JngGL3fp");
            return connect;
        } catch (Exception e) {
            System.out.println("Error connecting with Database");
            e.printStackTrace();
        }
        return null;
    }

    public void login()
    {
        login_errorMessage.setVisible(false);
        login_errorMessage.setText("");

        if (login_username.getText().isEmpty() || login_password.getText().isEmpty()) {
            login_errorMessage.setText("Please enter username and password.");
            login_errorMessage.setVisible(true);
        } else {
            String selectData = "SELECT password FROM users WHERE username = ?";
            connect = connectDB();
            try {
                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, login_username.getText());
                result = prepare.executeQuery();
                if (result.next()) {
                    String storedHashedPassword = result.getString("password");
                    String enteredHashedPassword = PasswordEncryption.hashPassword(login_password.getText());
                    if (storedHashedPassword.equals(enteredHashedPassword)) {
                        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/HomePage/HomePageLibrarians.fxml")));
                        Stage stage = new Stage();
                        stage.setTitle("Home Library System");
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
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
          } else if (event.getSource() == login_createAccount) {
              try {
                  Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Auth/signUp.fxml")));
                  Stage currentStage = (Stage) login_createAccount.getScene().getWindow();
                  Scene scene = new Scene(root);
                  currentStage.setScene(scene);
                  currentStage.setTitle("Sign Up Account");
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> login_btn.requestFocus());
    }
}