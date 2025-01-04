package librarysystem.controllers.HomePage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import librarysystem.controllers.Reservation.SubmitReservationController;
import librarysystem.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import javafx.scene.image.Image;
import librarysystem.models.Book;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Objects;
import java.io.IOException;
import java.util.ResourceBundle;


public class HomePageLibrariansController {
    @FXML
    private Button addBookButton;
    @FXML
    private Button bookOrderButton;
    @FXML
    private Button reservedBookButton;
    @FXML
    private Button addClientButton;
    @FXML
    private ImageView userIcon;
    @FXML
    private ImageView bookImage;
    @FXML
    private ImageView bookImageView;
    @FXML
    private Label studentCountLabel;  // Label for number of students
    @FXML
    private Label bookCountLabel;
    @FXML
    private Label firstBookName;  // Ensure you define it with @FXML
    @FXML
    int BookId;
    @FXML
    private void initialize() {
        // Initialize the label with the database values
        userIcon.setOnMouseClicked(event -> openProfile());
        fetchCountsFromDatabase();
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

    @FXML
    private void openProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/HomePage/ProfilePage.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) userIcon.getScene().getWindow();
            currentStage.setScene(new Scene(root, 1000, 750));
            currentStage.setTitle("Librarian Profile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void navigateToAddBook() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Book/ManagingBook.fxml")));
            Stage currentStage = (Stage) addBookButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Add Book And Update");
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void navigateToBookOrder() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Reservation/BookReservedAndOrdered.fxml")));
            Stage currentStage = (Stage) bookOrderButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Order Book");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void navigateToReservedBookButton() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Reservation/ReservedBooks.fxml")));
            Stage currentStage = (Stage) reservedBookButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Reserved books");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void navigateToAddNewLibrarianOrStudent() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Auth/AddNewLibrarianOrStudent.fxml")));
            Stage currentStage = (Stage) addClientButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Add Client and Student");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBookImageClick(MouseEvent event) {
        try {
            // Load the BookDetailsLibrarian.fxml scene
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Book/BookDetailsLibrarian.fxml")));

            // Get the current stage from the ImageView's scene
            Stage currentStage = (Stage) bookImage.getScene().getWindow();

            // Create a new scene with the loaded root and set it on the current stage
            Scene scene = new Scene(root);
            currentStage.setScene(scene);

            // Set the title for the new scene (optional)
            currentStage.setTitle("Book Details");

            // Show the stage with the new scene
            currentStage.show();

        } catch (IOException e) {
            // Handle the IOException if something goes wrong with loading the FXML
            e.printStackTrace();
            showErrorAlert("Error", "An error occurred while loading the book details.");
        }
    }

    private void showErrorAlert(String title, String message) {
        // Show an alert with the error message
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void fetchCountsFromDatabase() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Query to get number of students
            Query<Long> studentQuery = session.createQuery("SELECT COUNT(*) FROM Student", Long.class);
            Long studentCount = studentQuery.uniqueResult();

            // Query to get number of books
            Query<Long> bookQuery = session.createQuery("SELECT COUNT(*) FROM Book", Long.class);
            Long bookCount = bookQuery.uniqueResult();

            // Update labels with the counts
            studentCountLabel.setText("Number of Students: " + studentCount);
            bookCountLabel.setText("Number of Books: " + bookCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("firstBookName is " + (firstBookName == null ? "null" : "initialized"));
    }

    private void loadBookDetails(int bookId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Retrieve the book from the database
            Book book = session.get(Book.class, bookId);

            if (book != null) {
                // Check if the book is reserved
                if (book.getStatus() == Book.BookStatus.reserved) {
                    System.out.println("Book Name: " + book.getTitle());
                    if (firstBookName != null) {
                        firstBookName.setText(book.getTitle());
                    } else {
                        System.err.println("firstBookName is not initialized!");
                    }
                    // Set the book details in the labels


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

                    // Load the reservation UI
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/HomePage/HomePageLibrarians.fxml"));
                    loader.setController(this);  // Ensure that this controller is used
                    Parent root = loader.load();
                    SubmitReservationController controller = loader.getController();
                    controller.setBookName(book.getTitle());
                } else {
                    System.out.println("Book is available and not reserved. Skipping display.");
                }
            } else {
                System.out.println("Book not found in the database.");
                firstBookName.setText("Book not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
