package com.example.birlesmishali;

import java.util.List;

public class Category {
    private int id;
    private String name;
    private String order;
    private String image;
    private List<CategoryDetails> categoryDetails;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOrder() {
        return order;
    }

    public String getImage() {
        return image;
    }

    public List<CategoryDetails> getCategoryDetails() {
        return categoryDetails;
    }
}
