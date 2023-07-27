package com.example.honestyshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BuyActivity extends AppCompatActivity {

     Button addButton;
     Button genButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        addButton = findViewById(R.id.AddButton);
        genButton = findViewById(R.id.genRepButton);
        genButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ReportActivity.class);
                startActivity(i);
            }
        });
    }
}