package com.home.gamespace.Admin;


import com.home.gamespace.Database;
import com.home.gamespace.User.User;
import com.home.gamespace.User.changePassword;

import java.sql.SQLException;

public class Admin extends User implements changePassword {
    @Override
    public boolean changePassword(String oldPassword, String newPassword) throws SQLException {
        if(Auth.currentUser.getPassword().equals(oldPassword)){
            this.setPassword(newPassword);
            Database.changePassword(Auth.currentUser.getUserId(),newPassword);
            return true;
        }
        else {
            return false;
        }
    }
}
