package librarysystem.controllers.GuestPage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import librarysystem.models.Book;
import librarysystem.models.BookService;
import java.io.IOException;
import java.util.List;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;


public class GuestPageController {

    @FXML
    private Button reload;

    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Button sendButton;
    @FXML
    private ComboBox<String> filterTitleComboBox;
    @FXML
    private ComboBox<String> filterAuthorComboBox;
    @FXML
    private ComboBox<String> filterDateComboBox;

    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    @FXML
    private ImageView imageView3;
    @FXML
    private ImageView imageView4;
    @FXML
    private ImageView imageView5;
    @FXML
    private ImageView imageView6;
    @FXML
    private ImageView imageView7;
    @FXML
    private ImageView imageView8;
    @FXML
    private ImageView imageView9;

    private final BookService bookService = new BookService();

    @FXML
    private void initialize() {
        filterTitleComboBox.getItems().addAll("image process", "Digital image processing", "wwww", "Book cover");
        filterAuthorComboBox.getItems().addAll("Malak", "Mikel", "Jemmy");
        filterDateComboBox.getItems().addAll("2022", "2024", "2010", "2019", "2013");
        loadBooks();
    }

    @FXML
    private void handleSend(ActionEvent event) {
        String searchText = searchField.getText();
        String selectedTitle = filterTitleComboBox.getValue();
        String selectedAuthor = filterAuthorComboBox.getValue();
        String selectedDate = filterDateComboBox.getValue();

        try {
            List<Book> searchResults = bookService.filterBooks(searchText, selectedTitle, selectedAuthor, selectedDate);
            displayBooks(searchResults);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSearch(ActionEvent actionEvent) {
        String searchQuery = searchField.getText();
        if (!searchQuery.isEmpty()) {
            try {
                List<Book> searchedBooks = bookService.searchBooksByTitle(searchQuery);
                if (searchedBooks.isEmpty()) {
                    showAlert("Book Not Found", "The book you're searching for is not available.");
                } else {
                    displayBooks(searchedBooks);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void loadBooks() {
        try {
            List<Book> books = bookService.getAllBooks();
            displayBooks(books);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayBooks(List<Book> books) {
        resetImages();
        for (int i = 0; i < books.size() && i < 9; i++) {
            Book book = books.get(i);
            Image image = bookService.getImageByBookTitle(book.getTitle());

            switch (i) {
                case 0:
                    imageView1.setImage(image);
                    setOnClickAction(imageView1, book);
                    addMouseEffect(imageView1);
                    break;
                case 1:
                    imageView2.setImage(image);
                    setOnClickAction(imageView2, book);
                    addMouseEffect(imageView2);
                    break;
                case 2:
                    imageView3.setImage(image);
                    setOnClickAction(imageView3, book);
                    addMouseEffect(imageView3);
                    break;
                case 3:
                    imageView4.setImage(image);
                    setOnClickAction(imageView4, book);
                    addMouseEffect(imageView4);
                    break;
                case 4:
                    imageView5.setImage(image);
                    setOnClickAction(imageView5, book);
                    addMouseEffect(imageView5);
                    break;
                case 5:
                    imageView6.setImage(image);
                    setOnClickAction(imageView6, book);
                    addMouseEffect(imageView6);
                    break;
                case 6:
                    imageView7.setImage(image);
                    setOnClickAction(imageView7, book);
                    addMouseEffect(imageView7);
                    break;
                case 7:
                    imageView8.setImage(image);
                    setOnClickAction(imageView8, book);
                    addMouseEffect(imageView8);
                    break;
                case 8:
                    imageView9.setImage(image);
                    setOnClickAction(imageView9, book);
                    addMouseEffect(imageView9);
                    break;
            }
        }
    }

    @FXML
    private void setOnClickAction(ImageView imageView, Book book) {
        imageView.setOnMouseClicked(event -> {
            openBookDetailsPage(book);
        });
    }

    @FXML
    private void openBookDetailsPage(Book book) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/librarysystem/views/BookDetails.fxml"));
            Parent root = loader.load();


            String publishDateString = String.valueOf(book.getPublishDate());


            String status = book.getStatus().toString();


            BookDetailsController bookDetailsController = loader.getController();
            bookDetailsController.setBookDetails(
                    book.getTitle(),
                    book.getAuthor(),
                    publishDateString,
                    book.getType(),
                    status,
                    bookService.getImageByBookTitle(book.getTitle())
            );

            Stage stage = new Stage();
            stage.setTitle("Book Details");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void resetImages() {
        imageView1.setImage(null);
        imageView2.setImage(null);
        imageView3.setImage(null);
        imageView4.setImage(null);
        imageView5.setImage(null);
        imageView6.setImage(null);
        imageView7.setImage(null);
        imageView8.setImage(null);
        imageView9.setImage(null);
    }

    @FXML
    private void addMouseEffect(ImageView imageView) {
        imageView.setOnMouseEntered(event -> handleMouseEnter(imageView));
        imageView.setOnMouseExited(event -> handleMouseExit(imageView));
    }

    @FXML
    private void handleMouseEnter(ImageView imageView) {
        imageView.setOpacity(0.7);
    }

    @FXML
    private void handleMouseExit(ImageView imageView) {
        imageView.setOpacity(1);
    }

    @FXML
    public void handleMouseEnter(MouseEvent mouseEvent) {

    }

    @FXML
    public void handleImageClick(MouseEvent mouseEvent) {

    }

    @FXML
    public void handleMouseExit(MouseEvent mouseEvent) {

    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION, message, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }

    @FXML
    private void handleReload(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GuestPage/GuestPage.fxml"));
            Parent root = loader.load();


            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();


            Scene newScene = new Scene(root);


            stage.setScene(newScene);
            stage.show();
            System.out.println("The reload button has been pressed!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}