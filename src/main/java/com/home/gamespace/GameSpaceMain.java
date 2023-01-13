package com.home.gamespace;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class GameSpaceMain extends Application {
    @Override

    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameSpaceMain.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1310,845);
        stage.getIcons().add(new Image(Objects.requireNonNull(GameSpaceMain.class.getResourceAsStream("images/logo.png"))));
        stage.setTitle("GameSpace");
        stage.setScene(scene);
        stage.show();
        Database.intializeDatabase();
    }

    public static void main(String[] args) {
        launch();
    }
}