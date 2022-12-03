package com.project.demo;

import com.project.demo.Scene.Encryptor;
import com.project.demo.controller.Naboo;
import com.project.demo.model.DBget;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

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
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        TelegramBotsApi botsApi;
        try {
            botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Naboo());
        } catch (TelegramApiException e) {

            e.printStackTrace();
        }
        launch();
        //System.exit(0);

    }
}