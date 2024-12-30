package com.example.libraryfinalproject.Controllers;

import com.example.libraryfinalproject.Models.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

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

    private Book selectedBook;

    // استقبال البيانات من GuestPageController
    public void setBookDetails(Book book) {
        this.selectedBook = book;
        displayBookDetails(book);
    }

    // عرض تفاصيل الكتاب
    private void displayBookDetails(Book book) {
        if (book != null) {
            bookTitleLabel.setText(book.getTitle());
            authorLabel.setText(book.getAuthor());

            // تحويل التاريخ إلى نص باستخدام التنسيق المخصص
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            publishDateLabel.setText(book.getPublishDate().format(formatter));  // استخدم التنسيق المخصص هنا

            statusLabel.setText(book.getStatus());

            if (book.getImage() != null && !book.getImage().isEmpty()) {
                try {
                    Image image = new Image(book.getImage());
                    bookImageView.setImage(image);
                } catch (Exception e) {
                    bookImageView.setImage(null);
                    System.out.println("فشل في تحميل الصورة: " + e.getMessage());
                }
            } else {
                bookImageView.setImage(null);
            }
        }
    }

    // العودة للصفحة الرئيسية عند الضغط على زر العودة
    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        try {
            // قم بتحميل الواجهة الخاصة بالصفحة الرئيسية
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
