package com.example.sanhak3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityNewArticle extends AppCompatActivity {
    private ServiceApiCommunity service;
    private TextView newArcMenu;
    private EditText newArcTitle;
    private EditText newArcContent;
    private Button newArcButton;
    private Intent intentMenu;
    private String menuName;
    private ContentData contentData = new ContentData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newarticle);

        service = RetrofitClient.getClient().create(ServiceApiCommunity.class);

        newArcMenu = findViewById(R.id.newArcMenu);
        newArcTitle = (EditText) findViewById(R.id.newArcTitle);
        newArcContent = (EditText) findViewById(R.id.newArcContent);
        newArcButton = findViewById(R.id.newArcButton);

        intentMenu = getIntent();
        menuName = intentMenu.getStringExtra("menu");
        Log.d("메뉴", menuName);
        newArcMenu.setText(menuName);

        newArcButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // 작성한 글 제목, 내용 RDS에 저장
                contentData.setTitle(newArcTitle.getText().toString());
                contentData.setContent(newArcContent.getText().toString());
                contentData.setMenuName(menuName);

                attemptNewArticle(contentData);
//                finish();
            }
        });
    }

    private void attemptNewArticle(ContentData contentData) {
        service.newArticle(contentData).enqueue(new Callback<ContentNewResponse>() {
            @Override
            public void onResponse(Call<ContentNewResponse> call, Response<ContentNewResponse> response) {
                ContentNewResponse result = response.body();
                Log.d("실행 결과", result.getMessage());

                if (result.getCode() == 200) {
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ContentNewResponse> call, Throwable t) {
                Toast.makeText(ActivityNewArticle.this, "글 작성 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("글 작성 에러 발생", t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
