package org.openjfx.librarysystem.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/HomePage.fxml"));
        primaryStage.setTitle("Library Reservation System");
        primaryStage.setScene(new Scene(root, 1000, 750));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
