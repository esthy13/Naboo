package com.project.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-view.fxml"));
        //System.out.println(Main.class.getResource("login-view.fxml"));
        Parent root = fxmlLoader.load();
        stage.setTitle("Login");
        stage.getIcons().add(new Image(Main.class.getResource("TelegramProgetto.png").toString()));
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}