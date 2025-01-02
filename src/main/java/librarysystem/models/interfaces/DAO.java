package librarysystem.models.interfaces;

import librarysystem.models.Book;
import javafx.scene.image.Image;
import java.util.List;

public interface DAO {

    void save(Book book) throws Exception;

    boolean isBookExists(String title, String author) throws Exception;

    List<Book> filterBooks(String searchText, String selectedTitle, String selectedAuthor, String selectedDate) throws Exception;

    List<Book> getAllBooks() throws Exception;

    Book getBookDetailsByTitle(String title) throws Exception;

    Image getImageByBookTitle(String title) throws Exception;

    List<String> getAvailableTitles() throws Exception;

    List<String> getAvailableAuthors() throws Exception;

    boolean hasImage(String title) throws Exception;
}
