package com.example.mm.homeActivity;

import androidx.annotation.AnimRes;
import androidx.annotation.AnimatorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mm.R;
import com.example.mm.homeActivity.homeFragment.HomeFragment;
import com.example.mm.homeActivity.optionFragment.OptionFragment;
import com.example.mm.homeActivity.statisticFragment.MoreStatisticFragment;
import com.example.mm.homeActivity.statisticFragment.StatisticFragment;

public class Home extends AppCompatActivity implements OnClickListener, View.OnTouchListener {
    /* Definition of Elements of this Activity. */
    ImageView homeButton;
    ImageView statisticButton;
    ImageView optionButton;
    ImageView iconSeparator1;
    ImageView iconSeparator2;
    FragmentContainerView homeFCV;
    ConstraintLayout constraintLayout;

    /* Definition of int for set opacity of the icon. */
    int opacityOfIconDefault;
    int opacityOfIconClicked;

    /* Definition on GestureDetector to handle gesture. */
    GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Get Elements by id */
        homeButton = (ImageView) findViewById(R.id.home_icon);
        statisticButton = (ImageView) findViewById(R.id.statistic_icon);
        optionButton = (ImageView) findViewById(R.id.option_icon);
        iconSeparator1 = (ImageView) findViewById(R.id.MainIconSeparator1);
        iconSeparator2 = (ImageView) findViewById(R.id.MainIconSeparator2);
        homeFCV = (FragmentContainerView) findViewById(R.id.Home_FCV);
        constraintLayout = (ConstraintLayout) findViewById(R.id.MainConstraintLayout);

        /* Get Resources */
        opacityOfIconDefault = getResources().getInteger(R.integer.opacityOfIconDefault);
        opacityOfIconClicked = getResources().getInteger(R.integer.opacityOfIconClicked);

        /* "savedInstanceState" is a object containing the activity's previously saved state,
         * is null only if the activity has never existed before */
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                    .add(R.id.Home_FCV, HomeFragment.class, null)
                    .commit();
            statisticButton.setImageAlpha(opacityOfIconDefault);
            optionButton.setImageAlpha(opacityOfIconDefault);
        }

        /* Set OnClickListener for changing Fragment in FrameLayout */
        homeButton.setOnClickListener(this);
        statisticButton.setOnClickListener(this);
        optionButton.setOnClickListener(this);

        /* Set OnTouchListener for changing Fragment in FrameLayout */
        iconSeparator1.setOnTouchListener(this);
        iconSeparator2.setOnTouchListener(this);
        constraintLayout.setOnTouchListener(this);

        /* Creation of instance for gestureDetector. */
        gestureDetector = new GestureDetector(this, new GestureListener());
    }

    @Override
    public void onClick(View v) {
        @AnimatorRes @AnimRes int enterAnimation = R.anim.slide_in_right;
        @AnimatorRes @AnimRes int exitAnimation = R.anim.slide_out_left;

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.Home_FCV);
        if(currentFragment == null){
            return;
        }

        Class<? extends Fragment> newFragmentClass;
        if (homeButton.getId() == v.getId()) {
            newFragmentClass = HomeFragment.class;
            enterAnimation = R.anim.slide_in_left;
            exitAnimation = R.anim.slide_out_right;
            homeButton.setImageAlpha(opacityOfIconClicked);
            statisticButton.setImageAlpha(opacityOfIconDefault);
            optionButton.setImageAlpha(opacityOfIconDefault);
        } else if (statisticButton.getId() == v.getId()) {
            newFragmentClass = StatisticFragment.class;
            if(currentFragment.getClass() == OptionFragment.class){
                enterAnimation = R.anim.slide_in_left;
                exitAnimation = R.anim.slide_out_right;
            }
            else if(currentFragment.getClass() == MoreStatisticFragment.class){
                enterAnimation = R.anim.slide_in_top;
                exitAnimation = R.anim.slide_out_bottom;
            }
            homeButton.setImageAlpha(opacityOfIconDefault);
            statisticButton.setImageAlpha(opacityOfIconClicked);
            optionButton.setImageAlpha(opacityOfIconDefault);
        } else if (optionButton.getId() == v.getId()){
            newFragmentClass = OptionFragment.class;
            homeButton.setImageAlpha(opacityOfIconDefault);
            statisticButton.setImageAlpha(opacityOfIconDefault);
            optionButton.setImageAlpha(opacityOfIconClicked);
        } else if (R.id.StatisticMoreText == v.getId()){
            newFragmentClass = MoreStatisticFragment.class;
            enterAnimation = R.anim.slide_in_bottom;
            exitAnimation = R.anim.slide_out_top;
        } else /* (R.id.MoreStatisticLessText == v.getId()) */ {
            newFragmentClass = StatisticFragment.class;
            enterAnimation = R.anim.slide_in_top;
            exitAnimation = R.anim.slide_out_bottom;
        }

        if(currentFragment.getClass() == newFragmentClass) return;

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .setCustomAnimations(enterAnimation, exitAnimation)
                .remove(currentFragment)
                .commit();

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .setCustomAnimations(enterAnimation, exitAnimation)
                .add(R.id.Home_FCV, newFragmentClass, null)
                .commit();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        /* Call for gestureDetectorCompat. */
        return gestureDetector.onTouchEvent(event);
    }

    class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.Home_FCV);
            if(currentFragment == null) return false;

            boolean result = false;
            float differenceX = e2.getX() - e1.getX();
            float differenceY = e2.getY() - e1.getY();
            if(Math.abs(differenceX) >= Math.abs(differenceY)) {
                if (Math.abs(differenceX) > 100 && Math.abs(velocityX) > 100) {
                    if (differenceX > 0) {
                        if (currentFragment.getClass() == OptionFragment.class) {
                            onClick(statisticButton);
                        } else if (currentFragment.getClass() == StatisticFragment.class) {
                            onClick(homeButton);
                        }
                    } else {
                        if (currentFragment.getClass() == HomeFragment.class) {
                            onClick(statisticButton);
                        } else if (currentFragment.getClass() == StatisticFragment.class) {
                            onClick(optionButton);
                        }
                    }
                    result = true;
                }
            } else {
                if (Math.abs(differenceY) > 100 && Math.abs(velocityY) > 100) {
                    if (differenceY > 0) {
                        if (currentFragment.getClass() == MoreStatisticFragment.class) {
                            TextView moreStatisticLessText;
                            moreStatisticLessText = findViewById(R.id.MoreStatisticLessText);
                            if(moreStatisticLessText == null){
                                /* "findViewById" return "null" if the view has not being found,
                                * can happen when the Fragment (MoreStatisticFragment) has not
                                * being created. */
                                return false;
                            }
                            onClick(moreStatisticLessText);
                        }
                    } else {
                        if (currentFragment.getClass() == StatisticFragment.class) {
                            TextView statisticMoreText;
                            statisticMoreText = findViewById(R.id.StatisticMoreText);
                            if(statisticMoreText == null){
                                /* "findViewById" return "null" if the view has not being found,
                                 * can happen when the Fragment (StatisticFragment) has not
                                 * being created. */
                                return false;
                            }
                            onClick(statisticMoreText);
                        }
                    }
                }
            }
            return result;
        }
    }
}