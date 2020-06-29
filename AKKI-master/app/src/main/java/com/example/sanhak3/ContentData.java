package com.example.sanhak3;

import java.io.Serializable;

public class ContentData implements Serializable {
    private String title;
    private String content;
    private String menuName;
    private int number;

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public String getMenuName() { return menuName; }

    public void setMenuName(String menuName) { this.menuName = menuName; }

    public int getNumber() { return number; }

    public void setNumber(int number) { this.number = number; }
}
