package com.example.sanhak3;

import org.json.JSONArray;

public class OpenTransactionList {
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
    int page_record_cnt;
    char next_page_yn;
    String befor_inquiry_trace_info;
    JSONArray res_list;

    public String getApi_tran_id() { return api_tran_id; }

    public void setApi_tran_id(String api_tran_id) { this.api_tran_id = api_tran_id; }

    public String getRsp_code() { return rsp_code; }

    public void setRsp_code(String rsp_code) { this.rsp_code = rsp_code; }

    public String getRsp_message() { return rsp_message; }

    public void setRsp_message(String rsp_message) { this.rsp_message = rsp_message; }

    public String getApi_tran_dtm() { return api_tran_dtm; }

    public void setApi_tran_dtm(String api_tran_dtm) { this.api_tran_dtm = api_tran_dtm; }

    public String getBank_tran_id() { return bank_tran_id; }

    public void setBank_tran_id(String bank_tran_id) { this.bank_tran_id = bank_tran_id; }

    public int getBank_tran_date() { return bank_tran_date; }

    public void setBank_tran_date(int bank_tran_date) { this.bank_tran_date = bank_tran_date; }

    public int getBank_code_tran() { return bank_code_tran; }

    public void setBank_code_tran(int bank_code_tran) { this.bank_code_tran = bank_code_tran; }

    public int getBank_rsp_code() { return bank_rsp_code; }

    public void setBank_rsp_code(int bank_rsp_code) { this.bank_rsp_code = bank_rsp_code; }

    public String getBank_rsp_message() { return bank_rsp_message; }

    public void setBank_rsp_message(String bank_rsp_message) { this.bank_rsp_message = bank_rsp_message; }

    public String getFintech_use_num() { return fintech_use_num; }

    public void setFintech_use_num(String fintech_use_num) { this.fintech_use_num = fintech_use_num; }

    public int getBalance_amt() { return balance_amt; }

    public void setBalance_amt(int balance_amt) { this.balance_amt = balance_amt; }

    public int getPage_record_cnt() { return page_record_cnt; }

    public void setPage_record_cnt(int page_record_cnt) { this.page_record_cnt = page_record_cnt; }

    public char getNext_page_yn() { return next_page_yn; }

    public void setNext_page_yn(char next_page_yn) { this.next_page_yn = next_page_yn; }

    public String getBefor_inquiry_trace_info() { return befor_inquiry_trace_info; }

    public void setBefor_inquiry_trace_info(String befor_inquiry_trace_info) { this.befor_inquiry_trace_info = befor_inquiry_trace_info; }

    public JSONArray getRes_list() { return res_list; }

    public void setRes_list(JSONArray res_list) { this.res_list = res_list; }
}
