package librarysystem.controllers.HomePage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import librarysystem.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.Objects;

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
    private Label studentCountLabel;  // Label for number of students
    @FXML
    private Label bookCountLabel;
    @FXML
    private void initialize() {
        // Initialize the label with the database values
        userIcon.setOnMouseClicked(event -> openProfile());
        fetchCountsFromDatabase();
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
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Book/BookDetailsLibrarian.fxml")));
            Stage currentStage = (Stage) bookImage.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Book Details");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
