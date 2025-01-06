package librarysystem.models.services;

import javafx.scene.image.Image;
import librarysystem.models.Book;
import librarysystem.models.interfaces.DAO;
import librarysystem.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Objects;

public class BookDAO implements DAO
{
    private final HibernateUtil hibernateUtil;
    private final SessionFactory sessionFactory;

    public BookDAO()
    {
        hibernateUtil = HibernateUtil.getInstance();
        sessionFactory = hibernateUtil.getSessionFactory();
    }
    @Override
    public List<Book> filterBooks(String searchText, String selectedTitle, String selectedAuthor, String selectedDate) throws Exception
    {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try
        {
            transaction = session.beginTransaction();
            StringBuilder queryStr = new StringBuilder("FROM Book b WHERE 1=1");

            if (searchText != null && !searchText.trim().isEmpty())
            {
                queryStr.append(" AND (LOWER(b.title) LIKE LOWER(:searchText) OR LOWER(b.author) LIKE LOWER(:searchText))");
            }
            if (selectedTitle != null && !selectedTitle.equals("All Titles"))
            {
                queryStr.append(" AND LOWER(b.title) = LOWER(:title)");
            }
            if (selectedAuthor != null && !selectedAuthor.equals("All Authors"))
            {
                queryStr.append(" AND LOWER(b.author) = LOWER(:author)");
            }
            if (selectedDate != null && !selectedDate.trim().isEmpty())
            {
                queryStr.append(" AND b.publishDate = :publishDate");
            }

            Query<Book> query = session.createQuery(queryStr.toString(), Book.class);

            if (searchText != null && !searchText.trim().isEmpty())
            {
                query.setParameter("searchText", "%" + searchText.toLowerCase() + "%");
            }
            if (selectedTitle != null && !selectedTitle.equals("All Titles"))
            {
                query.setParameter("title", selectedTitle.toLowerCase());
            }
            if (selectedAuthor != null && !selectedAuthor.equals("All Authors"))
            {
                query.setParameter("author", selectedAuthor.toLowerCase());
            }
            if (selectedDate != null && !selectedDate.trim().isEmpty())
            {
                query.setParameter("publishDate", Integer.parseInt(selectedDate));
            }

            List<Book> books = query.getResultList();
            transaction.commit();
            return books;
        }
        catch (Exception e)
        {
            if (transaction != null)
            {
                transaction.rollback();
            }
            throw new Exception("Error filtering books: " + e.getMessage(), e);
        }
        finally
        {
            session.close();
        }
    }
    @Override
    public Book getBookDetailsByTitle(String title) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<Book> query = session.createQuery(
                    "FROM Book b WHERE LOWER(b.title) = LOWER(:title)",
                    Book.class
            );
            query.setParameter("title", title.toLowerCase());

            List<Book> books = query.getResultList();
            if (books.size() > 1)
            {
                throw new Exception("Multiple books found with the same title: " + title);
            }
            transaction.commit();
            return books.isEmpty() ? null : books.get(0);
        }
        catch (Exception e)
        {
            if (transaction != null && transaction.isActive())
            {
                transaction.rollback();
            }
            throw new Exception("Error fetching book details: " + e.getMessage(), e);
        }
        finally
        {
            session.close();
        }
    }
    public Image getImageByBookTitle(String title) throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            String hql = "FROM Book WHERE LOWER(title) = LOWER(:title)";
            Query<Book> query = session.createQuery(hql, Book.class);
            query.setParameter("title", title);
            Book book = query.uniqueResult();

            if (book != null && book.getImage() != null)
            {
                byte[] imageBytes = book.getImage();
                try
                {
                    return new Image(new ByteArrayInputStream(imageBytes));
                }
                catch (Exception e)
                {
                    System.err.println("Error creating image from bytes: " + e.getMessage());
                    return getDefaultImage();
                }
            }
            return getDefaultImage();
        }
        catch (Exception e)
        {
            System.err.println("Error fetching book image: " + e.getMessage());
            return getDefaultImage();
        }
        finally
        {
            session.close();
        }
    }
    private Image getDefaultImage()
    {
        try {
            return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/default-book.png")));
        }
        catch (Exception e)
        {
            System.err.println("Error loading default image: " + e.getMessage());
            return null;
        }
    }
    @Override
    public List<Book> getAllBooks() throws Exception
    {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try
        {
            transaction = session.beginTransaction();
            Query<Book> query = session.createQuery("FROM Book ORDER BY title", Book.class);
            List<Book> books = query.getResultList();
            transaction.commit();
            return books;
        }
        catch (Exception e)
        {
            if (transaction != null)
            {
                transaction.rollback();
            }
            throw new Exception("Error fetching all books: " + e.getMessage(), e);
        }
        finally
        {
            session.close();
        }
    }
    public List<Book> getBooksByTitle(String title) throws Exception
    {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try
        {
            transaction = session.beginTransaction();
            String hql = "FROM Book b WHERE LOWER(b.title) = LOWER(:title)";
            Query<Book> query = session.createQuery(hql, Book.class);
            query.setParameter("title", title.toLowerCase());
            List<Book> books = query.getResultList();
            transaction.commit();
            return books;
        }
        catch (Exception e)
        {
            if (transaction != null)
            {
                transaction.rollback();
            }
            throw new Exception("Error fetching books by title: " + e.getMessage(), e);
        }
        finally
        {
            session.close();
        }
    }
    @Override
    public boolean hasImage(String title)
    {
        try
        {
            Book book = getBookDetailsByTitle(title);
            return book != null && book.getImage() != null;
        }
        catch (Exception e)
        {
            if (e.getMessage().contains("Multiple books found with the same title"))
            {
                System.err.println("Duplicate books found for title: " + title);
            }
            else
            {
                e.printStackTrace();
            }
            return false;
        }
    }
}
