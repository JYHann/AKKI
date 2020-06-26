package com.example.sanhak3;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentBalance extends Fragment {

    View v;
    private TextView edtHtml;


    final static String openBalanceURL = "https://testapi.openbanking.or.kr/v2.0/account/balance/fin_num";

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_balance, container, false);
        edtHtml = (TextView) v.findViewById(R.id.balanceView);
        Log.d("인트", "" + Math.round(Math.random() * 10000000));

        new Thread() {
            public void run() {
                String btID = "T991605530U00" + Math.round(Math.random() * 10000000);
                OpenBalance ob = getOpenBalance("199160553057881514541922", btID, "20200429163540");
                Log.d("오픈밸런스", String.valueOf(ob.balance_amt));
                String openBalance = Integer.toString(ob.balance_amt);

                Bundle bun = new Bundle();
                bun.putString("HTML_BALANCE", openBalance);

                Message msg = handler.obtainMessage();
                msg.setData(bun);
                handler.sendMessage(msg);
            }
        }.start();



        return v;
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Bundle bun = msg.getData();
            String openBalance = bun.getString("HTML_BALANCE");
            edtHtml.setText(openBalance);
        }
    };

    public OpenBalance getOpenBalance(String finUseNum, String bankTranID, String tranDtime ) {
        OpenBalance OB = new OpenBalance();
        String urlString = openBalanceURL + "?fintech_use_num=" + finUseNum + "&bank_tran_id=" +
                bankTranID + "&tran_dtime=" + tranDtime;

        try {
            // call API by using HTTPURLConnection
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxMTAwNzUyNTcwIiwic2NvcGUiOlsiaW5xdWlyeSIsImxvZ2luIiwidHJhbnNmZXIiXSwiaXNzIjoiaHR0cHM6Ly93d3cub3BlbmJhbmtpbmcub3Iua3IiLCJleHAiOjE1OTgxODAwODAsImp0aSI6IjU0ODEyNTIwLWNkYzMtNDQ4ZS04OGUwLWRmYjJlMWU1ZTQ0OCJ9.mBLuXXRaY_Havg0-jMSrbwlrX4mLOSnpgOaM5WuMmn8");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            JSONObject json = new JSONObject(getStringFromInputStream(in));
            Log.d("오픈", json.toString());

            // parse JSON
            OB = parseJSON(json);
            OB.setFintech_use_num(finUseNum);
            OB.setBank_tran_id(bankTranID);
            OB.setApi_tran_dtm(tranDtime);


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

        return OB;
    }

    private OpenBalance parseJSON(JSONObject json) throws JSONException {
        OpenBalance OB = new OpenBalance();
        OB.setBalance_amt(json.getInt("balance_amt"));

        return OB;
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
}