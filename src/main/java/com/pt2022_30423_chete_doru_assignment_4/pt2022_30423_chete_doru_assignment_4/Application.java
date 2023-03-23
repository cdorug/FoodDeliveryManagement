package com.pt2022_30423_chete_doru_assignment_4.pt2022_30423_chete_doru_assignment_4;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    private static Scene scene;
    private Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Login.fxml"));
        scene = new Scene(fxmlLoader.load(), 1005, 625);
        stage.setTitle("Food Order Management");
        stage.setScene(scene);
        stage.show();
    }

    public static void changeWindow(String fxmlFileName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(fxmlFileName));
        scene.setRoot(fxmlLoader.load());
    }

    public static void main(String[] args) {
        launch();
    }



}