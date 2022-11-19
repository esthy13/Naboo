package com.project.demo;

import com.project.demo.Scene.Encryptor;
import com.project.demo.model.DBget;
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
        //FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("prova.fxml"));
        Parent root = fxmlLoader.load();
        stage.setTitle("Login");
        stage.getIcons().add(new Image(Main.class.getResource("TelegramProgetto.png").toString()));
        stage.setScene(new Scene(root, 716, 408));
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {

        launch();

    }
}