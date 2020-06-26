package com.example.sanhak3;

public class OpenBalance {
    String api_tran_id;
    String rsp_code;
    String rsp_message;
    String api_tran_dtm;
    String bank_tran_id;
    int bank_tran_date;
    int bank_code_tran;
    int bank_rsp_code;
    String bank_rsp_message;
    String fintech_use_num;
    int balance_amt;
    int available_amt;
    int account_type;
    String product_name;

    public String getApi_tran_id() { return api_tran_id; }
    public String getRsp_code() { return rsp_code; }
    public String getRsp_message() { return rsp_message; }
    public String getApi_tran_dtm() { return api_tran_dtm; }
    public String getBank_tran_id() { return bank_tran_id; }
    public int getBank_tran_date() { return bank_tran_date; }
    public int getBank_code_tran() { return bank_code_tran; }
    public int getBank_rsp_code() { return bank_rsp_code; }
    public String getBank_rsp_message() { return bank_rsp_message; }
    public String getFintech_use_num() { return fintech_use_num; }
    public int getBalance_amt() { return balance_amt; }
    public int getAvailable_amt() { return available_amt; }
    public int getAccount_type() { return account_type; }
    public String getProduct_name() { return product_name; }

    public void setApi_tran_id(String api_tran_id) { this.api_tran_id = api_tran_id; }
    public void setRsp_code(String rsp_code) { this.rsp_code = rsp_code; }
    public void setRsp_message(String rsp_message) { this.rsp_message = rsp_message; }
    public void setApi_tran_dtm(String api_tran_dtm) { this.api_tran_dtm = api_tran_dtm; }
    public void setBank_tran_id(String bank_tran_id) { this.bank_tran_id = bank_tran_id; }
    public void setBank_tran_date(int bank_tran_date) { this.bank_tran_date = bank_tran_date; }
    public void setBank_code_tran(int bank_code_tran) { this.bank_code_tran = bank_code_tran; }
    public void setBank_rsp_code(int bank_rsp_code) { this.bank_rsp_code = bank_rsp_code; }
    public void setBank_rsp_message(String bank_rsp_message) { this.bank_rsp_message = bank_rsp_message; }
    public void setFintech_use_num(String fintech_use_num) { this.fintech_use_num = fintech_use_num; }
    public void setBalance_amt(int balance_amt) { this.balance_amt = balance_amt; }
    public void setAvailable_amt(int available_amt) { this.available_amt = available_amt; }
    public void setAccount_type(int account_type) { this.account_type = account_type; }
    public void setProduct_name(String product_name) { this.product_name = product_name; }
}
