package com.example.sanhak3;

public class ListviewCard {
    private String iconDrawable ;
    private String lankingStr;
    private String titleStr ;
    private String descStr ;

    public void setLaking(String laking){
        lankingStr = laking;
    }
    public void setIcon(String icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDesc(String desc) {
        descStr = desc ;
    }


    public String getIcon() {
        return this.iconDrawable ;
    }
    public String getLaking() {
        return this.lankingStr;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getDesc() {
        return this.descStr ;
    }
}
