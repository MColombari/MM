package com.example.mm.exerciseActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.mm.R;

public class Exercise extends AppCompatActivity {
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        Bundle b = getIntent().getExtras();
        int value = -1; // or other values
        if(b != null)
            value = b.getInt("values");

        txt = findViewById(R.id.txtValue);
        txt.setText(Integer.toString(value));

    }
}