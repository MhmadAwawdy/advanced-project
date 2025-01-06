package librarysystem.controllers.HomePage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import librarysystem.controllers.Book.BookDetailsLibrarians;
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

public class HomePageLibrariansController
{
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
    private Label studentCountLabel;
    @FXML
    private Label bookCountLabel;
    @FXML
    private Label firstBookName;
    @FXML
    public static int Bookid;
    @FXML
    private GridPane reservationsGridPane;
    @FXML
    private GridPane booksGridPane;
    @FXML
    private TextField searchTextField;

    public ImageView bookImageView1;
    public Label bookName1;
    public ImageView bookImageView2;
    public Label bookName2;
    public ImageView bookImageView3;
    public Label bookName3;
    public ImageView bookImageView4;
    public Label bookName4;

    @FXML
    private void initialize()
    {

        userIcon.setOnMouseClicked(event -> openProfile());
        fetchCountsFromDatabase();
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            if (session != null)
            {
                System.out.println("Database connected successfully.");
            }
            System.out.println("firstBookName is " + (firstBookName == null ? "null" : "initialized"));
            loadBooks();
            loadLastReservations();
            loadBookDetails(Bookid);
        } catch (Exception e)
        {
            System.err.println("Error connecting to the database.");
            e.printStackTrace();
        }
    }
    @FXML
    private void openProfile()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/HomePage/ProfilePage.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) userIcon.getScene().getWindow();
            currentStage.setScene(new Scene(root, 1000, 750));
            currentStage.setTitle("Librarian Profile");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    @FXML
    public void navigateToAddBook()
    {
        try
        {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Book/ManagingBook.fxml")));
            Stage currentStage = (Stage) addBookButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Add Book And Update");
            currentStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @FXML
    public void navigateToBookOrder()
    {
        try
        {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Reservation/BookReservedAndOrdered.fxml")));
            Stage currentStage = (Stage) bookOrderButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Order Book");
            currentStage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    @FXML
    public void navigateToReservedBookButton()
    {
        try
        {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Reservation/ReservedBooks.fxml")));
            Stage currentStage = (Stage) reservedBookButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Reserved books");
            currentStage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    @FXML
    public void navigateToAddNewLibrarianOrStudent()
    {
        try
        {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Auth/AddNewLibrarianOrStudent.fxml")));
            Stage currentStage = (Stage) addClientButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Add Client and Student");
            currentStage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleBookImageClick(MouseEvent event)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Book/BookDetailsLibrarian.fxml"));
            Parent root = loader.load();
            ImageView clickedImageView = (ImageView) event.getSource();
            Book clickedBook = (Book) clickedImageView.getUserData();
            if (clickedBook != null)
            {
                Bookid= clickedBook.getId();
                BookDetailsLibrarians controller = loader.getController();
                controller.setBook(clickedBook);
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                currentStage.setScene(scene);
                currentStage.setTitle("Book Details");
                currentStage.show();
            }
            else
            {
                showErrorAlert("Error", "The book details could not be retrieved.");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            showErrorAlert("Error", "An error occurred while loading the book details.");
        }
    }
    private void showErrorAlert(String error, String s)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("An error occurred while loading the book details.");
        alert.showAndWait();
    }
    @FXML
    private void AddMouseEffect(ImageView imageView)
    {
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

    private void fetchCountsFromDatabase()
    {
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            Query<Long> studentQuery = session.createQuery("SELECT COUNT(*) FROM Student", Long.class);
            Long studentCount = studentQuery.uniqueResult();
            Query<Long> bookQuery = session.createQuery("SELECT COUNT(*) FROM Book", Long.class);
            Long bookCount = bookQuery.uniqueResult();
            studentCountLabel.setText("Number of Students: " + studentCount);
            bookCountLabel.setText("Number of Books: " + bookCount);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private void adjustGridPaneHeight()
    {
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            Query<Long> bookQuery = session.createQuery("SELECT COUNT(*) FROM Book", Long.class);
            Long bookCount = bookQuery.uniqueResult();
            int booksPerRow = 2;
            int rowCount = (int) Math.ceil((double) bookCount / booksPerRow);
            int rowHeight = 250;
            double totalHeight = rowCount * rowHeight;
            booksGridPane.setPrefHeight(totalHeight);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private void loadBookDetails(int bookId)
    {
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            Book book = session.get(Book.class, bookId);
            if (book != null)
            {
                if (book.getStatus() == Book.BookStatus.reserved)
                {
                    System.out.println("Book Name: " + book.getTitle());
                    if (firstBookName != null)
                    {
                        firstBookName.setText(book.getTitle());
                    }
                    else
                    {
                        System.err.println("firstBookName is not initialized!");
                    }
                    if (book.getImage() != null)
                    {
                        Image image = new Image(new ByteArrayInputStream(book.getImage()));
                        ImageView imageView = new ImageView(image);
                        imageView.setFitWidth(200);
                        imageView.setFitHeight(200);
                        imageView.setPreserveRatio(true);
                        bookImageView.setImage(image);
                    }
                }
                else
                {
                    System.out.println("Book is available and not reserved. Skipping display.");
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private void loadBooks()
    {
        adjustGridPaneHeight();
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            List<Book> books = session.createQuery("FROM Book", Book.class).list();
            if (booksGridPane != null)
            {
                booksGridPane.getChildren().clear();
                int row = 0;
                int column = 0;
                for (Book book : books)
                {
                    VBox bookBox = new VBox(10);
                    bookBox.setAlignment(Pos.CENTER);
                    bookBox.setStyle("-fx-padding: 10; -fx-border-color: #8B4513; -fx-border-width: 1; -fx-background-color: #FBF3DB    ;");
                    ImageView bookImageView = new ImageView();
                    if (book.getImage() != null)
                    {
                        Image bookImage = new Image(new ByteArrayInputStream(book.getImage()));
                        bookImageView.setImage(bookImage);
                    }
                    bookImageView.setUserData(book);
                    bookImageView.setFitWidth(100);
                    bookImageView.setFitHeight(150);
                    bookImageView.setPreserveRatio(true);
                    bookImageView.setOnMouseClicked(event -> handleBookImageClick(event));
                    AddMouseEffect(bookImageView);
                    Label bookTitleLabel = new Label(book.getTitle());
                    bookTitleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                    bookBox.getChildren().addAll(bookImageView, bookTitleLabel);
                    booksGridPane.add(bookBox, column, row);
                    column++;
                    if (column == 2)
                    {
                        column = 0;
                        row++;
                    }
                }
            }
            else
            {
                System.err.println("GridPane is not initialized!");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private void loadLastReservations()
    {
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            List<Integer> bookIds = session.createQuery("SELECT r.bookId FROM Reservation r ORDER BY r.reservationDate DESC", Integer.class).setMaxResults(4).list();
            List<Book> books = session.createQuery("FROM Book b WHERE b.id IN (:bookIds)", Book.class).setParameter("bookIds", bookIds).list();

            if (reservationsGridPane != null)
            {
                reservationsGridPane.getChildren().clear();
                reservationsGridPane.getColumnConstraints().clear();
                ColumnConstraints column1 = new ColumnConstraints();
                column1.setPercentWidth(100);
                reservationsGridPane.getColumnConstraints().add(column1);
                reservationsGridPane.getRowConstraints().clear();
                int row = 0;
                for (Book book : books)
                {
                    RowConstraints rowConstraints = new RowConstraints();
                    rowConstraints.setPrefHeight(85);
                    reservationsGridPane.getRowConstraints().add(rowConstraints);
                    HBox bookBox = new HBox(20);
                    bookBox.setAlignment(Pos.CENTER_LEFT);
                    bookBox.setStyle("-fx-padding: 10; -fx-border-color: #8B4513; -fx-border-width: 2; -fx-background-color: #FBF3DB; -fx-border-radius: 8; -fx-background-radius: 8;");
                    ImageView bookImageView = new ImageView();
                    if (book.getImage() != null)
                    {
                        Image bookImage = new Image(new ByteArrayInputStream(book.getImage()));
                        bookImageView.setImage(bookImage);
                    }
                    bookImageView.setFitHeight(75);
                    bookImageView.setFitWidth(50);
                    bookImageView.setPreserveRatio(true);
                    Label bookTitleLabel = new Label(book.getTitle());
                    bookTitleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 10));
                    bookTitleLabel.setStyle("-fx-text-fill: #3b3b3b;");
                    bookBox.getChildren().addAll(bookImageView, bookTitleLabel);
                    reservationsGridPane.add(bookBox, 0, row);
                    row++;
                    bookImageView.setOnMouseEntered(event -> bookImageView.setOpacity(0.8));
                    bookImageView.setOnMouseExited(event -> bookImageView.setOpacity(1.0));
                }
            }
            else
            {
                System.err.println("Reservations GridPane is not initialized!");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleSearch()
    {
        String searchQuery = searchTextField.getText().trim();
        if (searchQuery.isEmpty())
        {
            loadBooks();
            return;
        }
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            List<Book> books = session.createQuery("FROM Book b WHERE lower(b.title) LIKE :searchQuery", Book.class).setParameter("searchQuery", "%" + searchQuery.toLowerCase() + "%").list();
            displaySearchResults(books);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private void displaySearchResults(List<Book> books)
    {
        try
        {
            adjustGridPaneHeight();
            booksGridPane.getChildren().clear();
            if (books == null || books.isEmpty())
            {
                System.out.println("No books found matching the search query.");
                return;
            }
            int row = 0;
            int column = 0;
            for (Book book : books)
            {
                VBox bookBox = new VBox(10);
                bookBox.setAlignment(Pos.CENTER);
                bookBox.setStyle("-fx-padding: 10; -fx-border-color: #8B4513; -fx-border-width: 1; -fx-background-color: #FBF3DB;");
                ImageView bookImageView = new ImageView();
                if (book.getImage() != null)
                {
                    Image bookImage = new Image(new ByteArrayInputStream(book.getImage()));
                    bookImageView.setImage(bookImage);
                }
                bookImageView.setUserData(book);
                bookImageView.setFitWidth(100);
                bookImageView.setFitHeight(150);
                bookImageView.setPreserveRatio(true);
                bookImageView.setOnMouseClicked(event -> handleBookImageClick(event));
                AddMouseEffect(bookImageView);
                Label bookTitleLabel = new Label(book.getTitle());
                bookTitleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                bookTitleLabel.setStyle("-fx-text-fill: #3b3b3b;");
                bookBox.getChildren().addAll(bookImageView, bookTitleLabel);
                booksGridPane.add(bookBox, column, row);
                column++;
                if (column == 2)
                {
                    column = 0;
                    row++;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
