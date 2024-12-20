package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    @FXML
    private ImageView imageView;

    @FXML
    private ImageView imageView1;

    @FXML
    private ImageView imageView2;

    @FXML
    private ImageView imageView3;

    @FXML
    private ImageView imageView4;

    @FXML
    private ImageView imageView5;

    @FXML
    private ImageView imageView6;

    @FXML
    private ImageView imageView7;

    @FXML
    private ImageView imageView8;

    @FXML
    private ImageView imageView9;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageView.setOnMouseClicked(this::handleImageClick);
        imageView1.setOnMouseClicked(this::handleImageClick);
        imageView2.setOnMouseClicked(this::handleImageClick);
        imageView3.setOnMouseClicked(this::handleImageClick);
        imageView4.setOnMouseClicked(this::handleImageClick);
        imageView5.setOnMouseClicked(this::handleImageClick);
        imageView6.setOnMouseClicked(this::handleImageClick);
        imageView7.setOnMouseClicked(this::handleImageClick);
        imageView8.setOnMouseClicked(this::handleImageClick);
        imageView9.setOnMouseClicked(this::handleImageClick);

        imageView.setOnMouseClicked(this::handleImageClick);
        imageView1.setOnMouseEntered(this::handleMouseEnter);
        imageView2.setOnMouseEntered(this::handleMouseEnter);
        imageView3.setOnMouseEntered(this::handleMouseEnter);
        imageView4.setOnMouseEntered(this::handleMouseEnter);
        imageView5.setOnMouseEntered(this::handleMouseEnter);
        imageView6.setOnMouseEntered(this::handleMouseEnter);
        imageView7.setOnMouseClicked(this::handleImageClick);
        imageView8.setOnMouseEntered(this::handleMouseEnter);
        imageView9.setOnMouseEntered(this::handleMouseEnter);

        imageView.setOnMouseClicked(this::handleImageClick);
        imageView1.setOnMouseExited(this::handleMouseExit);
        imageView2.setOnMouseExited(this::handleMouseExit);
        imageView3.setOnMouseExited(this::handleMouseExit);
        imageView4.setOnMouseExited(this::handleMouseExit);
        imageView5.setOnMouseExited(this::handleMouseExit);
        imageView6.setOnMouseExited(this::handleMouseExit);
        imageView7.setOnMouseClicked(this::handleImageClick);
        imageView8.setOnMouseExited(this::handleMouseExit);
        imageView9.setOnMouseExited(this::handleMouseExit);
    }

    @FXML
    private void handleImageClick(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/View/BookDetails.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleMouseEnter(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(5.0);
        shadow.setOffsetY(5.0);
        shadow.setRadius(10.0);
        shadow.setColor(javafx.scene.paint.Color.GRAY);
        imageView.setEffect(shadow);
    }

    @FXML
    private void handleMouseExit(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        imageView.setEffect(null);
    }
}
