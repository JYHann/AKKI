package com.example.sanhak3;

import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

public class FragmentCard extends Fragment{
    ListView listView;
    ViewPager2 vp;
    ArrayList<cards> top3 = new ArrayList<cards>();
    ArrayList<cards> cardsArrayLIst = new ArrayList<cards>();
    ArrayList<nameserv> arr = new ArrayList<nameserv>();
    LinearLayout button;
    TextView btext;
    ImageView bim;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card,container,false);

        arr = ReadservData();
        button = (LinearLayout) view.findViewById(R.id.button);
        listView = (ListView) view.findViewById(R.id.list_view);
        btext = (TextView)view.findViewById(R.id.textView6);
        vp = (ViewPager2)view.findViewById(R.id.vp2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btext.getText().equals("더보기")) {
                    vp.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    btext.setText("닫기");
                }
                else
                {
                    vp.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                    btext.setText("더보기");
                }
            }
        });
        new GetCardAsync().execute();

        return view;
    }
    class GetCardAsync extends AsyncTask<Void, String, Void>
    {
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            algorithm();
            Collections.sort(cardsArrayLIst);
            cardAdapter adapter = new cardAdapter(cardsArrayLIst);
            for(int i=0;i<3;i++)
            {
                top3.add(cardsArrayLIst.get(i));
            }
            top3Adapter ta = new top3Adapter(top3);
            vp.setAdapter(ta);
            listView.setAdapter(adapter);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String url = "https://65rjhzgzwk.execute-api.ap-northeast-2.amazonaws.com/dynamo/dynamoget";
            String jsonData="";
            try {
                jsonData = Jsoup.connect(url).ignoreContentType(true).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            jsonData = jsonData.replaceAll("\\\\","");
            jsonData = jsonData.substring(1,jsonData.length()-1);
            Log.i("zzzzzzzz",jsonData);
            JSONObject jsonObject = null;
            cardsArrayLIst.clear();
            try {
                jsonObject = new JSONObject(jsonData);
                JSONArray cardArray = jsonObject.getJSONArray("Items");
                for(int i=0;i<cardArray.length();i++) {
                    cards temp = new cards();
                    JSONObject cardObject = cardArray.getJSONObject(i);
                    ArrayList<String> options = new ArrayList<String>();
                    Iterator<String> iter = cardObject.keys();
                    temp.Cname = cardObject.getString("Cname");
                    Log.i("Cnamezzz",temp.Cname);

                    temp.url = "https://akkicardimagebucket.s3.ap-northeast-2.amazonaws.com/"+ cardObject.getString("image");
                    while (iter.hasNext()) {
                        String b = iter.next().toString();
                        if (!b.equals("Cname")&&!b.equals("image")) {
                            temp.options.add(new pair(b, cardObject.getString(b)));
                        }
                    }
                    cardsArrayLIst.add(temp);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            publishProgress();
            return null;
        }
    }
    private ArrayList<nameserv> ReadservData() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        String json = sharedPrefs.getString("Mynameserv", "EMPTY");
        Type type = new TypeToken<ArrayList<nameserv>>(){
        }.getType();
        ArrayList<nameserv> arrayList = gson.fromJson(json, type);
        return arrayList;
    }
    public ArrayList<nameserv> algorithm(){
        for(int i=0;i<cardsArrayLIst.size();i++)
        {
            int sum=0;
            String cname = cardsArrayLIst.get(i).Cname;
            for(int j=0;j<arr.size();j++)
            {
                for(int k=0;k<cardsArrayLIst.get(i).options.size();k++)
                    if(arr.get(j).name.equals(cardsArrayLIst.get(i).options.get(k).key))
                    {
                        float per = Float.valueOf(cardsArrayLIst.get(i).options.get(k).value);
                        if(per<100)
                            sum+=per*arr.get(j).price/100;
                        else
                            sum+=per;
                    }
            }
            cardsArrayLIst.get(i).sum=sum;
        }
        return null;
    }
}