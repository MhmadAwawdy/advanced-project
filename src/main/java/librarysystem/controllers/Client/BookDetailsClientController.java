package librarysystem.controllers.Client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

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
    private void initialize() {

        setupReturnArrowImage();
    }

    public void setBookDetails(String title, String author, String publishDate, String type, String status, Image bookImage) {
        try {

            if (title != null && !title.isEmpty()) {
                bookTitleLabel.setText(title);
            } else {
                bookTitleLabel.setText("Title Not Available");
            }


            if (author != null && !author.isEmpty()) {
                authorLabel.setText(author);
            } else {
                authorLabel.setText("Author Unknown");
            }


            if (publishDate != null && !publishDate.isEmpty()) {
                publishDateLabel.setText(publishDate);
            } else {
                publishDateLabel.setText("Publication Date Unknown");
            }

            if (type != null && !type.isEmpty()) {
                statusLabel.setText(type);
            } else {
                statusLabel.setText("Type Not Specified");
            }


            if (status != null && !status.isEmpty()) {
                statusLabel1.setText(status);

                if (status.equalsIgnoreCase("Available")) {
                    statusLabel1.setStyle("-fx-text-fill: green;");
                } else if (status.equalsIgnoreCase("Unavailable")) {
                    statusLabel1.setStyle("-fx-text-fill: red;");
                }
            } else {
                statusLabel1.setText("Status Unknown");
            }


            if (bookImage != null) {
                bookImageView.setImage(bookImage);
                bookImageView.setFitWidth(200);
                bookImageView.setFitHeight(300);
                bookImageView.setPreserveRatio(true);
            } else {

                bookImageView.setImage(new Image(getClass().getResourceAsStream("/images/default-book.png")));
            }

        } catch (Exception e) {
            System.err.println("Error setting book details: " + e.getMessage());
            e.printStackTrace();
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
                returnArrowImageView.setOnMouseEntered(event ->
                        returnArrowImageView.setStyle("-fx-opacity: 0.7;"));
                returnArrowImageView.setOnMouseExited(event ->
                        returnArrowImageView.setStyle("-fx-opacity: 1.0;"));
            }
        } catch (Exception e) {
            System.err.println("Error setting up return arrow: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setDetailsImage(String imageUrl) {
        try {
            if (imageUrl != null && !imageUrl.isEmpty() && detailsImageView != null) {
                Image image = new Image(imageUrl);
                detailsImageView.setImage(image);
                detailsImageView.setFitWidth(200);
                detailsImageView.setFitHeight(300);
                detailsImageView.setPreserveRatio(true);
            }
        } catch (Exception e) {
            System.err.println("Error setting details image: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setReturnArrowImage(String imageUrl) {
        try {
            if (imageUrl != null && !imageUrl.isEmpty() && returnArrowImageView != null) {
                Image image = new Image(imageUrl);
                returnArrowImageView.setImage(image);
                returnArrowImageView.setFitWidth(30);
                returnArrowImageView.setFitHeight(30);
                returnArrowImageView.setPreserveRatio(true);
            }
        } catch (Exception e) {
            System.err.println("Error setting return arrow image: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    private Button ReservationRequest;

    public void backHome(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Client/HomePageClient.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Library Reservation System");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goForward(ActionEvent actionEvent) {
        if (actionEvent.getSource() == ReservationRequest) {
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Reservation/ReservationPage.fxml")));
                Stage currentStage = (Stage) ReservationRequest.getScene().getWindow();
                Scene scene = new Scene(root);
                currentStage.setScene(scene);
                currentStage.setTitle("Reservation Book");
                currentStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }




}