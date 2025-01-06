package librarysystem.controllers.Client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import librarysystem.controllers.Reservation.ReservationController;
import librarysystem.utils.StageUtil;

import java.io.IOException;
import java.util.Objects;

public class BookDetailsClientController {

    @FXML
    private Label bookTitleLabel;

    @FXML
    private Label authorLabel;

    @FXML
    private Label publishDateLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Label statusLabel1;

    @FXML
    private ImageView bookImageView;

    @FXML
    private ImageView detailsImageView;

    @FXML
    private ImageView returnArrowImageView;

    @FXML
    private Button ReservationRequest;

    private int bookId;

    @FXML
    private void initialize() {
        setupReturnArrowImage();
    }

    public void setBookDetails(String title, String author, String publishDate, String type, String status, Image bookImage, int bookId)
    {
        try
        {
            this.bookId = bookId;
            bookTitleLabel.setText(title != null && !title.isEmpty() ? title : "Title Not Available");
            authorLabel.setText(author != null && !author.isEmpty() ? author : "Author Unknown");
            publishDateLabel.setText(publishDate != null && !publishDate.isEmpty() ? publishDate : "Publication Date Unknown");
            statusLabel.setText(type != null && !type.isEmpty() ? type : "Type Not Specified");
            statusLabel1.setText(status != null && !status.isEmpty() ? status : "Status Unknown");

            if ("Available".equalsIgnoreCase(status)) {
                statusLabel1.setStyle("-fx-text-fill: green;");
            } else if ("Unavailable".equalsIgnoreCase(status)) {
                statusLabel1.setStyle("-fx-text-fill: red;");
            }

            bookImageView.setImage(bookImage != null ? bookImage : new Image(getClass().getResourceAsStream("/images/default-book.png")));
            bookImageView.setFitWidth(200);
            bookImageView.setFitHeight(300);
            bookImageView.setPreserveRatio(true);

        } catch (Exception e) {
            System.err.println("Error setting book details: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void goForward(ActionEvent actionEvent) {
        if (actionEvent.getSource() == ReservationRequest) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Reservation/ReservationPage.fxml"));
                Parent root = loader.load();

                ReservationController reservationController = loader.getController();

                reservationController.setBookId(bookId);

                Stage currentStage = (Stage) ReservationRequest.getScene().getWindow();
                Scene scene = new Scene(root);
                currentStage.setScene(scene);
                currentStage.setTitle("Reservation Book");
                StageUtil.setAppIcon(currentStage);
                currentStage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleBackButtonAction() {
        try {
            Stage currentStage = (Stage) bookTitleLabel.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            System.err.println("Error closing window: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupReturnArrowImage() {
        try {
            if (returnArrowImageView != null) {
                returnArrowImageView.setOnMouseClicked(event -> handleBackButtonAction());
                returnArrowImageView.setOnMouseEntered(event -> returnArrowImageView.setStyle("-fx-opacity: 0.7;"));
                returnArrowImageView.setOnMouseExited(event -> returnArrowImageView.setStyle("-fx-opacity: 1.0;"));
            }
        } catch (Exception e) {
            System.err.println("Error setting up return arrow: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
