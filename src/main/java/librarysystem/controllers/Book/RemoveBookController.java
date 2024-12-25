package librarysystem.controllers.Book;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Objects;

public class RemoveBookController {
    @FXML
    private TextField isbnField;
    @FXML
    private Button removeBook_Back;

    @FXML
    public void handleRemoveBook() {
        String isbn = isbnField.getText();
        if (!isbn.isEmpty()) {
            System.out.println("Book Removed with ISBN: " + isbn);
        } else {
            System.out.println("ISBN field is required!");
        }
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == removeBook_Back) {
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/HomePage/HomePageLibrarians.fxml")));
                Stage currentStage = (Stage) removeBook_Back.getScene().getWindow();
                Scene scene = new Scene(root);
                currentStage.setScene(scene);
                currentStage.setTitle("Library Reservation System");
                currentStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
