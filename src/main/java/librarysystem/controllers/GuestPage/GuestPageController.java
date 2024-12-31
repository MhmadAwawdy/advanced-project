package librarysystem.controllers.GuestPage;

import librarysystem.models.Book2;
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

    private List<Book2> books;
    private List<Book2> filteredBooks;
    private BookService bookService = new BookService();

    @FXML
    public void initialize() {
        loadBooks();
        setupInitialState();
        setupEventHandlers();
    }

    private void loadBooks() {
        books = bookService.getAllBooks();  // جلب كل الكتب من الخدمة
        filteredBooks = books;  // تعيين الفلاتر إلى جميع الكتب أولاً
        updateBookImages();  // تحديث الصور بعد تحميل الكتب
    }

    private void setupInitialState() {
        // إعداد قائمة تواريخ النشر
        publishDateComboBox.getItems().addAll(generateYearOptions());

        // إعداد قوائم الفلاتر الأخرى مثل النوع والمؤلف والعنوان
        typeComboBox.getItems().addAll(
                books.stream().map(Book2::getType).distinct().sorted().collect(Collectors.toList())
        );
        authorComboBox.getItems().addAll(
                books.stream().map(Book2::getAuthor).distinct().sorted().collect(Collectors.toList())
        );
        titleComboBox.getItems().addAll(
                books.stream().map(Book2::getTitle).distinct().sorted().collect(Collectors.toList())
        );
    }

    private List<String> generateYearOptions() {
        return List.of("2023", "2022", "2021", "2020", "2019", "2018", "2017");
    }

    private void setupEventHandlers() {
        // تعيين الإجراء عند الضغط على زر "بحث" للبحث وتطبيق الفلاتر
        searchButton.setOnAction(event -> applyFilters());
        setupMouseEffects();  // إضافة تأثيرات الفأرة
    }

    // تطبيق الفلاتر بما في ذلك البحث والنصوص المحددة من القوائم المنسدلة
    private void applyFilters() {
        String searchText = searchField.getText().toLowerCase();
        String selectedPublishDate = publishDateComboBox.getValue();
        String selectedType = typeComboBox.getValue();
        String selectedAuthor = authorComboBox.getValue();
        String selectedTitle = titleComboBox.getValue();

        filteredBooks = books.stream()
                .filter(book -> (searchText.isEmpty() || book.getTitle().toLowerCase().contains(searchText)) &&
                        (selectedPublishDate == null || book.getPublishDate().equals(selectedPublishDate)) &&
                        (selectedType == null || book.getType().equals(selectedType)) &&
                        (selectedAuthor == null || book.getAuthor().equals(selectedAuthor)) &&
                        (selectedTitle == null || book.getTitle().equals(selectedTitle)))
                .collect(Collectors.toList());

        updateBookImages();  // تحديث الصور بعد تطبيق الفلاتر
    }

    private void updateBookImages() {
        // تحديث الصور المعروضة بعد تطبيق الفلاتر
        ImageView[] imageViews = {imageView, imageView1, imageView2, imageView3, imageView4,
                imageView5, imageView6, imageView7, imageView8, imageView9};

        // مسح الصور الحالية
        for (ImageView iv : imageViews) {
            iv.setImage(null);
            iv.setUserData(null);
        }

        // إضافة الكتب المصفاة إلى الصور المتاحة
        for (int i = 0; i < filteredBooks.size() && i < imageViews.length; i++) {
            Book2 book = filteredBooks.get(i);
            ImageView iv = imageViews[i];
            if (!book.getImage().isEmpty()) {
                iv.setImage(new Image(book.getImage()));
                iv.setUserData(book.getId());
            }
        }
    }

    private void setupMouseEffects() {
        // إضافة تأثير الفأرة عند التمرير على الصور
        ImageView[] imageViews = {imageView, imageView1, imageView2, imageView3, imageView4,
                imageView5, imageView6, imageView7, imageView8, imageView9};

        for (ImageView iv : imageViews) {
            iv.setOnMouseClicked(this::handleImageClick);
            iv.setOnMouseEntered(this::handleMouseEnter);
            iv.setOnMouseExited(this::handleMouseExit);
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
                controller.setBookDetails(book);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleMouseEnter(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        imageView.setEffect(new DropShadow());  // إضافة تأثير الظل عند التمرير
    }

    @FXML
    public void handleMouseExit(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        imageView.setEffect(null);  // إزالة تأثير الظل عند الخروج
    }

    @FXML
    private void handleSearch() {
        // عند الضغط على زر البحث، نقوم بتطبيق الفلاتر بشكل تلقائي
        applyFilters();
    }

    @FXML
    private void handleSend() {
        // عند الضغط على زر "إرسال"، نطبع رسالة للتحقق من العمل
        System.out.println("Send action triggered");
        // لا داعي لكتابة كود إضافي هنا، لأن الفلاتر يتم تطبيقها بالفعل عند الضغط على البحث
    }
}
