package Controllers;
import DB.HibernateUtil;
import Models.Book;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import org.hibernate.Session;
import org.hibernate.Transaction;

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

    private int selectedBookId;

    public void setBookDetails(int bookId) {
        this.selectedBookId = bookId;
        Book book = getBookById(bookId);
        displayBookDetails(book);
    }


    private Book getBookById(int bookId) {
        Session session = null;
        Transaction transaction = null;
        Book book = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            book = session.get(Book.class, bookId);


            if (book != null && book.getImage() == null) {
                book.setImage("");
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return book;
    }


    private void displayBookDetails(Book book) {
        if (book != null) {
            bookTitleLabel.setText(book.getTitle());
            authorLabel.setText(book.getAuthor());
            publishDateLabel.setText(book.getPublishDate());
            statusLabel.setText(book.getStatus());


            if (book.getImage() != null && !book.getImage().isEmpty()) {
                try {

                    Image image = new Image(book.getImage());
                    bookImageView.setImage(image);
                } catch (Exception e) {

                    bookImageView.setImage(null);
                    System.out.println("Failed to load image: " + e.getMessage());
                }
            } else {

                bookImageView.setImage(null);
            }
        }
    }


    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/GuestPage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
