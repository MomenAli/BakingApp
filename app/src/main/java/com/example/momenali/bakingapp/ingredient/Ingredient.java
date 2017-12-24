package com.example.momenali.bakingapp.ingredient;

/**
 * Created by Momen Ali on 12/15/2017.
 */

public class Ingredient {


    /*
    the information i have about the ingredients is
    "quantity": ,
    "measure": ,
    "ingredient":
    */
    Double quantity;
    Measure measure;
    String ingredientName;


    public Ingredient() {
    }

    public Ingredient(Double quantity, Measure measure, String ingredientName) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredientName = ingredientName;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }


    /* enum represent the units to measure
    *  the ingredient of the recipe
    *  */
    public static enum Measure {
        CUP("CUP", 0),
        TBLSP("TBLSP", 1),
        TSP("TSP", 2),
        K("K", 3),
        G("G", 4),
        OZ("OZ", 5),
        UNIT("UNIT", 5);


        private String stringValue;
        private int intValue;

        private Measure(String toString, int value) {
            stringValue = toString;
            intValue = value;
        }


        @Override
        public String toString() {
            return stringValue;
        }
    }
}
