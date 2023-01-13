package com.home.gamespace;

import com.home.gamespace.Admin.Auth;
import com.home.gamespace.Admin.Store;
import com.home.gamespace.Game.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

import java.sql.SQLException;
import java.util.Objects;

public class LibraryGameItemController {

    @FXML
    private Label gameId;
    @FXML
    private Label developerName;

    @FXML
    private Label name;

    @FXML
    private Label originalPrice;

    @FXML
    private Label price;

    @FXML
    private Label rating;

    @FXML
    private Label tagLine;

    @FXML
    private HBox image;

    @FXML
    private Button removeGame;

    @FXML
    private Label balance;

    @FXML
    private HBox gameItem;

    public void setData(Game game){
        originalPrice.setText(Double.toString(game.getPrice()));
        name.setText(game.getName());
        double actualPrice = game.getPrice() - (game.getPrice() * game.getDiscount()) / 100 ;
        price.setText(Double.toString(actualPrice));
        rating.setText(Double.toString(game.getRating()));
        tagLine.setText(game.getGameTagLine());
        developerName.setText(game.getDeveloperName());
        gameId.setText(game.getGameId());

        try{
            Image img = new Image(Objects.requireNonNull(GameSpaceMain.class.getResourceAsStream(new String("images/game/"+game.getName()+".jpg"))));
            BackgroundImage bgimg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(1.0, 1.0, true, true, false, false));

            Background bg = new Background(bgimg);
            image.setBackground(bg);
        }
        catch (NullPointerException error){
            System.out.println(game.getName());
        }

        if(Auth.isAdmin){
            gameId.setText("Game Id : "+game.getGameId());
            gameId.setMaxHeight(20);
            gameId.setMinHeight(20);
            gameId.setPrefHeight(20);
            gameId.setVisible(true);
        }
    }

    public void removeGameHandler(ActionEvent event) throws SQLException {
        Game game = null;
        String gameIdTemp = gameId.getText();
        if(Auth.isAdmin){
            gameIdTemp = gameId.getText().substring(10);
        }
        for (int i = 0; i< Store.noOfGames; i++) {
            if (Store.games.get(i).getGameId().equals(gameIdTemp)) {
                game = Store.games.get(i);
            }
        }
        if(game != null){
            if(Auth.isAdmin){
                Store.removeGame(game.getGameId());
            }
            else{
                Auth.currentUser.removeGame(game.getGameId());
            }
            gameItem.setMaxHeight(0);
            gameItem.setMinHeight(0);
            gameItem.setPrefHeight(0);
            gameItem.setClip(new Rectangle(0, 0));
        }
    }
}
