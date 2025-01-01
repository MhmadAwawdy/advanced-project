package librarysystem.models;

import javafx.scene.image.Image;
import librarysystem.models.services.BookDAO;
import java.util.List;

public class BookService {
    private final BookDAO bookDAO;

    public BookService() {
        this.bookDAO = new BookDAO();
    }

    public List<Book> getAllBooks() {
        try {
            return bookDAO.getAllBooks();
        } catch (Exception e) {
            System.out.println("Error fetching all books: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Book> filterBooks(String searchText, String selectedTitle, String selectedAuthor, String selectedDate) {
        try {
            return bookDAO.filterBooks(searchText, selectedTitle, selectedAuthor, selectedDate);
        } catch (Exception e) {
            System.out.println("Error filtering books: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Book> filterBooksByTitle(String title) {
        return filterBooks(title, null, null, null);
    }

    public List<Book> filterBooksByAuthor(String author) {
        return filterBooks(null, null, author, null);
    }

    public List<Book> filterBooksByDate(String date) {
        return filterBooks(null, null, null, date);
    }

    public List<Book> searchBooksByTitle(String title) {
        try {
            return bookDAO.getBooksByTitle(title);
        } catch (Exception e) {
            System.out.println("Error fetching books by title: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean isBookExists(String title, String author) {
        try {
            return bookDAO.isBookExists(title, author);
        } catch (Exception e) {
            System.out.println("Error checking if book exists: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void saveBook(Book book) {
        try {
            bookDAO.save(book);
        } catch (Exception e) {
            System.out.println("Error saving the book: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Book getBookDetailsByTitle(String title) {
        try {
            return bookDAO.getBookDetailsByTitle(title);
        } catch (Exception e) {
            System.out.println("Error fetching book details: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Image getImageByBookTitle(String title) {
        try {
            return bookDAO.getImageByBookTitle(title);
        } catch (Exception e) {
            System.out.println("Error fetching book image: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getAvailableTitles() {
        try {
            return bookDAO.getAvailableTitles();
        } catch (Exception e) {
            System.out.println("Error fetching available titles: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
