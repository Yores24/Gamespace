package com.home.gamespace.Admin;

import com.home.gamespace.Database;
import com.home.gamespace.Game.Game;

import java.sql.SQLException;
import java.util.ArrayList;

public class Store {
    public static ArrayList<Game> games = new ArrayList<>();
    public static int noOfGames;
//    private static double[] totalPurchases = new double[30];


    public Store(){
        //initialize games at start take games from database
    }

    public static void addGame(Game game) throws SQLException {
        boolean isAlreadyPresent = false;
        for (int i=0;i<noOfGames;i++) {
            if(games.get(i).getGameId().equals(game.getGameId())){
                isAlreadyPresent = true;
            }
        }

        if(!isAlreadyPresent){
            games.add(game);
            noOfGames = noOfGames + 1;
            Database.addGame(game);
        }
        else{
            System.out.println("Game already exists ");
        }


    }

    public static void removeGame(String gameId) throws SQLException {
        for (int i=0;i<noOfGames;i++) {
            if (games.get(i).getGameId().equals(gameId)) {
                games.remove(i);
                noOfGames--;
                System.out.println("Game removed");
                Database.removeGame(gameId);
            }
        }
    }
}
