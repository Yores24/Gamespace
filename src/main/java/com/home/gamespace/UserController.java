package com.home.gamespace;

import com.home.gamespace.Admin.Auth;
import com.home.gamespace.Admin.Store;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

public class UserController implements Initializable{

    private Stage stage;
    private Scene scene;


    @FXML
    private Label userId;

    @FXML
    private Label balance;

    @FXML
    private Label changePassErrorLabel;


    @FXML
    private Button buyGame;

    @FXML
    private VBox gamesLayout;

    @FXML
    private VBox libraryLayout;

    @FXML
    private VBox store;

    @FXML
    private VBox friends;

    @FXML
    private VBox library;

    @FXML
    private VBox addBalancePanel;

    @FXML
    private TextField addBalanceTextField;

    @FXML
    private Label addBalanceMessage;

    @FXML
    private PasswordField oldPassword;

    @FXML
    private PasswordField newPassword;
    @FXML
    private VBox changePasswordPanel;





    public void logOutHandler(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
        stage =  (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public void addBalance() throws SQLException {
        double amount = Double.parseDouble(addBalanceTextField.getText());
        Auth.currentUser.addBalance(amount);
        addBalanceMessage.setVisible(true);
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



    public void showLibrary(){
        if(store.isVisible()){
            store.setVisible(false);
        }
        if(addBalancePanel.isVisible()){
            addBalancePanel.setVisible(false);
        }
        if(changePasswordPanel.isVisible()){
            changePasswordPanel.setVisible(false);
        }
        library.setVisible(true);

        libraryLayout.getChildren().clear();
        for(int i=0;i<Auth.currentUser.getLibrary().size();i++){
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
            lgic.setData(Auth.currentUser.getLibrary().get(i));
            libraryLayout.getChildren().add(hbox);
        }
    }

    public void showStore() throws IOException {
        if(library.isVisible()){
            library.setVisible(false);
        }
        if(addBalancePanel.isVisible()){
            addBalancePanel.setVisible(false);
        }
        if(changePasswordPanel.isVisible()){
            changePasswordPanel.setVisible(false);
        }
        store.setVisible(true);
    }

    public void showAddBalancePanel(){
        if(library.isVisible()){
            library.setVisible(false);
        }
        if(store.isVisible()){
            store.setVisible(false);
        }
        if(changePasswordPanel.isVisible()){
            changePasswordPanel.setVisible(false);
        }
        addBalancePanel.setVisible(true);
    }
    public void changePasswordPanelHandler(){
        if (store.isVisible())
            store.setVisible(false);
        if (library.isVisible())
            library.setVisible(false);
        if (addBalancePanel.isVisible())
            addBalancePanel.setVisible(false);
        changePasswordPanel.setVisible(true);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userId.setText(Auth.currentUser.getUserId());
        balance.textProperty().bind(Auth.currentUser.getBal());

        for(int i = 0; i< Store.games.size(); i++){
          FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Game_item.fxml"));

            HBox hbox = null;
            try {
                hbox = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            hbox.setMinHeight(200);
            hbox.setMaxWidth(1030);
            GameItemController gic = fxmlLoader.getController();
            gic.setData(Store.games.get(i));
            gamesLayout.getChildren().add(hbox);
        }
    }


}
