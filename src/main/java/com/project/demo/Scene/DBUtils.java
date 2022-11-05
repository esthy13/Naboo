package com.project.demo.Scene;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.*;

public class DBUtils {
    public static void changeScene(ActionEvent event, String fxmlFile, String title, String userName) {
        Parent root = new AnchorPane();

        if ((userName != null)){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                //System.out.println(DBUtils.class.getResource(fxmlFile).toString());
                root = fxmlLoader.load();
                LoggedInController loggedInController = fxmlLoader.getController();
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
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public static void signUpUser(ActionEvent event, String username, String password){
        Connection connection = null;
        PreparedStatement psinsert = null;
        PreparedStatement pscheckUserExists = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            pscheckUserExists = connection.prepareStatement("SELECT * FROM Utenti WHERE username = ?");
            pscheckUserExists.setString(1, username);
            resultSet = pscheckUserExists.executeQuery();

            if (resultSet.isBeforeFirst()){
                System.out.println("Username already taken.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("This username is taken!");
                alert.show();
            }else {
                psinsert = connection.prepareStatement("INSERT INTO Utenti (username, password, ruolo) VALUES (?, ?, ?)");
                psinsert.setString(1, username);
                psinsert.setString(2, password);
                psinsert.setString(3, "Amministratore");
                psinsert.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("User Registration Successful!");
                alert.show();

                changeScene(event, "loggedin-view.fxml", "Welcome!", username);
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }finally {
            if (resultSet != null){
                try {
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (pscheckUserExists != null){
                try {
                    pscheckUserExists.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psinsert != null){
                try {
                    psinsert.close();
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
                alert.setContentText("Provided credentials are incorrect!");
                alert.show();
            }else {
                while (resultSet.next()){
                    String retrievedPassword = resultSet.getString("password");
                    if (retrievedPassword.equals(password)){
                        changeScene(event, "loggedin-view.fxml", "Welcome!", username);
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
