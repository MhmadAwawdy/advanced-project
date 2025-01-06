package librarysystem.controllers.Reservation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import librarysystem.utils.StageUtil;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.Objects;

public class ReservedBooksController
{
    @FXML
    private FlowPane booksContainer;
    private static final String DATABASE_URL = "jdbc:mysql://108.181.157.249:10055/librarysystem";
    private static final String DATABASE_USER = "admin";
    private static final String DATABASE_PASSWORD = "JngGL3fp";

    @FXML
    private void initialize() {
        loadReservedBooks();
    }

    @FXML
    private void switchForm(ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/HomePage/HomePageLibrarians.fxml")));
            Stage currentStage = (Stage) booksContainer.getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Library Management System");
            StageUtil.setAppIcon(currentStage);
            currentStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private void loadReservedBooks()
    {
        String query = """
                SELECT book.title, reservations.reservation_date, reservations.due_date,
                       reservations.student_name, book.image
                FROM reservations
                JOIN book ON reservations.book_id = book.id
                WHERE reservations.status = 'Reserved'
                """;
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery())
        {
            while (resultSet.next())
            {
                VBox bookCard = createBookCard(
                        resultSet.getString("title"),
                        resultSet.getString("reservation_date"),
                        resultSet.getString("due_date"),
                        resultSet.getString("student_name"),
                        resultSet.getBytes("image")
                );
                booksContainer.getChildren().add(bookCard);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println("Error loading reserved books.");
        }
    }
    private VBox createBookCard(String title, String reservationDate, String dueDate, String studentName, byte[] imageBytes) {
        VBox bookCard = new VBox();
        bookCard.setStyle("-fx-border-color: black; -fx-padding: 10; -fx-spacing: 5; -fx-background-color: #f0f0f0; -fx-border-radius: 5;");

        ImageView bookImageView = new ImageView();
        if (imageBytes != null)
        {
            bookImageView.setImage(new Image(new ByteArrayInputStream(imageBytes)));
        }
        bookImageView.setFitWidth(100);
        bookImageView.setFitHeight(150);

        Label titleLabel = new Label("Title: " + title);
        Label reservationDateLabel = new Label("Reservation Date: " + reservationDate);
        Label dueDateLabel = new Label("Due Date: " + dueDate);
        Label studentNameLabel = new Label("Student: " + studentName);

        bookCard.getChildren().addAll(bookImageView, titleLabel, reservationDateLabel, dueDateLabel, studentNameLabel);
        return bookCard;
    }
}
