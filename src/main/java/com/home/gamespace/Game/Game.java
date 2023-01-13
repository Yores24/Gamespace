package com.home.gamespace.Game;

import java.util.Date;

public class Game {
    private String gameId;
    private String name;
    private String gameTagLine;

    private String developerName;
    private Date releaseDate;
    private double rating;
    private double size;
    private double price;
    private double discount;
    private boolean isFreeForPrime;

    public Game() {
    };

    public Game(String gameId, String name, String gameTagLine, String developerName, double size, double rating,
            double price, double discount) {
        setGameId(gameId);
        setName(name);
        setGameTagLine(gameTagLine);
        setDeveloperName(developerName);
        setSize(size);
        setRating(rating);
        setPrice(price);
        setDiscount(discount);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setGameTagLine(String gameTagLine) {
        this.gameTagLine = gameTagLine;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    public void setReleaseDate(Date releaseDate) {

        this.releaseDate = releaseDate;
    }

    public void setRating(double rating) {

        this.rating = rating;

    }

    public void setSize(double size) {
        this.size = size;
    }

    public void setPrice(double price) {

        this.price = price;

    }

    public void setDiscount(double discount) {

        this.discount = discount;

    }

    public void setFreeForPrime(boolean freeForPrime) {
        isFreeForPrime = freeForPrime;
    }

    public String getName() {
        return name;
    }

    public String getGameId() {
        return gameId;
    }

    public String getGameTagLine() {
        return gameTagLine;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public double getRating() {
        return rating;
    }

    public double getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscount() {
        return discount;
    }

    public boolean isFreeForPrime() {
        return isFreeForPrime;
    }

    public String toString() {
        return
                "\nId : " + getGameId() +"\n\tName : " + getName()+ "\n\tTagLine : " + getGameTagLine()+ "\n\tDeveloperName : " + getDeveloperName()+"\n\tRating : " + getRating()+ "\n\tPrice : " + getPrice()+"\n\tDiscount : " + getDiscount()+ "\n\tSize : " + getSize();

    }
}
