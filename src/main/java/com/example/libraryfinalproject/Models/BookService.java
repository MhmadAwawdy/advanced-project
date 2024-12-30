package com.example.libraryfinalproject.Models;


import com.example.libraryfinalproject.DB.DAO;
import java.util.List;

public class BookService {

    private DAO bookDAO;  // الربط مع DAO للوصول إلى البيانات

    // Constructor Dependency Injection: تمرير كائن من نوع DAO
    public BookService(DAO bookDAO) {
        if (bookDAO == null) {
            throw new IllegalArgumentException("bookDAO cannot be null");
        }
        this.bookDAO = bookDAO;
    }

    // استرجاع جميع الكتب
    public List<Book> getAllBooks() {
        return bookDAO != null ? bookDAO.getAllBooks() : null;  // استدعاء getAllBooks من DAO
    }

    // استرجاع الكتب بناءً على نص البحث
    public List<Book> getBooksBySearch(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            return null;  // إرجاع قيمة فارغة إذا كان نص البحث فارغاً
        }
        return bookDAO.getBooksBySearch(searchText);  // استدعاء getBooksBySearch من DAO
    }

    // استرجاع الكتب بناءً على الفلتر المحدد (مثل السنة، الكاتب، العنوان، النوع)
    public List<Book> getBooksByFilter(String filter, String filterValue, String value) {
        if (filter == null || filter.isEmpty() || filterValue == null || filterValue.isEmpty()) {
            return null;  // إرجاع قيمة فارغة في حال كانت قيمة الفلتر فارغة
        }

        // تحديد الحالة المناسبة بناءً على نوع الفلتر
        switch (filter) {
            case "Year":
                return bookDAO.getBooksByYear(filterValue);
            case "Author":
                return bookDAO.getBooksByAuthor(filterValue);
            case "Title":
                return bookDAO.getBooksByTitle(filterValue);
            case "Type":
                return bookDAO.getBooksByType(filterValue);
            default:
                return null;  // إرجاع قيمة فارغة إذا كان الفلتر غير معروف
        }
    }

    // استرجاع كتاب من خلال الـ ID
    public Book getBookById(int id) {
        if (id <= 0) {
            return null;  // إرجاع قيمة فارغة إذا كان الـ ID غير صالح
        }
        return bookDAO.getBookById(id);  // استدعاء getBookById من DAO
    }

    // استرجاع جميع المؤلفين
    public List<String> getAuthors() {
        return bookDAO != null ? bookDAO.getAuthors() : null;  // استدعاء getAuthors من DAO
    }

    // استرجاع جميع العناوين
    public List<String> getTitles() {
        return bookDAO != null ? bookDAO.getTitles() : null;  // استدعاء getTitles من DAO
    }

    // استرجاع جميع الأنواع
    public List<String> getTypes() {
        return bookDAO != null ? bookDAO.getTypes() : null;  // استدعاء getTypes من DAO
    }
}
