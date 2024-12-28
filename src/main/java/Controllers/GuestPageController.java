package Controllers;

import Models.Book;
import DB.BookDAOImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuestPageController {

    @FXML
    private ImageView imageView, imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9;
    @FXML
    private TextField searchTextField;
    @FXML
    private ComboBox<String> filterComboBox;
    @FXML
    private ComboBox<String> filterValuesComboBox;

    private List<Book> books;
    private String selectedFilter = "";

    private BookDAOImpl bookDAO = new BookDAOImpl();

    @FXML
    public void initialize() {
        filterComboBox.getItems().addAll("Year", "Author", "Title", "Type");
        filterComboBox.setOnAction(event -> updateFilterValues());
        filterValuesComboBox.setOnAction(event -> handleSearch());

        setupMouseEffects();
        loadBooks();
    }

    private void setupMouseEffects() {
        imageView.setOnMouseClicked(this::handleImageClick);
        imageView1.setOnMouseClicked(this::handleImageClick);
        imageView2.setOnMouseClicked(this::handleImageClick);
        imageView3.setOnMouseClicked(this::handleImageClick);
        imageView4.setOnMouseClicked(this::handleImageClick);
        imageView5.setOnMouseClicked(this::handleImageClick);
        imageView6.setOnMouseClicked(this::handleImageClick);
        imageView7.setOnMouseClicked(this::handleImageClick);
        imageView8.setOnMouseClicked(this::handleImageClick);

        imageView.setOnMouseEntered(this::handleMouseEnter);
        imageView1.setOnMouseEntered(this::handleMouseEnter);
        imageView2.setOnMouseEntered(this::handleMouseEnter);
        imageView3.setOnMouseEntered(this::handleMouseEnter);
        imageView4.setOnMouseEntered(this::handleMouseEnter);
        imageView5.setOnMouseEntered(this::handleMouseEnter);
        imageView6.setOnMouseEntered(this::handleMouseEnter);
        imageView7.setOnMouseEntered(this::handleMouseEnter);
        imageView8.setOnMouseEntered(this::handleMouseEnter);

        imageView.setOnMouseExited(this::handleMouseExit);
        imageView1.setOnMouseExited(this::handleMouseExit);
        imageView2.setOnMouseExited(this::handleMouseExit);
        imageView3.setOnMouseExited(this::handleMouseExit);
        imageView4.setOnMouseExited(this::handleMouseExit);
        imageView5.setOnMouseExited(this::handleMouseExit);
        imageView6.setOnMouseExited(this::handleMouseExit);
        imageView7.setOnMouseExited(this::handleMouseExit);
        imageView8.setOnMouseExited(this::handleMouseExit);
    }

    public void handleImageClick(MouseEvent event) {
        try {
            int bookId = getBookIdFromImageView((ImageView) event.getSource());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/BookDetails.fxml"));
            Parent root = loader.load();

            BookDetailsController bookDetailsController = loader.getController();
            bookDetailsController.setBookDetails(bookId);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleMouseEnter(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(5.0);
        shadow.setOffsetY(5.0);
        shadow.setRadius(10.0);
        shadow.setColor(javafx.scene.paint.Color.GRAY);
        imageView.setEffect(shadow);
    }

    public void handleMouseExit(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        imageView.setEffect(null);
    }

    private int getBookIdFromImageView(ImageView imageView) {
        // عودة قيم افتراضية فقط للتجربة
        return (int) (Math.random() * 100);
    }

    private void updateFilterValues() {
        selectedFilter = filterComboBox.getValue();
        if (selectedFilter != null) {
            filterValuesComboBox.getItems().clear();
            switch (selectedFilter) {
                case "Year":
                    filterValuesComboBox.getItems().addAll("2021", "2022", "2023", "2024");
                    break;
                case "Author":
                    filterValuesComboBox.getItems().addAll(getMockAuthors());
                    break;
                case "Title":
                    filterValuesComboBox.getItems().addAll(getMockTitles());
                    break;
                case "Type":
                    filterValuesComboBox.getItems().addAll(getMockTypes());
                    break;
            }
        }
    }

    private List<String> getMockAuthors() {
        List<String> authors = new ArrayList<>();
        authors.add("Author 1");
        authors.add("Author 2");
        authors.add("Author 3");
        return authors;
    }

    private List<String> getMockTitles() {
        List<String> titles = new ArrayList<>();
        titles.add("Title 1");
        titles.add("Title 2");
        titles.add("Title 3");
        return titles;
    }

    private List<String> getMockTypes() {
        List<String> types = new ArrayList<>();
        types.add("Fiction");
        types.add("Non-Fiction");
        types.add("Science");
        return types;
    }

    @FXML
    private void handleSearch() {
        String searchText = searchTextField.getText().toLowerCase();
        String selectedFilterValue = filterValuesComboBox.getValue();
        filterBooks(searchText, selectedFilterValue);
    }

    private void filterBooks(String searchText, String selectedFilterValue) {

        List<Book> filteredBooks = bookDAO.getBooksByFilter(selectedFilter, selectedFilterValue);
        books = new ArrayList<>();


        for (Book book : filteredBooks) {
            if (book.getTitle().toLowerCase().contains(searchText)) {
                books.add(book);
            }
        }
        updateBookImages();
    }

    private void loadBooks() {

        books = bookDAO.getAllBooks();
        updateBookImages();
    }

    private void updateBookImages() {

        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            ImageView imageView = getImageViewForPosition(i);
            if (book.getImage() != null) {
                Image image = new Image(book.getImage());
                imageView.setImage(image);
            }
        }
    }

    private ImageView getImageViewForPosition(int position) {
        switch (position) {
            case 0: return imageView;
            case 1: return imageView1;
            case 2: return imageView2;
            case 3: return imageView3;
            case 4: return imageView4;
            case 5: return imageView5;
            case 6: return imageView6;
            case 7: return imageView7;
            case 8: return imageView8;
            case 9: return imageView9;
            default: return imageView;
        }
    }
}
