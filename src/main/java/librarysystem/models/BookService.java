package librarysystem.models;

import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import librarysystem.models.services.BookDAO;
import librarysystem.controllers.GuestPage.BookDetailsController;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;

public class BookService {
    private final BookDAO bookDAO;

    public BookService() {
        this.bookDAO = new BookDAO();
    }

    public List<Book> getAllBooks() {
        try {
            List<Book> books = bookDAO.getAllBooks();
            if (books == null) {
                return new ArrayList<>();
            }
            // Ensure books have images loaded
            List<Book> booksWithImages = new ArrayList<>();
            for (Book book : books) {
                if (book != null && bookDAO.hasImage(book.getTitle())) {
                    booksWithImages.add(book);
                    if (booksWithImages.size() >= 9) {
                        break; // Stop after getting 9 books with images
                    }
                }
            }
            return booksWithImages;
        } catch (Exception e) {
            System.err.println("Error fetching all books: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Book> filterBooks(String searchText, String selectedTitle, String selectedAuthor, String selectedDate) {
        try {
            List<Book> books = bookDAO.filterBooks(searchText, selectedTitle, selectedAuthor, selectedDate);
            if (books == null) {
                return new ArrayList<>();
            }
            return books;
        } catch (Exception e) {
            System.err.println("Error filtering books: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
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
            List<Book> books = bookDAO.getBooksByTitle(title);
            if (books == null) {
                return new ArrayList<>();
            }
            return books;
        } catch (Exception e) {
            System.err.println("Error searching books by title: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean isBookExists(String title, String author) {
        try {
            return bookDAO.isBookExists(title, author);
        } catch (Exception e) {
            System.err.println("Error checking if book exists: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void saveBook(Book book, InputStream bookImageStream) {
        try {
            if (book != null) {
                // Save book information into the database
                bookDAO.save(book);

                // Save the image if provided
                if (bookImageStream != null) {
                    bookDAO.saveBookImage(book.getTitle(), bookImageStream);
                }
            }
        } catch (Exception e) {
            System.err.println("Error saving book: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Book getBookDetailsByTitle(String title) {
        try {
            return bookDAO.getBookDetailsByTitle(title);
        } catch (Exception e) {
            System.err.println("Error fetching book details: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Image getImageByBookTitle(String title) {
        try {
            Image image = bookDAO.getImageByBookTitle(title);
            if (image == null) {
                // Return a default image if the book image is not found
                return new Image(getClass().getResourceAsStream("/images/default-book.png"));
            }
            return image;
        } catch (Exception e) {
            System.err.println("Error fetching book image: " + e.getMessage());
            e.printStackTrace();
            // Return default image in case of error
            return new Image(getClass().getResourceAsStream("/images/default-book.png"));
        }
    }

    public List<String> getAvailableTitles() {
        try {
            List<String> titles = bookDAO.getAvailableTitles();
            if (titles == null) {
                return new ArrayList<>();
            }
            return titles;
        } catch (Exception e) {
            System.err.println("Error fetching available titles: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void onBookImageClick(Book book, MouseEvent event) {
        if (book == null) {
            System.err.println("Cannot open details for null book");
            return;
        }

        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GuestPage/BookDetails.fxml"));
            Parent root = loader.load();

            // Get the controller and set the book details
            BookDetailsController controller = loader.getController();

            // Get the book image before passing it
            Image bookImage = getImageByBookTitle(book.getTitle());

            // Set all book details
            controller.setBookDetails(
                    book.getTitle(),
                    book.getAuthor(),
                    String.valueOf(book.getPublishDate()),
                    book.getType(),
                    book.getStatus().toString(),
                    bookImage
            );

            // Create and configure the new stage
            Stage stage = new Stage();
            stage.setTitle("Book Details - " + book.getTitle());
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Make sure the stage is properly sized
            stage.sizeToScene();

            // Show the stage
            stage.show();

        } catch (IOException e) {
            System.err.println("Error opening book details: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}