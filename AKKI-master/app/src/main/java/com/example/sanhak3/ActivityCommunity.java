package com.example.sanhak3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityCommunity extends AppCompatActivity {
    private Intent intent;
    private String title;
    private TextView textView_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        intent = getIntent();
        title = intent.getStringExtra("menu");
        textView_title = findViewById(R.id.title_honey);
        textView_title.setText(title);
        Log.d("타이틀", title);
    }
}
