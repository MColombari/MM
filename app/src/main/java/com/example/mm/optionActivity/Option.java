package com.example.mm.optionActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mm.R;
import com.example.mm.exerciseActivity.Exercise;

public class Option extends AppCompatActivity implements View.OnClickListener {

    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, Exercise.class);
        Bundle b = new Bundle();
        b.putInt("values", 1);
        intent.putExtras(b);
        startActivity(intent);
    }
}