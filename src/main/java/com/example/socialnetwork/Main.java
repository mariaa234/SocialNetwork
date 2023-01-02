package com.example.socialnetwork;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("login.fxml"));
        GridPane root = loader.load();

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Main");

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
