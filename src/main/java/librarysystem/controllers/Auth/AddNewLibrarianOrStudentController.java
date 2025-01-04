package librarysystem.controllers.Auth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import librarysystem.models.Librarian;
import librarysystem.models.Student;
import librarysystem.models.services.LibrarianDAOimp;
import librarysystem.models.services.StudentDAOImp;
import librarysystem.utils.PasswordEncryption;
import librarysystem.utils.SessionManager;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddNewLibrarianOrStudentController implements Initializable {

    @FXML private Button newClient_Back;
    @FXML private Button newLibrarian_Back;
    @FXML private TextField signup_email;
    @FXML private TextField signup_username;
    @FXML private PasswordField signup_password;
    @FXML private PasswordField signup_cPassword;
    @FXML private TextField signup_phone;
    @FXML private Button signup_loginAccount;
    @FXML private Label signup_errorMessage;
    @FXML private TextField studentNameField;
    @FXML private TextField studentPhoneField;
    @FXML private Label errorMessageLabel;

    private final LibrarianDAOimp librarianDAOimp;
    private final StudentDAOImp studentDAOImp;

    public AddNewLibrarianOrStudentController() {
        this.librarianDAOimp = new LibrarianDAOimp();
        this.studentDAOImp = new StudentDAOImp();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Check the role of the logged-in user
        Librarian loggedInUser = SessionManager.getLoggedInLibrarian();
        if (loggedInUser == null || !"Admin".equalsIgnoreCase(loggedInUser.getRole())) {
            // Disable or hide the registration-related controls for librarians
            signup_email.setDisable(true);
            signup_username.setDisable(true);
            signup_password.setDisable(true);
            signup_cPassword.setDisable(true);
            signup_phone.setDisable(true);
            signup_loginAccount.setDisable(true);
        }
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == newClient_Back) {
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/HomePage/HomePageLibrarians.fxml")));
                Stage currentStage = (Stage) newClient_Back.getScene().getWindow();
                Scene scene = new Scene(root);
                currentStage.setScene(scene);
                currentStage.setTitle("Library Reservation System");
                currentStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (event.getSource() == newLibrarian_Back) {
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/HomePage/HomePageLibrarians.fxml")));
                Stage currentStage = (Stage) newLibrarian_Back.getScene().getWindow();
                Scene scene = new Scene(root);
                currentStage.setScene(scene);
                currentStage.setTitle("Library Reservation System");
                currentStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (event.getSource() == signup_loginAccount) {
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Auth/logIn.fxml")));
                Stage currentStage = (Stage) signup_loginAccount.getScene().getWindow();
                Scene scene = new Scene(root);
                currentStage.setScene(scene);
                currentStage.setTitle("Library Reservation System");
                currentStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void savestudent() {
        errorMessageLabel.setVisible(false);
        if (!validateStudentInput()) {
            return;
        }
        if (studentDAOImp.findByPhone(studentPhoneField.getText()) != null) {
            displayErrorStudent("This phone number is already registered.");
            return;
        }
        Student student = new Student();
        student.setStudentName(studentNameField.getText().trim());
        student.setStudentPhone(studentPhoneField.getText().trim());

        studentDAOImp.save(student);
        clearFieldsStudent();
        errorMessageLabel.setText("Student registered successfully!");
        errorMessageLabel.setTextFill(Paint.valueOf("green"));
        errorMessageLabel.setVisible(true);
    }

    private boolean validateStudentInput() {
        if (studentNameField.getText().isEmpty() || studentPhoneField.getText().isEmpty()) {
            displayErrorStudent("Both fields are required.");
            return false;
        }
        if (!studentPhoneField.getText().matches("\\d{10,15}")) {
            displayErrorStudent("Invalid phone number. It should be 10 to 15 digits long.");
            return false;
        }
        return true;
    }

    private void displayErrorStudent(String message) {
        errorMessageLabel.setText(message);
        errorMessageLabel.setTextFill(Paint.valueOf("red"));
        errorMessageLabel.setVisible(true);
    }

    private void clearFieldsStudent() {
        studentNameField.clear();
        studentPhoneField.clear();
    }

    @FXML
    public void register() {
        signup_errorMessage.setVisible(false);
        if (!validateInput()) {
            return;
        }
        try {
            if (librarianDAOimp.findByUsername(signup_username.getText()) != null) {
                displayError(signup_username.getText() + " is already taken");
                return;
            }
            Librarian user = new Librarian();
            user.setEmail(signup_email.getText());
            user.setUsername(signup_username.getText());
            user.setPhoneNumber(signup_phone.getText());
            user.setPassword(PasswordEncryption.hashPassword(signup_password.getText()));
            user.setRole("Librarian");
            user.setDate(new java.sql.Date(System.currentTimeMillis()));

            librarianDAOimp.save(user);

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
}