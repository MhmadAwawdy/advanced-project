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

    // فتح حزمة JavaFX للحزم التي تعمل عبر FXML
    opens com.example.libraryfinalproject to javafx.fxml;
    opens com.example.libraryfinalproject.Controllers to javafx.fxml;

    // تصدير الحزم الرئيسية للحزم الخارجية
    exports com.example.libraryfinalproject;
    exports com.example.libraryfinalproject. Controllers;
}
