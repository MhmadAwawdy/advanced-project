package librarysystem.models.interfaces;

import librarysystem.models.Book2;

import java.util.List;

public interface DAO {


    List<Book2> getAllBooks();


    List<Book2> getBooksBySearch(String searchText);


    List<Book2> getBooksByFilter(String filter, String filterValue);


    Book2 getBookById(Long id);


    List<String> getAuthors();


    List<String> getTitles();


    List<String> getGenres();


    List<String> getYears();


    List<Book2> getBooksByYear(String year);


    List<Book2> getBooksByAuthor(String author);

    List<Book2> getBooksByTitle(String title);


    List<Book2> getBooksByGenre(String genre);
}
