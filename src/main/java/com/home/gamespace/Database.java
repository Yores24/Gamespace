package com.home.gamespace;

import com.home.gamespace.Admin.Auth;
import com.home.gamespace.Admin.Store;
import com.home.gamespace.Game.Game;
import com.home.gamespace.User.User;
import com.home.gamespace.User.BasicUser;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;


public class Database {
    public static void intializeDatabase() throws SQLException {
        String dbURL = "jdbc:mysql://localhost:3306/gameSpace";
        String username = "root";
        String password = "Surbhi@gmail.com";

        Connection conn = DriverManager.getConnection(dbURL, username, password);


        String query = "SELECT * FROM games";
        int count = 0;
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(query);
        while (result.next()) {
            String gameId = result.getString(1);
            String name = result.getString(2);
            String gameTagLine = result.getString(3);
            String developerName = result.getString(4);
            double size = result.getDouble(5);
            double price = result.getDouble(6);
            double rating = result.getDouble(7);
            double discount = result.getDouble(8);
            Game game = new Game(gameId,name,gameTagLine,developerName,size,rating,price,discount);
            Store.games.add(game);
            count++;
        }
        Store.noOfGames = count;

        query = "SELECT * FROM users";
        result = statement.executeQuery(query);
        while (result.next()) {
            String userId = result.getString(1);
            String pass = result.getString(2);
            boolean isAdmin = result.getBoolean(3);
            User user = new User(userId,pass);
            Auth.users.add(new Pair<>(user,isAdmin));
        }
    }

    public static void intializeLibrary(BasicUser user) throws SQLException {
        String dbURL = "jdbc:mysql://localhost:3306/gameSpace";
        String username = "root";
        String password = "Surbhi@gmail.com";

        Connection conn = DriverManager.getConnection(dbURL, username, password);


        String query = "SELECT * FROM library where UserID = (userId)";
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(query);
        while (result.next()) {
            Game game = null;
            String gameId = result.getString(1);
            for(int i=0;i<Store.games.size();i++){
                if(Store.games.get(i).getGameId().equals(gameId)){
                    game = Store.games.get(i);
                }
            }
            user.addToLibrary(game);
        }
    }

    public static void intializeFriends(BasicUser user) throws SQLException {
        String dbURL = "jdbc:mysql://localhost:3306/gameSpace";
        String username = "root";
        String password = "Surbhi@gmail.com";

        Connection conn = DriverManager.getConnection(dbURL, username, password);

        String userId = user.getUserId();

        String query = "SELECT * FROM friends where UserID = ?";
        PreparedStatement pstmt = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1,userId);
        ResultSet result = pstmt.executeQuery();
        while (result.next()) {
            BasicUser friend = null;
            String userID = result.getString(1);
            for(int i=0;i<Auth.users.size();i++){
                if(Auth.users.get(i).getKey().getUserId().equals(userID)){
                    friend = (BasicUser) Auth.users.get(i).getKey();
                }
            }
            user.addToFriends(friend);
        }
    }

    public static double getBalance(BasicUser user) throws SQLException {
        String dbURL = "jdbc:mysql://localhost:3306/gameSpace";
        String username = "root";
        String password = "Surbhi@gmail.com";

        Connection conn = DriverManager.getConnection(dbURL, username, password);

        String userId = user.getUserId();

        String query = "SELECT Balance FROM users where UserID = ?";
        PreparedStatement pstmt = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1,userId);
        ResultSet result = pstmt.executeQuery();
        result.next();
        double balance = result.getDouble(1);
        user.setAccountBalance(balance);
        return BasicUser.round(balance,2);
    }

    public static boolean addBalance(double amount) throws SQLException {
        String dbURL = "jdbc:mysql://localhost:3306/gameSpace";
        String username = "root";
        String password = "Surbhi@gmail.com";

        Connection conn = DriverManager.getConnection(dbURL, username, password);

        String userId = Auth.currentUser.getUserId();

        String query = "UPDATE users set Balance = ? where UserID = ?";
        PreparedStatement pstmt = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
        pstmt.setDouble(1,amount + Auth.currentUser.getAccountBalance());
        pstmt.setString(2,userId);
        pstmt.executeUpdate();
        return true;
    }

    public static void addGame(Game game) throws SQLException {
        String dbURL = "jdbc:mysql://localhost:3306/gameSpace";
        String username = "root";
        String password = "Surbhi@gmail.com";

        Connection conn = DriverManager.getConnection(dbURL, username, password);


        String gameId = game.getGameId();
        String name = game.getName();
        String tagLine = game.getGameTagLine();
        String developerName = game.getDeveloperName();
        double rating = game.getRating();
        double size = game.getSize();
        double price = game.getPrice();
        double discount = game.getDiscount();

        String query = "insert into games() values(?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, gameId);
        pstmt.setString(2, name);
        pstmt.setString(3, tagLine);
        pstmt.setString(4, developerName);
        pstmt.setDouble(5, rating);
        pstmt.setDouble(6, size);
        pstmt.setDouble(7, price);
        pstmt.setDouble(8, discount);

        pstmt.executeUpdate();
    }

    public static void removeGame(String gameId) throws SQLException {
        String dbURL = "jdbc:mysql://localhost:3306/gameSpace";
        String username = "root";
        String password = "Surbhi@gmail.com";

        Connection conn = DriverManager.getConnection(dbURL, username, password);

        String query = "delete from games where GameID =?";
        PreparedStatement pstmt = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1,gameId);
        pstmt.executeUpdate();
    }

    public static void removeFromLibrary(String gameId) throws SQLException {
        String dbURL = "jdbc:mysql://localhost:3306/gameSpace";
        String username = "root";
        String password = "Surbhi@gmail.com";

        Connection conn = DriverManager.getConnection(dbURL, username, password);

        String query = "delete from library where GameID =?";
        PreparedStatement pstmt = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1,gameId);
        pstmt.executeUpdate();
    }

    public static void buyGame(Game game, BasicUser user) throws SQLException {
        String dbURL = "jdbc:mysql://localhost:3306/gameSpace";
        String username = "root";
        String password = "Surbhi@gmail.com";

        Connection conn = DriverManager.getConnection(dbURL, username, password);

        String query = "insert into library values(?,?)";
        PreparedStatement pstmt = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1,game.getGameId());
        pstmt.setString(2,user.getUserId());
        pstmt.executeUpdate();

        query = "Update users set balance=? where UserID = ?";
        pstmt = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
        double price = game.getPrice() - game.getDiscount()* game.getPrice()/100;
        pstmt.setString(1,Double.toString(user.getAccountBalance() - price));
        pstmt.setString(2,user.getUserId());
        pstmt.executeUpdate();
    }

    public static void changePassword(String userId, String newPassword) throws SQLException {
        String dbURL = "jdbc:mysql://localhost:3306/gameSpace";
        String username = "root";
        String password = "Surbhi@gmail.com";

        Connection conn = DriverManager.getConnection(dbURL, username, password);

        String query = "UPDATE users set Password = ? where UserID = ?";
        PreparedStatement pstmt = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1,newPassword);
        pstmt.setString(2,userId);
        pstmt.executeUpdate();
    }

    public static void addGameUsingCsv(){
        String jdbcURL = "jdbc:mysql://localhost:3306/gameSpace";
        String username = "root";
        String password = "Surbhi@gmail.com";

        String csvFilePath = "C:\\gameSpace\\src\\main\\java\\com\\home\\gamespace\\Game\\GAMES.csv";

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);

            String sql = "INSERT INTO games VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;

            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");
                String gameId = data[0];
                String name = data[1];
                String tagLine = data[2];
                String developerName = data[3];
                Double size = Double.parseDouble(data[4]);
                Double price = Double.parseDouble(data[5]);
                Double discount = Double.parseDouble(data[6]);
                Double rating = Double.parseDouble(data[7]);


                statement.setString(1, gameId);
                statement.setString(2, name);
                statement.setString(3, tagLine);
                statement.setString(4, developerName);
                statement.setDouble(5, size);
                statement.setDouble(6, price);
                statement.setDouble(7, rating);
                statement.setDouble(8, discount);

                statement.addBatch();

                statement.executeBatch();
                Game game = new Game(gameId,name,tagLine,developerName,size,rating,price,discount);
                System.out.println(game);
            }

            lineReader.close();

            connection.commit();
            connection.close();

        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SQLException ex) {
            ex.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateGameUsingCsv(){
        String jdbcURL = "jdbc:mysql://localhost:3306/gameSpace";
        String username = "root";
        String password = "Surbhi@gmail.com";

        String csvFilePath = "C:\\gameSpace\\src\\main\\java\\com\\home\\gamespace\\Game\\GAMES.csv";

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);

            String sql = "INSERT INTO games VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            String sql1 = "UPDATE games set  Name = ?, TagLine = ?, DeveloperName = ?, Size = ?, Price = ?, Rating = ?, Discount = ? where gameId = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement1 = connection.prepareStatement(sql1);


            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;

            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");
                String gameId = data[0];
                String name = data[1];
                String tagLine = data[2];
                String developerName = data[3];
                Double size = Double.parseDouble(data[4]);
                Double price = Double.parseDouble(data[5]);
                Double discount = Double.parseDouble(data[6]);
                Double rating = Double.parseDouble(data[7]);


                boolean alreadyExist = false;
                for(int i=0;i<Store.noOfGames;i++){
                    if(Store.games.get(i).getGameId().equals(gameId)){
                        alreadyExist = true;
                    }
                }

                if(alreadyExist){
                    statement1.setString(1, name);
                    statement1.setString(2, tagLine);
                    statement1.setString(3, developerName);
                    statement1.setDouble(4, size);
                    statement1.setDouble(5, price);
                    statement1.setDouble(6, rating);
                    statement1.setDouble(7, discount);
                    statement1.setString(8, gameId);


                    statement1.addBatch();

                    statement1.executeBatch();
                }
                else{
                    statement.setString(1, gameId);
                    statement.setString(2, name);
                    statement.setString(3, tagLine);
                    statement.setString(4, developerName);
                    statement.setDouble(5, size);
                    statement.setDouble(6, price);
                    statement.setDouble(7, rating);
                    statement.setDouble(8, discount);

                    statement.addBatch();

                    statement.executeBatch();
                }
                Game game = new Game(gameId,name,tagLine,developerName,size,rating,price,discount);
                System.out.println(game);

            }

            lineReader.close();

            connection.commit();
            connection.close();

        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SQLException ex) {
            ex.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

