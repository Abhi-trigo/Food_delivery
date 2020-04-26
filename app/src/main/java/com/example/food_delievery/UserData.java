package com.example.food_delievery;

public class UserData {
    private String name;
    private Integer user_rating;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public UserData(String name, Integer user_rating, String url, Double rating) {
        this.name = name;
        this.user_rating = user_rating;
        this.url = url;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUser_rating() {
        return user_rating;
    }

    public void setUser_rating(int user_rating) {
        this.user_rating = user_rating;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public UserData(String name, int user_rating, Double rating) {
        this.name = name;
        this.user_rating = user_rating;
        this.rating = rating;
    }

    private Double rating;

}
