package com.example.mm.exerciseActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;


import com.example.mm.homeActivity.Home;

import com.example.mm.R;

public class Exercise extends AppCompatActivity implements View.OnClickListener {
    TextView txt;
    TextView txtQuestion;
    Button btnLeave;
    Button btnNext;
    Button btnPrev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        this.txtQuestion = (TextView)findViewById(R.id.txtQuestion);

        Bundle b = getIntent().getExtras();
        int value = -1; // or other values
        if(b != null)
            value = b.getInt("values");

        txt = (TextView)findViewById(R.id.txtTime);
        txt.setText(Integer.toString(value));
        this.btnLeave = (Button)findViewById(R.id.btnLeave);


        //btnleave per tornare alla schermata home non va,
        //settare i colori giusti per option/exercise

        //chiedere come si passa da una domanda all'altra nel modo migliore
        /*
        quando avrai array domande ( per ora usare dei dummy), mmettere animazione semplice (impara come si fa) tipo testo che appare
        /scompare, rimanere nello stesso, non iniziare nuova attività, metodo: cambiare testo
        usare fragment per testo/rdbbutton (puoi riciclasre animaz del signor Colombari)
        */

        //controlla se rdb hanno prop uncheckable
        //creaare classe exercise data container (es: rispo selezionata per domanda selezionata, e altri dati e più codice per noi)

        //creare classe in db esterno che impl runnable con nome a piacere (search backup db), che appena chiamata fa roba che dice mattia ( per ora è una prova)

        //


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
                this.txtQuestion.setText("Hai premuto precdente");

                break;
        }
    }
}