package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    TextView title;
    TextView description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        bindView();
        setIntentData();
    }

    private void setIntentData() {
        Intent intent=getIntent();
        title.setText(intent.getStringExtra(AppConstant.TITLE));
        description.setText(intent.getStringExtra(AppConstant.DESCRIPTION));

    }

    private void bindView() {
        title=findViewById(R.id.TextViewTitle_Detail);
        description=findViewById(R.id.TextViewDescription_Detail);
    }
}
