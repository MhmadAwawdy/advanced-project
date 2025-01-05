package librarysystem.controllers.HomePage;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import librarysystem.controllers.Book.BookDetailsLibrarians;
import librarysystem.controllers.Reservation.SubmitReservationController;
import librarysystem.models.Reservation;
import librarysystem.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import javafx.scene.image.Image;
import librarysystem.models.Book;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.List;
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
    public static int Bookid;
    @FXML
    private GridPane reservationsGridPane;
    @FXML
    private GridPane booksGridPane
            ;
    public ImageView bookImageView1;
    public Label bookName1;
    public ImageView bookImageView2;
    public Label bookName2;
    public ImageView bookImageView3;
    public Label bookName3;
    public ImageView bookImageView4;
    public Label bookName4;

    @FXML
    private void initialize() {
        // Initialize the label with the database values
        userIcon.setOnMouseClicked(event -> openProfile());
        fetchCountsFromDatabase();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            if (session != null) {
                System.out.println("Database connected successfully.");
            }
            System.out.println("firstBookName is " + (firstBookName == null ? "null" : "initialized"));
            loadBooks();
            loadLastReservations();
            loadBookDetails(Bookid); // Test with a valid book ID
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Book/BookDetailsLibrarian.fxml"));
            Parent root = loader.load();

            // Get the clicked ImageView from the event

            ImageView clickedImageView = (ImageView) event.getSource();

            // Retrieve the Book object from the ImageView's userData
            Book clickedBook = (Book) clickedImageView.getUserData();

            if (clickedBook != null) {
                // Get the Book ID (or any other property you need)
                Bookid= clickedBook.getId();  // Assuming getId() returns the book ID

                // Pass the book data to the BookDetailsLibrarians controller
                BookDetailsLibrarians controller = loader.getController();
                controller.setBook(clickedBook);  // Assuming you have a setBook() method in the controller



                // Set the scene with the BookDetailsLibrarian.fxml
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                currentStage.setScene(scene);
                currentStage.setTitle("Book Details");
                currentStage.show();
            } else {
                showErrorAlert("Error", "The book details could not be retrieved.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Error", "An error occurred while loading the book details.");
        }
    }

    @FXML
    private void AddMouseEffect(ImageView imageView) {
        imageView.setOnMouseEntered(event -> HandleMouseEnter(imageView));
        imageView.setOnMouseExited(event -> HandleMouseExit(imageView));
        imageView.setStyle("-fx-cursor: Hand;");

    }

    @FXML
    private void HandleMouseEnter(ImageView imageView) {
        imageView.setOpacity(0.7);
    }

    @FXML
    private void HandleMouseExit(ImageView imageView) {
        imageView.setOpacity(1);
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

    private void adjustGridPaneHeight() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Query to get the number of books
            Query<Long> bookQuery = session.createQuery("SELECT COUNT(*) FROM Book", Long.class);
            Long bookCount = bookQuery.uniqueResult();

            // Calculate the number of rows needed
            int booksPerRow = 2; // Assuming 2 books per row
            int rowCount = (int) Math.ceil((double) bookCount / booksPerRow);

            // Calculate the total preferred height
            int rowHeight = 250; // Approximate height for each row (including padding)
            double totalHeight = rowCount * rowHeight;

            // Set the prefHeight for the GridPane
            booksGridPane.setPrefHeight(totalHeight);

            System.out.println("GridPane height adjusted to: " + totalHeight);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

                } else {
                    System.out.println("Book is available and not reserved. Skipping display.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadBooks() {
        adjustGridPaneHeight();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // استرجاع جميع الكتب من قاعدة البيانات
            List<Book> books = session.createQuery("FROM Book", Book.class).list();


            // التأكد من أن GridPane متوفر في واجهة FXML
            if (booksGridPane != null) {
                booksGridPane.getChildren().clear(); // تفريغ البيانات السابقة إذا وجدت
                int row = 0;
                int column = 0;

                for (Book book : books) {
                    // إنشاء VBox لكل كتاب
                    VBox bookBox = new VBox(10);
                    bookBox.setAlignment(Pos.CENTER);
                    bookBox.setStyle("-fx-padding: 10; -fx-border-color: #8B4513; -fx-border-width: 1; -fx-background-color: #FBF3DB    ;");

                    // إنشاء صورة الكتاب
                    ImageView bookImageView = new ImageView();
                    if (book.getImage() != null) {
                        Image bookImage = new Image(new ByteArrayInputStream(book.getImage()));
                        bookImageView.setImage(bookImage);
                    }

                    bookImageView.setUserData(book);


                    bookImageView.setFitWidth(100);
                    bookImageView.setFitHeight(150);
                    bookImageView.setPreserveRatio(true);

                    bookImageView.setOnMouseClicked(event -> handleBookImageClick(event));
                    // Add mouse hover effects
                    AddMouseEffect(bookImageView);

                    // اسم الكتاب
                    Label bookTitleLabel = new Label(book.getTitle());
                    bookTitleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

                    // إضافة الصورة والاسم إلى VBox
                    bookBox.getChildren().addAll(bookImageView, bookTitleLabel);

                    // إضافة VBox إلى GridPane
                    booksGridPane.add(bookBox, column, row);

                    column++;
                    if (column == 2) { // الانتقال إلى الصف التالي
                        column = 0;
                        row++;
                    }
                }
            } else {
                System.err.println("GridPane is not initialized!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadLastReservations() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Step 1: Retrieve the last 4 reservation book IDs
            List<Integer> bookIds = session.createQuery(
                            "SELECT r.bookId FROM Reservation r ORDER BY r.reservationDate DESC", Integer.class)
                    .setMaxResults(4)
                    .list();

            // Step 2: Fetch book details using the retrieved book IDs
            List<Book> books = session.createQuery(
                            "FROM Book b WHERE b.id IN (:bookIds)", Book.class)
                    .setParameter("bookIds", bookIds)
                    .list();

            // Step 3: Populate the GridPane (assuming the GridPane is correctly initialized)
            if (reservationsGridPane != null) {
                reservationsGridPane.getChildren().clear(); // Clear previous reservations

                // Clear previous column constraints (if necessary)
                reservationsGridPane.getColumnConstraints().clear();

                // Add a single column constraint, as we want each book in one row
                ColumnConstraints column1 = new ColumnConstraints();
                column1.setPercentWidth(100); // 100% width for a single column per row
                reservationsGridPane.getColumnConstraints().add(column1);

                // Clear previous row constraints and set fixed height for each row
                reservationsGridPane.getRowConstraints().clear();

                // Loop through the books and dynamically add them to the GridPane
                int row = 0;

                for (Book book : books) {
                    // Add RowConstraints for each row with a fixed height of 150px
                    RowConstraints rowConstraints = new RowConstraints();
                    rowConstraints.setPrefHeight(85); // Set fixed row height
                    reservationsGridPane.getRowConstraints().add(rowConstraints);

                    // Create HBox for each book, with ImageView on the left and Label on the right
                    HBox bookBox = new HBox(20); // Spacing between image and title
                    bookBox.setAlignment(Pos.CENTER_LEFT);
                    bookBox.setStyle("-fx-padding: 10; -fx-border-color: #8B4513; -fx-border-width: 2; -fx-background-color: #FBF3DB; -fx-border-radius: 8; -fx-background-radius: 8;");

                    // Create an ImageView for the book image
                    ImageView bookImageView = new ImageView();
                    if (book.getImage() != null) {
                        Image bookImage = new Image(new ByteArrayInputStream(book.getImage()));
                        bookImageView.setImage(bookImage);
                    }
                    bookImageView.setFitHeight(75); // Ensure the image fits within the height
                    bookImageView.setFitWidth(50);  // Set the width proportionally to the height
                    bookImageView.setPreserveRatio(true); // Maintain the aspect ratio of the image

                    // Create a Label for the book title
                    Label bookTitleLabel = new Label(book.getTitle());
                    bookTitleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 10));
                    bookTitleLabel.setStyle("-fx-text-fill: #3b3b3b;");

                    // Add the ImageView and Label to the HBox
                    bookBox.getChildren().addAll(bookImageView, bookTitleLabel);

                    // Add the HBox to the GridPane at the correct row (one book per row)
                    reservationsGridPane.add(bookBox, 0, row);

                    // Move to the next row for the next book
                    row++;

                    // Apply hover effects
                    bookImageView.setOnMouseEntered(event -> bookImageView.setOpacity(0.8));
                    bookImageView.setOnMouseExited(event -> bookImageView.setOpacity(1.0));
                }
            } else {
                System.err.println("Reservations GridPane is not initialized!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}




