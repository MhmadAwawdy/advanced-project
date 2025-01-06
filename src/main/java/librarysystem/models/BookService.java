package librarysystem.models;

import javafx.scene.image.Image;
import librarysystem.models.services.BookDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookService
{
    private final BookDAO bookDAO;
    public BookService()
    {
        this.bookDAO = new BookDAO();
    }

    public List<Book> getAllBooks()
    {
        try
        {
            List<Book> books = bookDAO.getAllBooks();
            if (books == null)
            {
                return new ArrayList<>();
            }
            List<Book> booksWithImages = new ArrayList<>();
            for (Book book : books)
            {
                if (book != null && bookDAO.hasImage(book.getTitle()))
                {
                    booksWithImages.add(book);
                    if (booksWithImages.size() >= 9)
                    {
                        break;
                    }
                }
            }
            return booksWithImages;
        }
        catch (Exception e)
        {
            System.err.println("Error fetching all books: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    public List<Book> filterBooks(String searchText, String selectedTitle, String selectedAuthor, String selectedDate)
    {
        try
        {
            List<Book> books = bookDAO.filterBooks(searchText, selectedTitle, selectedAuthor, selectedDate);
            if (books == null)
            {
                return new ArrayList<>();
            }
            return books;
        }
        catch (Exception e)
        {
            System.err.println("Error filtering books: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    public List<Book> searchBooksByTitle(String title)
    {
        try
        {
            List<Book> books = bookDAO.getBooksByTitle(title);
            if (books == null)
            {
                return new ArrayList<>();
            }
            return books;
        }
        catch (Exception e)
        {
            System.err.println("Error searching books by title: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    public Image getImageByBookTitle(String title)
    {
        try
        {
            Image image = bookDAO.getImageByBookTitle(title);
            if (image == null)
            {
                return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/default-book.png")));
            }
            return image;
        }
        catch (Exception e)
        {
            System.err.println("Error fetching book image: " + e.getMessage());
            e.printStackTrace();
            return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/default-book.png")));
        }
    }
}