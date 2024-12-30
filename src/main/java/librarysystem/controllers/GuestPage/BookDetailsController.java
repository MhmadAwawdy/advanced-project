package librarysystem.controllers.GuestPage;

import librarysystem.models.Book2;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class BookDetailsController {

    // المتغيرات المرتبطة مع الـ FXML
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

    private Book2 selectedBook;


    public void setBookDetails(Book2 book) {
        this.selectedBook = book;
        displayBookDetails(book);
    }


    private void displayBookDetails(Book2 book) {
        if (book != null) {
            // عرض عنوان الكتاب
            bookTitleLabel.setText("Book Title: " + book.getTitle());

            // عرض اسم المؤلف
            authorLabel.setText("Author Name: " + book.getAuthor());

            // عرض نوع الكتاب
            publishDateLabel.setText("Book Type: " + book.getType()); // تم استبداله بـ getType()

            // عرض تاريخ النشر
            statusLabel.setText("Publication date: " + book.getPublishDate()); // تم استبداله بـ getPublishDate()

            // تحميل الصورة الخاصة بالكتاب إذا كانت موجودة
            if (book.getImage() != null && !book.getImage().isEmpty()) {
                try {
                    Image image = new Image(book.getImage());
                    bookImageView.setImage(image);
                } catch (Exception e) {
                    bookImageView.setImage(null);  // في حالة فشل تحميل الصورة
                    System.out.println("فشل في تحميل الصورة: " + e.getMessage());
                }
            } else {
                bookImageView.setImage(null);  // إذا لم يكن هناك صورة
            }
        }
    }

    // العودة إلى الصفحة السابقة عند الضغط على زر العودة
    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        try {
            // قم بتحميل الواجهة الخاصة بالصفحة الرئيسية أو أي صفحة ترغب في العودة إليها
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GuestPage.fxml"));
            Parent root = loader.load();

            // الحصول على المرحلة الحالية وتغيير المشهد
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // إغلاق المشهد الحالي (اختياري)
            stage.close();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
