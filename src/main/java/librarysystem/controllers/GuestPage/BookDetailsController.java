package librarysystem.controllers.GuestPage;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.IOException;

public class BookDetailsController {

    @FXML
    private Label bookTitleLabel;

    @FXML
    private Label authorLabel;

    @FXML
    private Label publishDateLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private ImageView bookImageView;

    @FXML
    private Label typeLabel;

    @FXML
    private Button returnBackButton;


    @FXML
    private void handleBackButtonAction(MouseEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/librarysystem/views/GuestPage/guest_page.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) returnBackButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setBookDetails(String title, String author, String publishDate, String type, String status, Image image) {

        bookTitleLabel.setText(title);
        authorLabel.setText(author);
        publishDateLabel.setText(publishDate);
        typeLabel.setText(type);
        statusLabel.setText(status);
        bookImageView.setImage(image);
    }


    public void initialize() {

        bookTitleLabel.setText("عنوان الكتاب");
        authorLabel.setText("اسم المؤلف");
        publishDateLabel.setText("تاريخ النشر");
        statusLabel.setText("حالة الكتاب");
        typeLabel.setText("نوع الكتاب");
        bookImageView.setImage(new Image("path_to_default_image.jpg"));
    }
}
