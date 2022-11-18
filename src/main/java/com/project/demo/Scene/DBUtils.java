package com.project.demo.Scene;

import com.project.demo.model.DBget;
import com.project.demo.model.DBinsert;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.*;

public class DBUtils {
    public static void changeScene(ActionEvent event, String fxmlFile, String title, String userName, String search) {
        Parent root = new AnchorPane();

        if ((userName != null)){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = fxmlLoader.load();
                switch(fxmlFile) {
                    case "commenti.fxml":
                        CommentiController commentiController = fxmlLoader.getController();
                        commentiController.text.setText(userName);
                        commentiController.search.setText(search);
                        break;
                    case "fonti.fxml":
                        FontiController fontiController = fxmlLoader.getController();
                        fontiController.text.setText(userName);
                        fontiController.search.setText(search);
                        break;
                    case "home.fxml":
                        HomeController homeController = fxmlLoader.getController();
                        homeController.text.setText(userName);
                        break;
                    case "news.fxml":
                        NewsController newsController = fxmlLoader.getController();
                        newsController.text.setText(userName);
                        newsController.search.setText(search);
                        break;
                    case "utenti.fxml":
                        UserController userController = fxmlLoader.getController();
                        userController.text.setText(userName);
                        userController.search.setText(search);
                        break;
                }

            }catch (Exception exception){
                exception.printStackTrace();
            }
        }else {
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = fxmlLoader.load();
            }catch (Exception exception){
                exception.printStackTrace();
            }
        }
        //root.getStylesheets(); per dark mode
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 716, 408));
        stage.show();
    }

    public static void signUpUser(ActionEvent event, String username, String password){
        DBinsert dBinsert = new DBinsert();
        DBget dBget = new DBget();
        if(dBget.userExists(username) && dBget.getRuolo(dBget.getId_user(username)).equals("Amministratore")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().setHeaderText("Utente già registrato");
            alert.show();
        }
        else if(dBget.userExists(username) && dBget.getRuolo(dBget.getId_user(username)).equals("User")){
            dBinsert.modifyRole(Integer.parseInt(dBget.getId_user(username)),"Amministratore");
            dBinsert.modifyPasswordCrypt(Integer.parseInt(dBget.getId_user(username)),password);
            changeScene(event, "home.fxml", "Home", username, null);
        }
        else{
            Encryptor En = new Encryptor();
            String key = "Bar12345Bar12345"; // 128-bit key
            String initVector = "RandomInitVector"; // 16 bytes IV
            String encrypt_pass = En.encrypt(key, initVector, password);
            dBinsert.insertUser(username, encrypt_pass, "Amministratore");
            changeScene(event, "home.fxml", "Home", username, null);
        }
    }

    public static void logInUser(ActionEvent event, String username, String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            preparedStatement = connection.prepareStatement("SELECT password,username FROM Utenti WHERE username = ?");
            preparedStatement.setString(1,username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()){
                System.out.println("User not found in the database!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initStyle(StageStyle.DECORATED);
                //alert.setBackground(Background.fill(Paint.valueOf("#FFB3A8")));
                alert.setContentText("Provided credentials are incorrect!");
                alert.show();
            }else {
                while (resultSet.next()){
                    String retrievedPassword = resultSet.getString("password");
                    String key = "Bar12345Bar12345"; // 128 bit key
                    String initVector = "RandomInitVector"; // 16 bytes IV
                    String decrypt_pass = Encryptor.decrypt(key, initVector,retrievedPassword);
                    DBget dBget = new DBget();
                    
                    if(dBget.getRuolo(dBget.getId_user(username)).equals("User")){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Utente non autorizzato all'accesso");
                        alert.show();
                    }
                    else if (decrypt_pass.equals(password)){
                        changeScene(event, "home.fxml", "Home", username, null);
                    }else {
                        System.out.println("Password did not match!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Provided credentials are incorrect!");
                        alert.show();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            if (resultSet != null){
                try {
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null){
                try {
                    preparedStatement.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if (connection != null){
                try {
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
