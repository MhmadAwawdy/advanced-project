package librarysystem.models.interfaces;

import librarysystem.models.Book;
import java.util.List;

public interface BookDAO
{
    void save(Book book);
    Book getBookByName(String name);
    boolean isBookExists(String title, String author);
}
