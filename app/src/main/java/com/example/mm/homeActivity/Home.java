package com.example.mm.homeActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mm.R;

import localDatabase.TestApp;

public class Home extends AppCompatActivity {
    FragmentTransaction transaction;
    Fragment homeFragment;
    Fragment statisticFragment;
    ImageView home_button;
    ImageView statistic_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Get View */
        homeFragment = new HomeFragment();
        statisticFragment = new StatisticFragment();
        home_button = findViewById(R.id.home_icon);
        statistic_button = findViewById(R.id.statistic_icon);

        /* Set Home_FL (FrameLayout) as homeFragment (Fragment) */
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.Home_FL, homeFragment);
        transaction.commitAllowingStateLoss();

        /* Set OnClickListener for changing Fragment in FrameLayout */
        home_button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.Home_FL, homeFragment);
                transaction.commitAllowingStateLoss();
            }
        });
        statistic_button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.Home_FL, statisticFragment);
                transaction.commitAllowingStateLoss();;
            }
        });


        /*
         * La seguente parte di codice è provvisoria e serve allo scopo di testare l'utilizzo di Room (db locale).
         * I file raltivi al db locale sono presenti nel package "localDatabase", dove a sua volta è presente una classe che implementa Runnable "TestApp"
         * che testa l'utilizzo del db locale aggiungento elementi e stampandoli du schermo.
         * */
        //TextView mainTextView = findViewById(R.id.mainText);
        //Thread t = new Thread(new TestApp(getApplicationContext(), this, mainTextView));
        //t.start();
    }
}