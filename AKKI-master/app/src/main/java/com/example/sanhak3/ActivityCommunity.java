package com.example.sanhak3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCommunity extends AppCompatActivity {
    private ServiceApiCommunity service;
    private Intent intent;
    private Intent intentNewArticle;
    private String title;
    private TextView textView_title;
    private RecyclerView recyclerView;
    List<JsonObject> comu_content_array = new ArrayList<>();
    List<ContentData> comu_content_list = new ArrayList<>();
    FloatingActionButton fab;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        service = RetrofitClient.getClient().create(ServiceApiCommunity.class);

        intent = getIntent();
        title = intent.getStringExtra("menu");
        textView_title = findViewById(R.id.title_honey);
        textView_title.setText(title);
        Log.d("타이틀", title);

        context = this;
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentNewArticle = new Intent(context, ActivityNewArticle.class);
                intentNewArticle.putExtra("menu", title);
                startActivity(intentNewArticle);
            }
        });

        recyclerView = findViewById(R.id.honey_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        attemptLoadContent(title);
    }

    private void attemptLoadContent(final String title) {
        // 리사이클러뷰 어댑터 내용 부착해야 됨
        int articleNumber;
        JsonObject json = new JsonObject();
        json.addProperty("menuName", title);
        service.getContent(json).enqueue(new Callback<ContentResponse>() {
            @Override
            public void onResponse(Call<ContentResponse> call, Response<ContentResponse> response) {
                comu_content_list.clear();
                ContentResponse result = response.body();
//                Toast.makeText(getActivity(), result.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("콘텐츠메세지", result.getMessage());
                comu_content_array = result.getContentlist();
                Log.d("콘텐츠Array", comu_content_array.toString());
                for(int i = 0; i < comu_content_array.size(); i++) {
                    ContentData content = new ContentData();
                    content.setTitle(comu_content_array.get(i).get("title").getAsString());
                    content.setContent(comu_content_array.get(i).get("content").getAsString());
                    content.setNumber(comu_content_array.get(i).get("contentID").getAsInt());
//                    Log.d("콘텐츠하나", content.getTitle());

                    comu_content_list.add(content);
                }
                Log.d("콘텐츠리스트", comu_content_list.toString());
                recyclerView.setAdapter(new AdapterCommunity2(comu_content_list, context, title));
            }

            @Override
            public void onFailure(Call<ContentResponse> call, Throwable t) {
//                Toast.makeText(getActivity(), "메뉴 로딩 중 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("콘텐츠 로딩 중 에러 발생", t.getMessage());
            }
        });
    }
}
