package com.example.libraryfinalproject.Controllers;

import com.example.libraryfinalproject.Models.Book;

import com.example.libraryfinalproject.DB.BookDAOImpl;
import com.example.libraryfinalproject.Models.*;


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
import java.util.List;

public class GuestPageController {

    @FXML private ImageView imageView, imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9;
    @FXML private TextField searchTextField;
    @FXML private ComboBox<String> filterComboBox;
    @FXML private ComboBox<String> filterValuesComboBox;

    private List<Book> books;
    private String selectedFilter = "";


    private BookService bookService = new BookService(new BookDAOImpl());

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

    // هذا هو طريقة التعامل مع الضغط على صورة الكتاب
    public void handleImageClick(MouseEvent event) {
        try {
            int bookId = getBookIdFromImageView((ImageView) event.getSource());

            // الحصول على الكتاب من bookService بناءً على الكتاب المحدد
            Book book = bookService.getBookById(bookId);

            // تحميل BookDetails.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BookDetails.fxml"));
            Parent root = loader.load();

            // تمرير الكتاب الى BookDetailsController
            BookDetailsController bookDetailsController = loader.getController();
            bookDetailsController.setBookDetails(book);  // تمرير الكائن Book إلى BookDetailsController

            // عرض صفحة التفاصيل
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
        return (int) imageView.getUserData();  // يفترض أنك تخزن id الكتاب هنا
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
                    filterValuesComboBox.getItems().addAll(bookService.getAuthors());
                    break;
                case "Title":
                    filterValuesComboBox.getItems().addAll(bookService.getTitles());
                    break;
                case "Type":
                    filterValuesComboBox.getItems().addAll(bookService.getTypes());
                    break;
            }
        }
    }

    @FXML
    private void handleSearch() {
        String searchText = searchTextField.getText().toLowerCase();
        String selectedFilterValue = filterValuesComboBox.getValue();
        filterBooks(searchText, selectedFilterValue);
    }

    private void filterBooks(String searchText, String selectedFilterValue) {
        books = bookService.getBooksByFilter(searchText, selectedFilterValue, selectedFilter);
        updateBookImages();
    }

    private void loadBooks() {
        books = bookService.getAllBooks();
        updateBookImages();
    }

    private void updateBookImages() {
        // هنا نقوم بتحديث الصور
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            ImageView imageView = getImageViewForPosition(i);
            if (book.getImage() != null) {
                Image image = new Image(book.getImage());
                imageView.setImage(image);

                // ربط الـ bookId مع الـ ImageView
                imageView.setUserData(book.getId());
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
