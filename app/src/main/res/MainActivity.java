package com.example.cyberclock;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mm.R;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    SeekBar barr;
    TextView value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        barr = findViewById(R.id.seekBar);
        barr.setOnSeekBarChangeListener(this);

        value = findViewById(R.id.value);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        value.setText(((Integer)progress).toString());
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        value.setTextColor(getResources().getColor(R.color.red));
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        value.setTextColor(getResources().getColor(R.color.green));
    }
}