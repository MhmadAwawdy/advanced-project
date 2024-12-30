package librarysystem.models;

import librarysystem.models.interfaces.DAO;
import librarysystem.models.Book2;
import java.util.List;

public class BookService {

    private DAO dao;  // تعريف DAO

    public BookService(DAO dao) {
        this.dao = dao;  // تمرير DAO من الخارج للمساعدة في تحميل البيانات
    }

    // الحصول على جميع الكتب
    public List<Book2> getAllBooks() {
        return dao.getAllBooks();  // استدعاء الدالة من DAO
    }

    // البحث في الكتب باستخدام نص بحث
    public List<Book2> searchBooks(String searchText) {
        return dao.getBooksBySearch(searchText);  // استدعاء دالة البحث من DAO
    }

    // فلترة الكتب باستخدام فلاتر متعددة
    public List<Book2> filterBooks(String filter, String filterValue) {
        return dao.getBooksByFilter(filter, filterValue);  // استدعاء دالة الفلترة من DAO
    }

    // الحصول على الكتاب بواسطة المعرف
    public Book2 getBookById(Long id) {
        return dao.getBookById(id);
    }

    // الحصول على قائمة المؤلفين المميزين
    public List<String> getAuthors() {
        return dao.getAuthors();
    }

    // الحصول على قائمة العناوين المميزة
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

    // الحصول على الكتب باستخدام عنوان معين
    public List<Book2> getBooksByTitle(String title) {
        return dao.getBooksByTitle(title);
    }

    // الحصول على الكتب حسب النوع
    public List<Book2> getBooksByGenre(String genre) {
        return dao.getBooksByGenre(genre);
    }
}
