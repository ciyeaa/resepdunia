package com.difa.resepdunia;

public class Meal {

    String strMeal, strMealThumb, idMeal;

    public Meal(String idMeal, String strMeal, String strMealThumb) {
        this.strMeal = strMeal;
        this.strMealThumb = strMealThumb;
        this.idMeal = idMeal;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public String getIdMeal() {
        return idMeal;
    }
}