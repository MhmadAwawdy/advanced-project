package librarysystem.models.interfaces;

import librarysystem.models.Book;
import javafx.scene.image.Image;
import java.util.List;

public interface DAO {

    // لحفظ الكتاب في قاعدة البيانات
    void save(Book book) throws Exception;

    // للتحقق مما إذا كان الكتاب موجودًا في قاعدة البيانات
    boolean isBookExists(String title, String author) throws Exception;

    // لتصفية الكتب بناءً على معايير محددة
    List<Book> filterBooks(String searchText, String selectedTitle, String selectedAuthor, String selectedDate) throws Exception;

    // للحصول على جميع الكتب
    List<Book> getAllBooks() throws Exception;

    // للحصول على تفاصيل كتاب باستخدام العنوان
    Book getBookDetailsByTitle(String title) throws Exception;

    // للحصول على صورة الكتاب باستخدام العنوان
    Image getImageByBookTitle(String title) throws Exception;

    // للحصول على العناوين المتاحة في قاعدة البيانات
    List<String> getAvailableTitles() throws Exception;

    // للحصول على المؤلفين المتاحين
    List<String> getAvailableAuthors() throws Exception;

    // للحصول على التواريخ المتاحة للنشر


}
