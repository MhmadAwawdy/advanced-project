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
    private Label typeLabel;  // إضافة label لعرض نوع الكتاب

    @FXML
    private Button returnBackButton;

    // التعامل مع حدث الضغط على زر العودة
    @FXML
    private void handleBackButtonAction(MouseEvent event) {
        try {
            // العودة إلى صفحة GuestPage.fxml عند الضغط على زر العودة
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/librarysystem/views/GuestPage/guest_page.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) returnBackButton.getScene().getWindow(); // الحصول على النافذة الحالية
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // استقبال البيانات الممررة من GuestPageController
    public void setBookDetails(String title, String author, String publishDate, String type, String status, Image image) {
        // تعيين تفاصيل الكتاب
        bookTitleLabel.setText(title);  // تعيين عنوان الكتاب
        authorLabel.setText(author);  // تعيين اسم المؤلف
        publishDateLabel.setText(publishDate);  // تعيين تاريخ النشر
        typeLabel.setText(type);  // تعيين نوع الكتاب
        statusLabel.setText(status);  // تعيين الحالة
        bookImageView.setImage(image);  // تعيين الصورة
    }

    // طريقة initialize لضبط القيم الأولية أو الافتراضية عند تحميل الصفحة
    public void initialize() {
        // تعيين نصوص افتراضية في حال لم يتم تمرير قيم
        bookTitleLabel.setText("عنوان الكتاب");
        authorLabel.setText("اسم المؤلف");
        publishDateLabel.setText("تاريخ النشر");
        statusLabel.setText("حالة الكتاب");
        typeLabel.setText("نوع الكتاب");
        bookImageView.setImage(new Image("path_to_default_image.jpg")); // تعيين صورة افتراضية
    }
}
