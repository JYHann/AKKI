package com.example.sanhak3;

public class ManuFactureData {
    private String category;
    private float price = 0f;

    public ManuFactureData() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price += price;
    }
}
