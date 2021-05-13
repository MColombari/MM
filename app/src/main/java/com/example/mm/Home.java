package com.example.mm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import localDatabase.Question;
import localDatabase.TestApp;

public class Home extends AppCompatActivity {
    TextView mainTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mainTextView = findViewById(R.id.mainText);

        /*
         * La seguente parte di codice è provvisoria e serve allo scopo di testare l'utilizzo di Room (db locale).
         * I file raltivi al db locale sono presenti nel package "localDatabase", dove a sua volta è presente una classe che implementa Runnable "TestApp"
         * che testa l'utilizzo del db locale aggiungento elementi e stampandoli du schermo.
         * */
        Thread t = new Thread(new TestApp(getApplicationContext(), this, mainTextView));
        t.start();
    }
}