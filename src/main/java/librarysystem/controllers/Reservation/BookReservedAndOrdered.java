package librarysystem.controllers.Reservation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import librarysystem.models.Book;
import librarysystem.models.ReservationReq;
import librarysystem.models.Student;
import librarysystem.models.services.ReservationReqDAOImp;
import librarysystem.utils.StageUtil;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Objects;

public class BookReservedAndOrdered
{
    @FXML
    public VBox reservationContainer;
    @FXML
    private AnchorPane parentPane;

    public void initialize() {
        loadReservations();
    }

    public void loadReservations()
    {
        List<ReservationReq> reservations = ReservationReqDAOImp.getAllReservationsWithDetails();
        reservationContainer.getChildren().clear();

        for (ReservationReq reservation : reservations) {
            Student student = reservation.getStudent();
            Book book = reservation.getBook();

            VBox reservationBox = new VBox();
            reservationBox.setSpacing(10);
            reservationBox.setStyle("-fx-padding: 10; -fx-background-color: #fdf4dc; -fx-border-radius: 10; -fx-background-radius: 10;");
            reservationBox.setPrefWidth(900);

            HBox hbox = new HBox(20);
            hbox.setStyle("-fx-alignment: center-left;");

            byte[] imageData = book.getImage();
            if (imageData != null && imageData.length > 0) {
                Image bookImage = new Image(new ByteArrayInputStream(imageData));
                ImageView imageView = new ImageView(bookImage);
                imageView.setFitWidth(150);
                imageView.setFitHeight(200);
                imageView.setPreserveRatio(true);
                hbox.getChildren().add(imageView);
            }

            VBox detailsBox = new VBox(10);
            Label studentIdLabel = new Label("Student ID: " + student.getStudentID());
            Label studentNameLabel = new Label("Student Name: " + student.getStudentName());
            Label bookTitleLabel = new Label("Book Title: " + book.getTitle());
            Label bookAuthorLabel = new Label("Book Author: " + book.getAuthor());
            Label reservedOnLabel = new Label("Reserved On: " + reservation.getReservationDate());

            detailsBox.getChildren().addAll(studentIdLabel, studentNameLabel, bookTitleLabel, bookAuthorLabel, reservedOnLabel);
            hbox.getChildren().add(detailsBox);

            reservationBox.getChildren().add(hbox);
            reservationContainer.getChildren().add(reservationBox);
        }
    }

    private AnchorPane createReservationPane(ReservationReq reservation, int yOffset) {
        AnchorPane reservationPane = new AnchorPane();
        reservationPane.setPrefSize(361, 207);
        reservationPane.setLayoutX(109);
        reservationPane.setLayoutY(yOffset);
        reservationPane.setStyle("-fx-background-color: #FBF3DB; -fx-background-radius: 20px; -fx-border-color: #DCA942; -fx-border-radius: 20px;");

        ImageView bookImage = new ImageView(new Image("file:src/main/resources/images/book" + reservation.getBook() + ".jpg"));
        bookImage.setFitHeight(150);
        bookImage.setFitWidth(200);
        bookImage.setLayoutX(50);
        bookImage.setLayoutY(29);
        reservationPane.getChildren().add(bookImage);

        Label clientName = new Label("Student ID: " + reservation.getStudent());
        clientName.setLayoutX(205);
        clientName.setLayoutY(50);
        clientName.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        reservationPane.getChildren().add(clientName);

        Label bookName = new Label("Book ID: " + reservation.getBook());
        bookName.setLayoutX(205);
        bookName.setLayoutY(100);
        bookName.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        reservationPane.getChildren().add(bookName);

        Label reservationDate = new Label("Reserved On: " + reservation.getReservationDate());
        reservationDate.setLayoutX(205);
        reservationDate.setLayoutY(150);
        reservationDate.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        reservationPane.getChildren().add(reservationDate);

        return reservationPane;
    }

    @FXML
    private Button bookOrder_Back;

    public void switchForm(javafx.event.ActionEvent event)
    {
        if (event.getSource() == bookOrder_Back) {
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/HomePage/HomePageLibrarians.fxml")));
                Stage currentStage = (Stage) bookOrder_Back.getScene().getWindow();
                Scene scene = new Scene(root);
                currentStage.setScene(scene);
                currentStage.setTitle("Library Reservation System");
                StageUtil.setAppIcon(currentStage);
                currentStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
