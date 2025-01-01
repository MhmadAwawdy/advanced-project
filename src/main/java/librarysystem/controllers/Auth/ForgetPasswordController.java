package librarysystem.controllers.Auth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import librarysystem.models.Librarian;
import librarysystem.models.services.LibrarianDAOimp;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public class ForgetPasswordController {
    @FXML private TextField txtEmail;
    @FXML private Button forget_backBtn;
    @FXML private Label forget_errorMessage;
    @FXML private Line verificationLine;
    @FXML private Label verificationLabel;
    @FXML private TextField verificationCodeField;
    @FXML private Button verifyCodeButton;
    @FXML private Label verificationLabelCode;

    private String sentCode;

    private final LibrarianDAOimp librarianDAO;

    public ForgetPasswordController() {
        librarianDAO = new LibrarianDAOimp();
    }

    @FXML
    void sendBtnOnAction(ActionEvent event) {
        forget_errorMessage.setVisible(false);
        forget_errorMessage.setText("");
        String recipientEmail = txtEmail.getText();

        if (recipientEmail == null || !recipientEmail.contains("@")) {
            forget_errorMessage.setText("Please enter a valid email address");
            forget_errorMessage.setVisible(true);
            return;
        }

        Librarian librarian = librarianDAO.findByEmail(recipientEmail);
        if (librarian == null) {
            forget_errorMessage.setText("Email not found in the system");
            forget_errorMessage.setVisible(true);
            return;
        }

        try {
            String randomCode = generateRandomCode();
            sentCode = randomCode;
            sendEmail(recipientEmail, randomCode);
            forget_errorMessage.setText("Verification code sent successfully!");
            forget_errorMessage.setTextFill(Paint.valueOf("green"));
            forget_errorMessage.setVisible(true);
            verificationLine.setVisible(true);
            verificationLabel.setVisible(true);
            verificationCodeField.setVisible(true);
            verifyCodeButton.setVisible(true);
        } catch (MessagingException e) {
            forget_errorMessage.setText("Failed to send email Please try again.");
            forget_errorMessage.setVisible(true);
            e.printStackTrace();
        }
    }

    @FXML
    void verifyCode(ActionEvent event) {
        String enteredCode = verificationCodeField.getText();
        if (enteredCode.isEmpty()) {
            verificationLabelCode.setText("Please enter the code.");
            verificationLabelCode.setVisible(true);
            return;
        }
        if (enteredCode.equals(sentCode)) {
            try {
                Stage stage = (Stage) verifyCodeButton.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Auth/resetPassword.fxml"));
                Parent root = loader.load();

                ResetPasswordController resetPasswordController = loader.getController();
                resetPasswordController.setUserEmail(txtEmail.getText());

                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Reset Password");
                stage.show();
            } catch (IOException e) {
                verificationLabelCode.setText("Error loading the reset password page.");
                verificationLabelCode.setVisible(true);
            }
        } else {
            verificationLabelCode.setText("Invalid code. Please try again.");
            verificationLabelCode.setVisible(true);
        }
    }

    private void sendEmail(String recipientEmail, String randomCode) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myAccountEmail = "mh.hmood2004@gmail.com";
        String password = "ajcm cijj qxrj kwpa";

        Session emailSession = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        Message message = new MimeMessage(emailSession);
        message.setFrom(new InternetAddress(myAccountEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        message.setSubject("Your Verification Code");
        message.setText("Your verification code is: " + randomCode);

        Transport.send(message);
    }

    private String generateRandomCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    public void handleBackClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) forget_backBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Auth/logIn.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }
}
