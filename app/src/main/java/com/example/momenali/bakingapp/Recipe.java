package com.example.momenali.bakingapp;

/**
 * Created by Momen Ali on 12/13/2017.
 */

public class Recipe {
    int id;
    String name;
    String servings;
    String ImageURL;


    public Recipe() {
        id = -1;
    }

    public Recipe(int id, String name, String servings, String imageURL) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        ImageURL = imageURL;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
