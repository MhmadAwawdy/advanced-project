package librarysystem.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import librarysystem.utils.StageUtil;
import java.io.IOException;

public class

Main extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/Auth/WelcomePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setMinWidth(1000);
        stage.setMinHeight(750);
        StageUtil.setAppIcon(stage);
        stage.setTitle("Library Reservation System");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
