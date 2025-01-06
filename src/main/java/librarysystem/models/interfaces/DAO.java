package librarysystem.models.interfaces;

import javafx.scene.image.Image;
import librarysystem.models.Book;
import java.util.List;

public interface DAO
{

    List<Book> filterBooks(String searchText, String selectedTitle, String selectedAuthor, String selectedDate) throws Exception;

    List<Book> getAllBooks() throws Exception;

    Book getBookDetailsByTitle(String title) throws Exception;

    Image getImageByBookTitle(String title) throws Exception;

    boolean hasImage(String title) throws Exception;
}
