package com.example.mm.exerciseActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
//////import android.widget.TimePicker;


import com.example.mm.homeActivity.Home;

import com.example.mm.R;

public class Exercise extends AppCompatActivity implements View.OnClickListener {
    TextView txt;
    ////////TimePicker txtTime;
    TextView txtQuestion;
    Button btnLeave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        this.txtQuestion = findViewById(R.id.txtQuestion);

        Bundle b = getIntent().getExtras();
        int value = -1; // or other values
        if(b != null)
            value = b.getInt("values");

        txt = findViewById(R.id.txtTime);
        txt.setText(Integer.toString(value));
        txt.setActivated(false);
        this.txtQuestion.setActivated(false);
        this.btnLeave = findViewById(R.id.btnLeave);

        //cose provate a fare: disabilitare testi domanda e tempo, prove con next e prev ceh non cambiano il testo
        //btnleave per tornare alla schermata home non va, txtquestion è plaintext, verificare cosa serve  effettivamente
        //settare i colori giusti per option/exercise
        //chiedere come si passa da una domanda all'altra nel modo migliore
        //fare prova in cui si passa da option a ex passando un testo qualsiasi e
        //domande relative a temi interessanti quali i porcellini d'india o capybara


    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnLeave:
                Intent intent = new Intent(this, Home.class);
                startActivity(intent);
                break;
            case R.id.btnNext:
                //prova
                this.txtQuestion.setText("Hai premuto prossimo");
                break;
            case R.id.btnPrev:
                //prova
                this.txtQuestion.setText("Hai premuto prossimo");

                break;
        }
    }
}