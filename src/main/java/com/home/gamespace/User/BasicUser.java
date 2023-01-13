package com.home.gamespace.User;

import com.home.gamespace.Database;
import com.home.gamespace.Game.Game;
import com.home.gamespace.User.changePassword;
import javafx.beans.property.SimpleStringProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;

public class BasicUser extends User implements changePassword {
    private String profilePhoto;
    private double accountBalance;

    private SimpleStringProperty bal;
    private ArrayList<BasicUser> friends = new ArrayList<>();
    private ArrayList<Game> library = new ArrayList<>();

    public BasicUser(){}

    public BasicUser(String userId,String password){
        super(userId,password);
    }

    public BasicUser(String profilePhoto,double accountBalance,ArrayList<BasicUser> friends, ArrayList<Game> library){
        this.setProfilePhoto(profilePhoto);
        this.setAccountBalance(accountBalance);
        this.setFriends(friends);
        this.setLibrary(library);
        this.bal.setValue(Double.toString(accountBalance));
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = round(accountBalance,2);
    }

    public ArrayList<BasicUser> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<BasicUser> friends) {
        this.friends = friends;
    }

    public ArrayList<Game> getLibrary() {
        return library;
    }

    public void setLibrary(ArrayList<Game> library) {
        this.library = library;
    }

    public void editProfile(){

    }

    public SimpleStringProperty getBal() {
        return bal;
    }

    public void setBal(String bal) {
        this.bal = new SimpleStringProperty();
        this.bal.setValue(bal);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public boolean changePassword(String oldPassword, String newPassword) throws SQLException {
        if(this.getPassword().equals(oldPassword)){
            this.setPassword(newPassword);
            Database.changePassword(this.getUserId(),newPassword);
            return true;
        }
        else {
            return false;
        }
    }

    public String buyGame(Game game) throws SQLException {
        if(library.contains(game)){
            return "Game already in library";
        }
        if(accountBalance>=game.getPrice()){
            this.library.add(game);
            //code for adding in database
            Database.buyGame(game,this);
            this.setAccountBalance(getAccountBalance() - game.getPrice());
            this.bal.setValue(Double.toString(accountBalance));
            return "Added to library" ;
        }
        else {
            return "Insufficient balance ";
        }
    }

    public void addToLibrary(Game game){
        library.add(game);
    }

    public void addToFriends(BasicUser user){
        friends.add(user);
    }

    public void removeGame(String gameId) throws SQLException {
        for(int i=0;i<library.size();i++){
            if(library.get(i).getGameId().equals(gameId)){
                library.remove(i);
                Database.removeFromLibrary(gameId);
            }
        }
    }

    public void addBalance(double amount) throws SQLException {
        Database.addBalance(amount);
        this.setAccountBalance(this.getAccountBalance() + amount);
        this.bal.setValue(Double.toString(accountBalance));
    }

    public void Gift(Game game , BasicUser friend){
        if(friend.library.contains(game)){
            System.out.println("Game already in library");
            return;
        }
        if(friend.accountBalance>=game.getPrice()){
            friend.library.add(game);

            //code for adding in database
        }
        else {
            System.out.println("Insufficient balance ");
        }
    }
}

