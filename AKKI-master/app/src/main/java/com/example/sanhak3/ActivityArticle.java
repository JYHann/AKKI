package com.example.sanhak3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.openqa.selenium.json.Json;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityArticle extends AppCompatActivity {
    private ServiceApiCommunity service;
    private TextView arcMenu;
    private TextView arcTitle;
    private TextView arcContent;
    private EditText arcComment;
    private Intent intentMenu;
    private int articleNumber;
    private ArrayList<String> commentList = new ArrayList<String>();
    private ListView listView;
    private Button btnComment;
    private ContentData contentData = new ContentData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        service = RetrofitClient.getClient().create(ServiceApiCommunity.class);

        arcMenu = findViewById(R.id.arcMenu);
        arcTitle = findViewById(R.id.arcTitle);
        arcContent = findViewById(R.id.arcContent);
        arcComment = findViewById(R.id.arcComment);
        btnComment = findViewById(R.id.btn_comment);

        intentMenu = getIntent();
        arcMenu.setText(intentMenu.getStringExtra("menu")); // '인기게시판'에서 양 옆에 ', ' 떼기
        articleNumber = intentMenu.getIntExtra("articleNumber", 0);
        // arcTitle, arcContent setText
        attemptLoadArticle(articleNumber);

        listView = (ListView) findViewById(R.id.commentListview);
        attemptLoadComment(articleNumber);

        btnComment.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                contentData.setContent(arcComment.getText().toString());
                contentData.setNumber(articleNumber);
                attemptNewComment(contentData);
            }
        });
    }

    private void attemptNewComment(ContentData comment) {
//        JsonObject json = new JsonObject();
//        json.addProperty("comment", comment);
        service.newComment(comment).enqueue(new Callback<ContentNewResponse>() {
            @Override
            public void onResponse(Call<ContentNewResponse> call, Response<ContentNewResponse> response) {
                ContentNewResponse result = response.body();
                Toast.makeText(ActivityArticle.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("댓글 작성 메세지", result.getMessage());
                arcComment.clearComposingText();
            }

            @Override
            public void onFailure(Call<ContentNewResponse> call, Throwable t) {
                Toast.makeText(ActivityArticle.this, "댓글 작성 에러", Toast.LENGTH_SHORT).show();
                Log.e("댓글 작성 에러", t.getMessage());
                t.printStackTrace();
            }
        });
    }

    private void attemptLoadArticle(int aNum) {
        JsonObject json = new JsonObject();
        json.addProperty("articleNumber", aNum);
        service.getArticle(json).enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                ArticleResponse result = response.body();
                Toast.makeText(ActivityArticle.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("글 불러오기 메세지", result.getMessage());
                arcTitle.setText(result.getArticle().get(0).get("title").getAsString());
                arcContent.setText(result.getArticle().get(0).get("content").getAsString());
            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                Toast.makeText(ActivityArticle.this, "글 불러오기 에러", Toast.LENGTH_SHORT).show();
                Log.e("글 불러오기 에러", t.getMessage());
                t.printStackTrace();
            }
        });
    }

    private void attemptLoadComment(int aNum) {
        JsonObject json = new JsonObject();
        json.addProperty("articleNumber", aNum);
        service.getComment(json).enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                CommentResponse result = response.body();
                ArrayList<JsonObject> commentArray = result.getComment();
                Toast.makeText(ActivityArticle.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("댓글 불러오기 메세지", result.getMessage());
                for(int i = 0; i < commentArray.size(); i++) {
                    commentList.add(commentArray.get(i).get("comment").getAsString());
                }
                MyAdapter myAdapter = new MyAdapter(ActivityArticle.this, commentList);
                listView.setAdapter(myAdapter);
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                Toast.makeText(ActivityArticle.this, "글 불러오기 에러", Toast.LENGTH_SHORT).show();
                Log.e("글 불러오기 에러", t.getMessage());
                t.printStackTrace();
            }
        });
    }

    class MyAdapter extends BaseAdapter {
        Context mContext = null;
        ArrayList<String> mData;
        LayoutInflater mLayoutInflater = null;

        public MyAdapter(Context context, ArrayList<String> data) {
            mContext = context;
            mData = data;
            mLayoutInflater = LayoutInflater.from(mContext);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = mLayoutInflater.inflate(R.layout.item_comu_comment, null);
            TextView comment = (TextView) view.findViewById(R.id.comment_textview);
            comment.setText(mData.get(position));
            return view;
        }

        @Override
        public int getCount() { return mData.size(); }

        @Override
        public Object getItem(int position) { return mData.get(position); }

        @Override
        public long getItemId(int position) { return position; }
    }
}
