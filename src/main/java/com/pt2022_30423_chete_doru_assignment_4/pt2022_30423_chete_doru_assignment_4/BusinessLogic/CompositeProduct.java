package com.pt2022_30423_chete_doru_assignment_4.pt2022_30423_chete_doru_assignment_4.BusinessLogic;

import java.util.List;

/**
 * Class that models a composite product, can have more items.
 */
public class CompositeProduct extends MenuItem {
    private int id;
    private String title;
    private double rating;
    private int calories;
    private int protein;
    private int fat;
    private int sodium;
    private int price;
    private List<CompositeProduct> containedProducts;

    public CompositeProduct(int id, String title, double rating, int calories, int protein, int fat, int sodium, int price, List<CompositeProduct> containedProducts) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.sodium = sodium;
        this.price = price;
        this.containedProducts = containedProducts;
    }

    public void addProduct(CompositeProduct product) {
        containedProducts.add(product);
    }
    
    public void setRating(int rating) {
        for(CompositeProduct product: containedProducts) {
            product.setRating(rating);
        }
    }
    
    public int getSodium() {
        int sodium = 0;
        for(CompositeProduct product: containedProducts) {
            soidum += product.
        }
    }


