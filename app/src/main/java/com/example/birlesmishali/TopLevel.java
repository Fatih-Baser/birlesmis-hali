package com.example.birlesmishali;

import java.util.List;

public class TopLevel {
    private String versionNumber;
    private Boolean isUpdated;
    private List<Category> categories;

    public String getVersionNumber() {
        return versionNumber;
    }

    public Boolean getUpdated() {
        return isUpdated;
    }

    public List<Category> getCategories() {
        return categories;
    }
}
