package com.example.sanhak3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class FragmentInout extends Fragment implements View.OnClickListener {
    private TextView edtHtml;

    final static String openInoutURL = "https://testapi.openbanking.or.kr/v2.0/account/transaction_list/fin_num";
    String tran_date;
    String inout_type;
    String print_content;
    String tran_amt;
    String after_balance_amt;
    String branch_name;
    ArrayList<nameserv> arr = new ArrayList<nameserv>();
    inoutAdapter adapter = new inoutAdapter() ;
    private FragmentManager fragmentManager;
    private FragmentInout2_1 fragmentA;

    private FragmentTransaction transaction;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inout,container,false);
        fragmentManager = getFragmentManager();

        /*Button fButton = view.findViewById(R.id.btn_fragment_inout2_2);
        fButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getActivity(), ActivityInout2_2.class);
                startActivity(intent);*/
        ListView listview ;
        listview = (ListView) view.findViewById(R.id.list_inout2_1);
        listview.setAdapter(adapter);

        // Adapter 생성

        // 리스트뷰 참조 및 Adapter달기

        // edtHtml = (TextView) view.findViewById(R.id.inoutView);


        new Thread() {
            public void run() {
                String btID = "T991605530U00" + Math.round(Math.random() * 10000000);
                OpenTransactionList otl = getOpenTransactionList("199160553057881514541922", btID, "20200526113233", 20190102, 20190104);
                Log.d("오픈거래내역", otl.res_list.toString());
                String openTransactionList = "";
                try {
                    for(int i=0;i<otl.res_list.length();i++) {
                        tran_date = otl.res_list.getJSONObject(i).get("tran_date").toString();
                        inout_type = otl.res_list.getJSONObject(i).get("inout_type").toString();
                        print_content = otl.res_list.getJSONObject(i).get("print_content").toString();
                        tran_amt = otl.res_list.getJSONObject(i).get("tran_amt").toString();
                        after_balance_amt = otl.res_list.getJSONObject(i).get("after_balance_amt").toString();
                        branch_name = otl.res_list.getJSONObject(i).get("branch_name").toString();
                        arr.add(new nameserv(print_content,Integer.parseInt(tran_amt),tran_date,inout_type));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                /*아거Bundle bun = new Bundle();
                bun.putString("HTML_TRANSACTION", openTransactionList);

                Message msg = handler.obtainMessage();
                msg.setData(bun);
                handler.sendMessage(msg);
                */
                SaveservData(arr);

            }
        }.start();
        ArrayList<nameserv> newarr = ReadservData();
        for(int i=0;i<newarr.size();i++)
        {
            adapter.addItem(newarr.get(i).date,newarr.get(i).name,newarr.get(i).price+"","우리은행");
        }


        return view;
    }

    @Override
    public void onClick(View view){

    }

   /* Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Bundle bun = msg.getData();
            String openTransactionList = bun.getString("HTML_TRANSACTION");
            edtHtml.setText(openTransactionList);
        }
    };*/



    public OpenTransactionList getOpenTransactionList(String finUseNum, String bankTranID, String tranDtime, int fDate, int tDate) {
        OpenTransactionList OTL = new OpenTransactionList();
        String urlString = openInoutURL + "?bank_tran_id=" + bankTranID + "&fintech_use_num=" +
                finUseNum + "&inquiry_type=A&inquiry_base=D&from_date=" + fDate + "&to_date=" + tDate +
                "&sort_order=D&tran_dtime=" + tranDtime + "&befor_inquiry_trace_info=1T201806172";

        try {
            // call API by using HTTPURLConnection
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxMTAwNzUyNTcwIiwic2NvcGUiOlsiaW5xdWlyeSIsImxvZ2luIiwidHJhbnNmZXIiXSwiaXNzIjoiaHR0cHM6Ly93d3cub3BlbmJhbmtpbmcub3Iua3IiLCJleHAiOjE1OTg4NzgxNDksImp0aSI6IjQzM2I2YzJiLWIyMDQtNGMxYi04NzM3LWEzNzcyYzNmYzcwOCJ9.075V1R3_2eutHSRSIC-3sx2AENp-tYRCmx7WAkA95X0");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            JSONObject json = new JSONObject(getStringFromInputStream(in));
            Log.d("오픈", json.toString());

            // parse JSON
            OTL = parseJSON(json);
            OTL.setFintech_use_num(finUseNum);
            OTL.setBank_tran_id(bankTranID);
            OTL.setApi_tran_dtm(tranDtime);

        } catch (MalformedURLException e) {
            System.err.println("Malformed URL");
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            System.err.println("JSON parsing error");
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            System.err.println("URL Connection failed");
            e.printStackTrace();
            return null;
        }

        return OTL;
    }

    private OpenTransactionList parseJSON(JSONObject json) throws JSONException {
        OpenTransactionList OTL = new OpenTransactionList();
        OTL.setRes_list(json.getJSONArray("res_list"));
        Log.d("오픈레스리스트", OTL.res_list.toString());

        return OTL;
    }

    private static String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;

        try {
            br = new BufferedReader(new InputStreamReader(is));
            while((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }
    private void SaveservData(ArrayList<nameserv> nameservs) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(nameservs);
        editor.putString("Mynameserv", json);
        editor.commit();
    }
    ArrayList<nameserv> ReadservData() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        String json = sharedPrefs.getString("Mynameserv", "EMPTY");
        Type type = new TypeToken<ArrayList<nameserv>>() {
        }.getType();
        ArrayList<nameserv> arrayList = gson.fromJson(json, type);
        return arrayList;
    }


}