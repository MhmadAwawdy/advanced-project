module com.example.librarysystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.media;
    requires java.logging;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires java.mail;
    requires java.activation;
    requires java.desktop;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.persistence;
    requires mysql.connector.java;
    requires org.slf4j;
    requires annotations;


    opens librarysystem.models to org.hibernate.orm.core;
    exports librarysystem.models;

    opens librarysystem.app to javafx.fxml;
    exports librarysystem.app;

    opens librarysystem.utils to javafx.fxml;
    exports librarysystem.utils;

    exports librarysystem.controllers.Auth;
    opens librarysystem.controllers.Auth to javafx.fxml;

    exports librarysystem.controllers.Book;
    opens librarysystem.controllers.Book to javafx.fxml;
    exports librarysystem.controllers.HomePage;
    opens librarysystem.controllers.HomePage to javafx.fxml;
    exports librarysystem.controllers.Reservation;
    opens librarysystem.controllers.Reservation to javafx.fxml;
    exports librarysystem.controllers.Client;
    opens librarysystem.controllers.Client to javafx.fxml;
}
