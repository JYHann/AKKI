package com.example.sanhak3;
import android.graphics.drawable.Drawable;
public class ListviewInout {
    // 아이템 타입을 구분하기 위한 type 변수.
    private int type ;

    private String categoryStr ;
    private String comnameStr ;
    private String moneyStr ;
    private String bankStr ;

    private String date2Str ;
    private String inputStr ;
    private String outputStr ;

    public void setType(int type) {
        this.type = type ;
    }
    public void setCategory(String category) {
        categoryStr = category ;
    }
    public void setComname(String comname) {
        comnameStr = comname ;
    }
    public void setMoney(String money) {
        moneyStr = money ;
    }
    public void setBank(String bank) {
        bankStr = bank ;
    }
    public void setDate2(String date2) {
        date2Str = date2 ;
    }
    public void setInput(String input) {
        inputStr = input ;
    }
    public void setOutput(String output) {
        outputStr = output;
    }


    public int getType() {
        return this.type ;
    }

    public String getCategory() {
        return this.categoryStr ;
    }
    public String getComname() {
        return this.comnameStr ;
    }
    public String getMoney() {
        return this.moneyStr ;
    }
    public String getBank() {
        return this.bankStr ;
    }
    public String getDate2() {
        return this.date2Str ;
    }
    public String getInput() {
        return this.inputStr ;
    }
    public String getOutput() {
        return this.outputStr ;
    }

}
