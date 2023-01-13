package com.home.gamespace.User;

import java.sql.SQLException;

public interface changePassword {
    public boolean changePassword(String oldPass, String newPass) throws SQLException;
}
