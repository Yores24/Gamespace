package com.home.gamespace;

import com.home.gamespace.Admin.Auth;
import com.home.gamespace.User.BasicUser;
import com.home.gamespace.User.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Pair;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginController {

    @FXML
    private TextField userIdField;

    @FXML
    private Label errorLabel;

    @FXML
    private PasswordField userPasswordField;

    @FXML

    protected void logInHandler(ActionEvent event) throws IOException, SQLException {
        String userId, userPass, adminId, adminPass;

        userId = userIdField.getText();
        userPass = userPasswordField.getText();

        ArrayList<Pair<User, Boolean>> users = Auth.users;

        boolean isAdmin = false;
        boolean isnull = userId.equals("") || userPass.equals("");

        boolean isValidUser = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getKey().getUserId().equals(userId)
                    && users.get(i).getKey().getPassword().equals(userPass)) {
                isValidUser = true;
                isAdmin = users.get(i).getValue();
            }
        }
        BasicUser user;

        if (isnull) {
            errorLabel.setText("User-ID or password is missing");
            return;

        }
        if (!isValidUser) {
            errorLabel.setText("User-ID or Password is Incorrect");
            return;
        }

        else {
            user = new BasicUser(userId, userPass);
            Auth.setAuthorization(user, isAdmin);
            Database.intializeFriends(user);
            Database.intializeLibrary(user);
            double bal = Database.getBalance(user);
            Auth.currentUser.setBal(Double.toString(bal));
        }

        FXMLLoader fxmlLoader;
        if (isAdmin) {
            fxmlLoader = new FXMLLoader(GameSpaceMain.class.getResource("Admin.fxml"));
        }

        else {
            fxmlLoader = new FXMLLoader(GameSpaceMain.class.getResource("User.fxml"));
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1310, 845);
        stage.setScene(scene);
        stage.show();

    }

}