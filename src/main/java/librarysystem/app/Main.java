package librarysystem.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class

Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/Auth/WelcomePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setMinWidth(1000);
        stage.setMinHeight(750);

        //icon for the application
        stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/app_icon/library.png")))); // Update the path to your icon

        stage.setTitle("Library Reservation System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
