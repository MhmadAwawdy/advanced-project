package librarysystem.controllers.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import javafx.application.Platform;
import librarysystem.utils.StageUtil;

public class HomePageClientController
{
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> filterTitleComboBox;
    @FXML
    private ComboBox<String> filterAuthorComboBox;
    @FXML
    private ComboBox<String> filterDateComboBox;
    @FXML
    private ImageView imageView;
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

        filterTitleComboBox.getItems().addAll("To Kill a Mockingbird", "The Great Gatsby", "The Alchemist", "One Hundred Years of Solitude", "Atomic Habits", "The Catcher in the Rye", "The Power", "Pride and Prejudice", "The Great Gatsby", "Sapiens: A Brief History of Humankind", "Becoming", "The Road", "The Kite Runner", "Educated");
        filterAuthorComboBox.getItems().addAll("Harper Lee", "F. Scott Fitzgerald", "Paulo Coelho", "Gabriel García Márquez", "James Clear", "Rhonda Byrne", "J.D. Salinger", "Jane Austen", "F. Scott Fitzgerald", "Yuval Noah Harari", "Michelle Obama", "Cormac McCarthy", "Khaled Hosseini", "Tara Westover");
        filterDateComboBox.getItems().addAll("2022", "2024", "2010", "2019", "2013", "2025", "2011", "2009", "1913", "2003", "2018", "2006", "2011", "2001", "2004", "1925");


        Platform.runLater(this::loadInitialBooks);
    }

        private void loadInitialBooks() {
        try {

            List<Book> books = bookService.getAllBooks();
            if (books != null && !books.isEmpty()) {
                displayBooks(books);
            } else {
                showAlert("No Books", "No books with images are available.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Could not load initial books: " + e.getMessage());
        }
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

    private void displayBooks(List<Book> books) {
        resetImages();
        for (int i = 0; i < books.size() && i < 9; i++) {
            Book book = books.get(i);
            Image image = bookService.getImageByBookTitle(book.getTitle());


            if (image == null) {
                image = new Image("/path/to/default/image.png");
            }

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
            try {
                openBookDetailsPage(book);
            } catch (Exception e) {
                showAlert("Error", "Could not open book details: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void openBookDetailsPage(Book book) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Client/bookDetailsClient.fxml"));
            Parent root = loader.load();

            BookDetailsClientController bookDetailsController = loader.getController();


            Image bookImage = bookService.getImageByBookTitle(book.getTitle());


            bookDetailsController.setBookDetails(
                    book.getTitle(),
                    book.getAuthor(),
                    String.valueOf(book.getPublishDate()),
                    book.getType(),
                    book.getStatus().toString(),
                    bookImage,
                    book.getId()
                        );


            Stage stage = new Stage();
            stage.setTitle("Book Details - " + book.getTitle());
            Scene scene = new Scene(root);
            stage.setScene(scene);
            StageUtil.setAppIcon(stage);
            stage.show();

        } catch (IOException e) {
            showAlert("Error", "Could not open book details: " + e.getMessage());
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION, message, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }

    @FXML
    private void handleReload(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Client/HomePageClient.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene newScene = new Scene(root);
            stage.setScene(newScene);
            StageUtil.setAppIcon(stage);
            stage.show();
            System.out.println("The reload button has been pressed!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void handleImageClick(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Client/bookDetailsClient.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Book Details");
            StageUtil.setAppIcon(stage);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleMouseEnter(MouseEvent event) {
        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(5.0);
        shadow.setOffsetY(5.0);
        shadow.setRadius(10.0);
        shadow.setColor(javafx.scene.paint.Color.GRAY);
        imageView.setEffect(shadow);
    }

    @FXML
    private void handleMouseExit(MouseEvent event) {
        imageView.setEffect(null);
    }


    public void GoBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Auth/WelcomePage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Library Reservation System");
            StageUtil.setAppIcon(stage);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}