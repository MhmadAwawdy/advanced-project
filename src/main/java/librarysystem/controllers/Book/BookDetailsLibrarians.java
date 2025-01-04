package librarysystem.controllers.Book;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import librarysystem.controllers.Reservation.SubmitReservationController;
import librarysystem.models.Book;
import librarysystem.utils.HibernateUtil;
import org.hibernate.Session;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.io.ByteArrayInputStream;
import java.util.Objects;

public class BookDetailsLibrarians {
    @FXML
    private Button BackToHome;

    @FXML
    private Label bookNameLabel;

    @FXML
    private Label authorNameLabel;

    @FXML
    private Label bookTypeLabel;

    @FXML
    private Label publicationDateLabel;

    @FXML
    private Label bookingStatusLabel;

    @FXML
    private ImageView bookImageView;

    @FXML
    private Image image;

    String BookTitle;

    int BookId;

    public void initialize() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            if (session != null) {
                System.out.println("Database connected successfully.");
            }
            loadBookDetails(17); // Test with a valid book ID
        } catch (Exception e) {
            System.err.println("Error connecting to the database.");
            e.printStackTrace();
        }
    }


    private void loadBookDetails(int bookId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Retrieve the book from the database
            Book book = session.get(Book.class, bookId);
            System.out.println("Book Status: " + book.getStatus());

            if (book != null) {
                // Set the book details in the labels
                bookNameLabel.setText("Book name: " + book.getTitle());
                authorNameLabel.setText("Author name: " + book.getAuthor());
                bookTypeLabel.setText("Book type: " + book.getType());
                publicationDateLabel.setText("Publication date: " + book.getPublishDate());
                bookingStatusLabel.setText(
                        "Booking status: " + (book.getStatus() == Book.BookStatus.available ? "Available" : "Reserved")
                );
                BookTitle = book.getTitle();
                BookId = book.getId();

                if (book.getImage() != null) {
                    // Convert byte array to Image object
                    Image image = new Image(new ByteArrayInputStream(book.getImage()));
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(200);  // Set the width
                    imageView.setFitHeight(200); // Set the height
                    imageView.setPreserveRatio(true);

                    // Assuming you have an ImageView in your FXML
                    bookImageView.setImage(image);
                }
            } else {
                bookNameLabel.setText("Book not found");
                authorNameLabel.setText("");
                bookTypeLabel.setText("");
                publicationDateLabel.setText("");
                bookingStatusLabel.setText("");
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Reservation/SubmitReservation.fxml"));
            Parent root = loader.load();
            SubmitReservationController controller = loader.getController();
            controller.setBookName(book.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == BackToHome) {
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/HomePage/HomePageLibrarians.fxml")));
                Stage currentStage = (Stage) BackToHome.getScene().getWindow();
                Scene scene = new Scene(root);
                currentStage.setScene(scene);
                currentStage.setTitle("Library Reservation System");
                currentStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void goToStudent(ActionEvent actionEvent) {
        try {


            // First, load the FXML and get the root
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Reservation/SubmitReservation.fxml"));
            Parent root = loader.load();  // This loads the FXML and returns the root

            // Get the controller from the loader
            SubmitReservationController controller = loader.getController();

            // Set the book name in the controller
            controller.setBookName(BookTitle);
            controller.setBookId(BookId);
            // Set up the stage and scene
            Stage currentStage = (Stage) BackToHome.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Library Reservation System");
            currentStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void extendReservation(ActionEvent actionEvent) {
        loadNewScene("/fxml/Reservation/ExtendedReservation.fxml", "Extend Reservation");
    }

    public void cancelReservation(ActionEvent actionEvent) {
        loadNewScene("/fxml/Reservation/CancelReservation.fxml", "Cancel Reservation");
    }

    private void loadNewScene(String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
            Stage currentStage = (Stage) BackToHome.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle(title);
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void deleteBook(ActionEvent actionEvent) {

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Delete");
        confirmationAlert.setHeaderText("Are you sure you want to delete this book?");
        confirmationAlert.setContentText("This action cannot be undone.");
        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                session.beginTransaction();
                Book bookToDelete = session.get(Book.class, BookId);

                if (bookToDelete != null) {
                    session.delete(bookToDelete);
                    session.getTransaction().commit();
                    System.out.println("Book deleted successfully");

                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/HomePage/HomePageLibrarians.fxml")));
                    Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    currentStage.setScene(scene);
                    currentStage.setTitle("Library Reservation System");
                    currentStage.show();
                } else {
                    System.err.println("Book not found for deletion.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error occurred while deleting the book.");
            }
        } else {
            System.out.println("Book deletion cancelled by the user.");
        }
    }




}
