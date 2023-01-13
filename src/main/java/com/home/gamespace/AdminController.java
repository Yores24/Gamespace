package com.home.gamespace;

import com.home.gamespace.Admin.Auth;
import com.home.gamespace.Admin.Store;
import com.home.gamespace.Game.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private TextField addGameName;
    @FXML
    private TextField addGameId;
    @FXML
    private TextField addGamePrice;
    @FXML
    private TextField addGameTagLine;
    @FXML
    private TextField addGameDeveloperName;
    @FXML
    private TextField addGameRating;
    @FXML
    private TextField addGameDiscount;
    @FXML
    private TextField removeGameId;
    @FXML
    private PasswordField adminPassword;

    @FXML
    private PasswordField adminPassword1;

    @FXML
    private PasswordField oldPassword;

    @FXML
    private PasswordField newPassword;

    @FXML
    private Label errorLabel;

    @FXML
    private Label changePassErrorLabel;

    @FXML
    private Label errorLabel1;
    @FXML
    private VBox showGamesPanel;

    @FXML
    private VBox addGamePanel;

    @FXML
    private VBox removeGamePanel;

    @FXML
    private VBox gamesLayout;

    @FXML
    private VBox addUsingCsvPanel;

    @FXML
    private VBox updateUsingCsvPanel;

    @FXML
    private VBox changePasswordPanel;



    public void addGameHandler() throws Exception {
        try {
            String name = addGameName.getText();
            String id = addGameId.getText();

            double price = Double.parseDouble(addGamePrice.getText());
            String tagline = addGameTagLine.getText();
            String devName = addGameDeveloperName.getText();
            double rating = Double.parseDouble(addGameRating.getText());
            double discount = Double.parseDouble(addGameDiscount.getText());
            double size = 23;

            Game newGame = new Game(id, name, tagline, devName, size, rating, price, discount);
            Store.addGame(newGame);

            clearField();

        } catch (Exception e) {
            System.out.println("Something is wrong");
        }
    }

    public void addGameCsv() {
        String adminPass = adminPassword.getText();
        if(adminPass.equals(Auth.currentUser.getPassword())){
            Database.addGameUsingCsv();
            errorLabel.setText("Data added successfully");
            errorLabel.setTextFill(Color.rgb(144, 250, 77));
            errorLabel.setVisible(true);

        }
        else
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

    public void updateGameCsv(){
        String adminPass = adminPassword1.getText();
        if(adminPass.equals(Auth.currentUser.getPassword())){
            Database.updateGameUsingCsv();
            errorLabel1.setText("Data updated successfully");
            errorLabel1.setTextFill(Color.rgb(144, 250, 77));
            errorLabel1.setVisible(true);

        }
        else
            errorLabel1.setVisible(true);

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        errorLabel1.setVisible(false);
                        errorLabel1.setTextFill(Color.rgb(255, 0, 0));
                    }
                },
                3000
        );
    }

    public void changePassword() throws SQLException {
        String oldPass = oldPassword.getText();
        String newPass = newPassword.getText();

        boolean isChanged = Auth.currentUser.changePassword(oldPass,newPass);
        if(!isChanged){
            changePassErrorLabel.setText("Password is incorrect");
            changePassErrorLabel.setVisible(true);
        }
        else{
            changePassErrorLabel.setText("Password Changed Successfully");
            changePassErrorLabel.setTextFill(Color.rgb(144, 250, 77));
            changePassErrorLabel.setVisible(true);
        }
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        changePassErrorLabel.setVisible(false);
                        changePassErrorLabel.setTextFill(Color.rgb(255, 0, 0));
                    }
                },
                3000
        );
    }

    public void clearField() {
        addGameName.clear();
        addGameId.clear();
        addGamePrice.clear();
        addGameDiscount.clear();
        addGameRating.clear();
        addGameDeveloperName.clear();
        addGameTagLine.clear();
        removeGameId.clear();
    }


    public void removeGameHandler() {
        try {
            String id = removeGameId.getText();
            String reason;
            Store.removeGame(id);
            clearField();
        } catch (Exception e) {
            System.out.println("Check again");
        }
    }

    public void addCsvHandler(){
        if (addGamePanel.isVisible())
            addGamePanel.setVisible(false);
        if (removeGamePanel.isVisible())
            removeGamePanel.setVisible(false);
        if (showGamesPanel.isVisible())
            showGamesPanel.setVisible(false);
        if (updateUsingCsvPanel.isVisible())
            updateUsingCsvPanel.setVisible(false);
        if (changePasswordPanel.isVisible())
            changePasswordPanel.setVisible(false);
        addUsingCsvPanel.setVisible(true);
    }

    public void updateCsvHandler(){
        if (addGamePanel.isVisible())
            addGamePanel.setVisible(false);
        if (removeGamePanel.isVisible())
            removeGamePanel.setVisible(false);
        if (addUsingCsvPanel.isVisible())
            addUsingCsvPanel.setVisible(false);
        if (showGamesPanel.isVisible())
            showGamesPanel.setVisible(false);
        if (changePasswordPanel.isVisible())
            changePasswordPanel.setVisible(false);
        updateUsingCsvPanel.setVisible(true);
    }


    public void showGamesPanelHandler() {
        if (addGamePanel.isVisible())
            addGamePanel.setVisible(false);
        if (removeGamePanel.isVisible())
            removeGamePanel.setVisible(false);
        if (addUsingCsvPanel.isVisible())
            addUsingCsvPanel.setVisible(false);
        if (updateUsingCsvPanel.isVisible())
            updateUsingCsvPanel.setVisible(false);
        if (changePasswordPanel.isVisible())
            changePasswordPanel.setVisible(false);
        showGamesPanel.setVisible(true);
    }

    public void addGamePanelHandler() {
        if (removeGamePanel.isVisible())
            removeGamePanel.setVisible(false);
        if (showGamesPanel.isVisible())
            showGamesPanel.setVisible(false);
        if (addUsingCsvPanel.isVisible())
            addUsingCsvPanel.setVisible(false);
        if (updateUsingCsvPanel.isVisible())
            updateUsingCsvPanel.setVisible(false);
        if (changePasswordPanel.isVisible())
            changePasswordPanel.setVisible(false);
        addGamePanel.setVisible(true);
    }

    public void removeGamePanelHandler() {
        if (addGamePanel.isVisible())
            addGamePanel.setVisible(false);
        if (showGamesPanel.isVisible())
            showGamesPanel.setVisible(false);
        if (addUsingCsvPanel.isVisible())
            addUsingCsvPanel.setVisible(false);
        if (updateUsingCsvPanel.isVisible())
            updateUsingCsvPanel.setVisible(false);
        if (changePasswordPanel.isVisible())
            changePasswordPanel.setVisible(false);
        removeGamePanel.setVisible(true);
    }

    public void changePasswordPanelHandler(){
        if (addGamePanel.isVisible())
            addGamePanel.setVisible(false);
        if (showGamesPanel.isVisible())
            showGamesPanel.setVisible(false);
        if (addUsingCsvPanel.isVisible())
            addUsingCsvPanel.setVisible(false);
        if (updateUsingCsvPanel.isVisible())
            updateUsingCsvPanel.setVisible(false);
        if (removeGamePanel.isVisible())
            removeGamePanel.setVisible(false);
        changePasswordPanel.setVisible(true);
    }

    public void logOutHandler(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(int i=0;i<Store.games.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Library_Game_item.fxml"));

            HBox hbox = null;
            try {
                hbox = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            hbox.setMinHeight(200);
            hbox.setMaxWidth(1030);
            LibraryGameItemController lgic = fxmlLoader.getController();
            lgic.setData(Store.games.get(i));
            gamesLayout.getChildren().add(hbox);
        }
    }
}
