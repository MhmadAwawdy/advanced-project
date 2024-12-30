package librarysystem.controllers.GuestPage;


import librarysystem.models.interfaces.DAO;
import librarysystem.models.Book2;
import librarysystem.models.BookDAOImpl;
import librarysystem.models.BookService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class GuestPageController {

    @FXML
    private ImageView imageView, imageView1, imageView2, imageView3, imageView4,
            imageView5, imageView6, imageView7, imageView8, imageView9;
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> publishDateComboBox;
    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private ComboBox<String> authorComboBox;
    @FXML
    private ComboBox<String> titleComboBox;
    @FXML
    private Button searchButton;
    @FXML
    private Button sendButton;

    private List<Book2> books;
    private List<Book2> filteredBooks;
    private BookService bookService;

    @FXML
    public void initialize() {
        bookService = new BookService(new BookDAOImpl());
        loadBooks();
        setupInitialState();
        setupEventHandlers();
    }

    private void setupInitialState() {
        // Populate ComboBoxes with unique values from database
        publishDateComboBox.getItems().addAll(bookService.getAllBooks().stream()
                .map(Book2::getPublishDate)
                .distinct()
                .sorted()
                .collect(Collectors.toList()));

        typeComboBox.getItems().addAll(bookService.getAllBooks().stream()
                .map(Book2::getType)
                .distinct()
                .sorted()
                .collect(Collectors.toList()));

        authorComboBox.getItems().addAll(bookService.getAllBooks().stream()
                .map(Book2::getAuthor)
                .distinct()
                .sorted()
                .collect(Collectors.toList()));

        titleComboBox.getItems().addAll(bookService.getAllBooks().stream()
                .map(Book2::getTitle)
                .distinct()
                .sorted()
                .collect(Collectors.toList()));
    }

    private void setupEventHandlers() {
        searchButton.setOnAction(event -> handleSearch());
        sendButton.setOnAction(event -> handleSend());
        publishDateComboBox.setOnAction(event -> handleFilterChange());
        typeComboBox.setOnAction(event -> handleFilterChange());
        authorComboBox.setOnAction(event -> handleFilterChange());
        titleComboBox.setOnAction(event -> handleFilterChange());

        setupMouseEffects();
    }

    private void loadBooks() {
        books = bookService.getAllBooks();  // Retrieve all books from the database
        filteredBooks = books; // Initialize filteredBooks with all books initially
        updateBookImages(); // Display all books on load
    }

    private void handleSearch() {
        applyFilters();
    }

    private void handleSend() {
        applyFilters();
    }

    private void handleFilterChange() {
        applyFilters();
    }

    private void applyFilters() {
        String searchText = searchField.getText().toLowerCase();
        String selectedPublishDate = publishDateComboBox.getValue();
        String selectedType = typeComboBox.getValue();
        String selectedAuthor = authorComboBox.getValue();
        String selectedTitle = titleComboBox.getValue();

        // Apply filters based on the combo box and text field selections
        filteredBooks = books.stream()
                .filter(book -> {
                    boolean matchesSearch = searchText.isEmpty() ||
                            book.getTitle().toLowerCase().contains(searchText) ||
                            book.getAuthor().toLowerCase().contains(searchText) ||
                            book.getType().toLowerCase().contains(searchText);

                    boolean matchesPublishDate = selectedPublishDate == null ||
                            book.getPublishDate().equals(selectedPublishDate);

                    boolean matchesType = selectedType == null ||
                            book.getType().equals(selectedType);

                    boolean matchesAuthor = selectedAuthor == null ||
                            book.getAuthor().equals(selectedAuthor);

                    boolean matchesTitle = selectedTitle == null ||
                            book.getTitle().equals(selectedTitle);

                    return matchesSearch && matchesPublishDate && matchesType &&
                            matchesAuthor && matchesTitle;
                })
                .collect(Collectors.toList());

        updateBookImages();  // Update book images based on filtered results
    }

    private void updateBookImages() {
        ImageView[] imageViews = {imageView, imageView1, imageView2, imageView3, imageView4,
                imageView5, imageView6, imageView7, imageView8, imageView9};

        // Clear previous book images and set user data to null
        for (ImageView iv : imageViews) {
            if (iv != null) {
                iv.setImage(null);
                iv.setUserData(null);
            }
        }

        // Display images based on filtered books
        for (int i = 0; i < filteredBooks.size() && i < imageViews.length; i++) {
            Book2 book = filteredBooks.get(i);
            ImageView iv = imageViews[i];

            if (iv != null && book.getImage() != null && !book.getImage().isEmpty()) {
                try {
                    Image image = new Image(book.getImage());
                    iv.setImage(image);
                    iv.setUserData(book.getId());  // Store book ID in the ImageView's user data
                } catch (Exception e) {
                    System.err.println("Error loading image for book: " + book.getTitle());
                    e.printStackTrace();
                }
            }
        }
    }

    private void setupMouseEffects() {
        ImageView[] imageViews = {imageView, imageView1, imageView2, imageView3, imageView4,
                imageView5, imageView6, imageView7, imageView8, imageView9};

        for (ImageView iv : imageViews) {
            if (iv != null) {
                iv.setOnMouseClicked(this::handleImageClick);
                iv.setOnMouseEntered(this::handleMouseEnter);
                iv.setOnMouseExited(this::handleMouseExit);
            }
        }
    }

    @FXML
    private void handleImageClick(MouseEvent event) {
        try {
            ImageView clickedImage = (ImageView) event.getSource();
            Long bookId = (Long) clickedImage.getUserData();

            if (bookId != null) {
                Book2 book = bookService.getBookById(bookId);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/BookDetails.fxml"));
                Parent root = loader.load();

                BookDetailsController controller = loader.getController();
                controller.setBookDetails(book);  // Passing the selected book to the BookDetailsController

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleMouseEnter(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(5.0);
        shadow.setOffsetY(5.0);
        shadow.setRadius(10.0);
        shadow.setColor(javafx.scene.paint.Color.GRAY);
        imageView.setEffect(shadow);
    }

    private void handleMouseExit(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        imageView.setEffect(null);
    }
}
