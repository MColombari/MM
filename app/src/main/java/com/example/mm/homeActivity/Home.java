package com.example.mm.homeActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.example.mm.R;

public class Home extends AppCompatActivity implements OnClickListener{
    ImageView home_button;
    ImageView statistic_button;
    ImageView option_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Get ImageView by id */
        home_button = findViewById(R.id.home_icon);
        statistic_button = findViewById(R.id.statistic_icon);
        option_button = findViewById(R.id.option_icon);

        /* Set Home_FL (FrameLayout) as homeFragment (Fragment) */
        this.onClick(home_button);

        /* Set OnClickListener for changing Fragment in FrameLayout */
        home_button.setOnClickListener(this);
        statistic_button.setOnClickListener(this);
        option_button.setOnClickListener(this);


        /*
         * La seguente parte di codice è provvisoria e serve allo scopo di testare l'utilizzo di Room (db locale).
         * I file raltivi al db locale sono presenti nel package "localDatabase", dove a sua volta è presente una classe che implementa Runnable "TestApp"
         * che testa l'utilizzo del db locale aggiungento elementi e stampandoli du schermo.
         * */
        //TextView mainTextView = findViewById(R.id.mainText);
        //Thread t = new Thread(new TestApp(getApplicationContext(), this, mainTextView));
        //t.start();
    }

    @Override
    public void onClick(View v) {
        Class<? extends Fragment> newFragmentClass;
        if (home_button == v) {
            newFragmentClass = HomeFragment.class;
        } else if (statistic_button == v) {
            newFragmentClass = StatisticFragment.class;
        } else {
            newFragmentClass = OptionFragment.class;
        }


        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.Home_FCV);
        if(currentFragment != null){
            if(currentFragment.getClass() == newFragmentClass) return;
            getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.setCustomAnimations(R.animator.fade_in, R.animator.fade_out);
        transaction.add(R.id.Home_FCV, newFragmentClass, null);
        transaction.commit();
    }
}