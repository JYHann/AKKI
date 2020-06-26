package com.example.sanhak3;

public class InoutData {
    private String idx;
    private String category;
    private String price;
    private float totalPrice = 0f;

    public InoutData() {

    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
