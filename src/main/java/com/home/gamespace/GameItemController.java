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
import javafx.scene.paint.Color;

import java.sql.SQLException;
import java.util.Objects;

public class GameItemController {

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
    private Button buyButton;

    @FXML
    private Label balance;

    @FXML
    private Label errorLabel;


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
            BackgroundImage bgimg = new BackgroundImage(img,BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(1.0, 1.0, true, true, false, false));

            Background bg = new Background(bgimg);
            image.setBackground(bg);
        }
        catch (NullPointerException error){
            System.out.println(game.getName());
        }
        Image img = new Image(Objects.requireNonNull(GameSpaceMain.class.getResourceAsStream(new String("images/game/"+game.getName()+".jpg"))));
        BackgroundImage bgimg = new BackgroundImage(img,BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, false));

        Background bg = new Background(bgimg);
        image.setBackground(bg);
    }

    public void buyGameHandler(ActionEvent event) throws SQLException {
        Game game = null;
        for (int i = 0; i< Store.noOfGames; i++) {
            if (Store.games.get(i).getGameId().equals(gameId.getText())) {
                game = Store.games.get(i);
            }
        }
        if(game != null){
            String ans = Auth.currentUser.buyGame(game);
            errorLabel.setText(ans);
            if(ans.equals("Added to library")){
                errorLabel.setTextFill(Color.rgb(144, 250, 77));
            }
            errorLabel.setVisible(true);
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            errorLabel.setVisible(false);
                            errorLabel.setTextFill(Color.rgb(255, 0, 0));
                        }
                    },
                    3000
            );
        }

    }
}
