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



    opens com.project.demo.Scene to javafx.fxml;
    exports com.project.demo;
    exports com.project.demo.Scene;

    //opens com.project.demo.Scene to javafx.fxml;
}