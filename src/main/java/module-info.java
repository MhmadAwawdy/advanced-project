module com.example.libraryfinalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.naming;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.logging;
    requires java.persistence;
    requires org.hibernate.orm.core;

    opens JavaMain to javafx.fxml;
    exports JavaMain;
    exports Controllers;
    opens Controllers to javafx.fxml;
}