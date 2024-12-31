package librarysystem.models;
import librarysystem.models.services.bookDAOImp;
import librarysystem.models.interfaces.DAO;
import librarysystem.models.services.bookDAOImp;

import java.util.List;

public class BookService {

    private DAO dao;

    public BookService() {

        this.dao = new bookDAOImp();
    }


    public List<Book2> getAllBooks() {
        return dao.getAllBooks();
    }


    public List<Book2> searchBooks(String searchText) {
        return dao.getBooksBySearch(searchText);
    }


    public List<Book2> filterBooks(String filter, String filterValue) {
        return dao.getBooksByFilter(filter, filterValue);
    }


    public Book2 getBookById(Long id) {
        return dao.getBookById(id);
    }


    public List<String> getAuthors() {
        return dao.getAuthors();
    }


    public List<String> getTitles() {
        return dao.getTitles();
    }


    public List<String> getGenres() {
        return dao.getGenres();
    }


    public List<String> getYears() {
        return dao.getYears();
    }


    public List<Book2> getBooksByYear(String year) {
        return dao.getBooksByYear(year);
    }


    public List<Book2> getBooksByAuthor(String author) {
        return dao.getBooksByAuthor(author);
    }


    public List<Book2> getBooksByTitle(String title) {
        return dao.getBooksByTitle(title);
    }


    public List<Book2> getBooksByGenre(String genre) {
        return dao.getBooksByGenre(genre);
    }
}
