module com.example.mm {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires spring.security.crypto;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires spring.jdbc;


    opens com.example.mm to javafx.fxml;
    exports com.example.mm;
    exports com.example.mm.controllers;
    opens com.example.mm.controllers to javafx.fxml;
    opens com.example.mm.models to javafx.base;
    exports com.example.mm.utils;
    opens com.example.mm.utils to javafx.fxml;
    exports com.example.mm.utils.database;
    opens com.example.mm.utils.database to javafx.fxml;

}