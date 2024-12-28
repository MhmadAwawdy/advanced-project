package DB;

import Models.Book;
import java.util.List;

public interface DAO {
    List<Book> getAllBooks();
    List<Book> getBooksBySearch(String searchText);
    List<Book> getBooksByFilter(String filter, String filterValue);
}
