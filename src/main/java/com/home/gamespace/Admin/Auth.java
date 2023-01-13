package com.home.gamespace.Admin;

import com.home.gamespace.User.BasicUser;
import com.home.gamespace.User.User;
import javafx.util.Pair;

import java.util.ArrayList;

public class Auth {

    public static ArrayList<Pair<User, Boolean>> users = new ArrayList<Pair<User , Boolean> >();

    public static BasicUser currentUser;
    public static boolean isAdmin;

    public static void setAuthorization(BasicUser user, boolean isAdmin) {
        Auth.currentUser = user;
        Auth.isAdmin = isAdmin;

        /*
        initialize all values of user using values in database

        if(!isAdmin){
            String profilePhoto;
            double accountBalance;
            ArrayList<BasicUser> friends;
            ArrayList<Game> library;
            Auth.user = new BasicUser(profilePhoto,accountBalance,friends,library);
        }

         */
    }
}
