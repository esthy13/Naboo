module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires com.rometools.rome;
    requires transitive javafx.graphics;
    requires java.sql;
    requires mysql.connector.java;
    requires telegrambots;
    requires telegrambots.meta;
    requires emoji.java;
    requires opencsv;
    requires javafx.media;
    requires de.jensd.fx.glyphs.commons;



    opens com.project.demo.Scene to javafx.fxml;
    exports com.project.demo;
    exports com.project.demo.Scene;
    exports com.project.demo.model;
    opens com.project.demo.model to javafx.base, javafx.fxml;

    //opens com.project.demo.Scene to javafx.fxml;
}