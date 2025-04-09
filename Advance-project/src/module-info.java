
module com.example.advanceproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;

    opens com.example.advanceproject to javafx.fxml;
    exports com.example.advanceproject;
}