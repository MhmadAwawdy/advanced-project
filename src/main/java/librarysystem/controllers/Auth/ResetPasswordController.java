package librarysystem.controllers.Auth;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import librarysystem.utils.HibernateUtil;
import librarysystem.utils.PasswordEncryption;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;

public class ResetPasswordController {
    @FXML
    private Button BackResetPassword;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label resetPasswordMessage;

    private String userEmail;

    public void setUserEmail(String email) {
        this.userEmail = email;
    }

    @FXML
    void handleBackClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Auth/logIn.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BackResetPassword.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Forgot Password");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleChangePasswordClick() {
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        resetPasswordMessage.setVisible(false);

        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            resetPasswordMessage.setText("Please fill in all fields.");
            resetPasswordMessage.setVisible(true);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            resetPasswordMessage.setText("Passwords do not match.");
            resetPasswordMessage.setVisible(true);
            return;
        }

        String hashedPassword = PasswordEncryption.hashPassword(newPassword);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Query query = session.createQuery("UPDATE User SET password = :password WHERE email = :email");
            query.setParameter("password", hashedPassword);
            query.setParameter("email", userEmail);
            int result = query.executeUpdate();
            session.getTransaction().commit();

            if (result > 0) {
                resetPasswordMessage.setText("Password changed successfully!");
                resetPasswordMessage.setStyle("-fx-text-fill: green;");
                resetPasswordMessage.setVisible(true);

                newPasswordField.clear();
                confirmPasswordField.clear();
            } else {
                resetPasswordMessage.setText("Error: User not found.");
                resetPasswordMessage.setVisible(true);
            }
        } catch (Exception e) {
            resetPasswordMessage.setText("An error occurred. Please try again.");
            resetPasswordMessage.setVisible(true);
            e.printStackTrace();
        }
    }
}
