package com.example.mm.exerciseActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.view.View;


import com.example.mm.exerciseActivity.localDatabaseInteraction.GetQuestionByRecurrence;
import com.example.mm.exerciseActivity.localDatabaseInteraction.SetStatisticUser;
import com.example.mm.homeActivity.Home;

import com.example.mm.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import localDatabase.Tables.StatisticUser;
import questionSortingAlgorithm.dataStructure.QuestionsDataRecurrence;

public class Exercise extends AppCompatActivity implements View.OnClickListener {
    Button leaveButton;
    Button nextButton;
    Button prevButton;
    TextView questionText;
    TextView currentQuestionNumberTextView;
    RadioGroup radioGroup;
    RadioButton radioGroupExerciseActivity1;
    RadioButton radioGroupExerciseActivity2;
    RadioButton radioGroupExerciseActivity3;
    RadioButton radioGroupExerciseActivity4;

    Exercise activityExercise;

    int numberOfQuestion;
    int sortOption;
    ArrayList<Integer> listOfCoursesSelectedIds;

    ArrayList<ResponseData> responseDataArrayList;
    int questionIndex;

    Runnable sortingAlgorithm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        activityExercise = this;

        responseDataArrayList = new ArrayList<>();

        leaveButton = (Button) findViewById(R.id.LeaveButton);
        nextButton = (Button) findViewById(R.id.NextButton);
        prevButton = (Button) findViewById(R.id.PrevButton);
        questionText = (TextView) findViewById(R.id.QuestionText);
        currentQuestionNumberTextView = (TextView) findViewById(R.id.CurrentQuestionNumber);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroupExerciseActivity);
        radioGroupExerciseActivity1 = (RadioButton) findViewById(R.id.radioGroupExerciseActivity1);
        radioGroupExerciseActivity2 = (RadioButton) findViewById(R.id.radioGroupExerciseActivity2);
        radioGroupExerciseActivity3 = (RadioButton) findViewById(R.id.radioGroupExerciseActivity3);
        radioGroupExerciseActivity4 = (RadioButton) findViewById(R.id.radioGroupExerciseActivity4);

        leaveButton.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            numberOfQuestion = bundle.getInt("QuestionNumber");
            sortOption = bundle.getInt("SortOption");
            listOfCoursesSelectedIds = bundle.getIntegerArrayList("CoursesId");
        }

        int[] listOfCoursesSelectedIdsArray = new int[listOfCoursesSelectedIds.size()];
        for(int i = 0; i < listOfCoursesSelectedIds.size(); i++){
            listOfCoursesSelectedIdsArray[i] = listOfCoursesSelectedIds.get(i);
        }

        if(sortOption == 1){
            sortingAlgorithm = new GetQuestionByRecurrence(getApplicationContext(), this, listOfCoursesSelectedIdsArray);
        }

        Thread thread = new Thread(sortingAlgorithm);
        thread.start();
    }


    public void nextQuestion(){
        if(questionIndex <= (numberOfQuestion - 1)){
            if(radioGroupExerciseActivity1.isChecked()){
                responseDataArrayList.get(questionIndex).setAnswerChose(0);
            }
            else if(radioGroupExerciseActivity2.isChecked()){
                responseDataArrayList.get(questionIndex).setAnswerChose(1);
            }
            else if(radioGroupExerciseActivity3.isChecked()){
                responseDataArrayList.get(questionIndex).setAnswerChose(2);
            }
            else if(radioGroupExerciseActivity4.isChecked()){
                responseDataArrayList.get(questionIndex).setAnswerChose(3);
            }
        }
        if(questionIndex >= (numberOfQuestion - 1)){
            /* Finisci esercitazione */
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = layoutInflater.inflate(R.layout.double_button_popup_window, null);

            PopupWindow doubleButtonPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT, true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                doubleButtonPopupWindow.setElevation(20);
            }
            doubleButtonPopupWindow.setAnimationStyle(R.style.AnimationGenericPopupWindow);
            doubleButtonPopupWindow.update();
            /* "v" is used as a parent view to get the View.getWindowToken() token from. */
            doubleButtonPopupWindow.showAtLocation((View) nextButton, Gravity.CENTER, 0, 0);

            TextView text = (TextView) popupView.findViewById(R.id.Double_Button_Popup_Window_Text);
            TextView title = (TextView) popupView.findViewById(R.id.Double_Button_Popup_Window_Title);
            TextView yesButton = (TextView) popupView.findViewById(R.id.Double_Button_Popup_Window_Yes);
            TextView noButton = (TextView) popupView.findViewById(R.id.Double_Button_Popup_Window_No);

            title.setText("Terminate");
            text.setText("Are you sure you want to\nend the examination?");

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /* Exit and save progress */
                    doubleButtonPopupWindow.dismiss();

                    LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = layoutInflater.inflate(R.layout.generic_popup_window, null);

                    PopupWindow genericPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT, true);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        genericPopupWindow.setElevation(20);
                    }
                    genericPopupWindow.setAnimationStyle(R.style.AnimationGenericPopupWindow);
                    genericPopupWindow.update();
                    /* "v" is used as a parent view to get the View.getWindowToken() token from. */
                    genericPopupWindow.showAtLocation((View) nextButton, Gravity.CENTER, 0, 0);

                    TextView text = (TextView) popupView.findViewById(R.id.Generic_Popup_Window_Text);
                    TextView title = (TextView) popupView.findViewById(R.id.Generic_Popup_Window_Title);
                    TextView button = (TextView) popupView.findViewById(R.id.Generic_Popup_Window_Ok);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            genericPopupWindow.dismiss();
                            goBackHome();
                        }
                    });

                    title.setText("Result");
                    text.setText("Elaboration results...");

                    /* Elaboration results. */
                    int points = 0;
                    ArrayList<StatisticUser> statisticUserArrayList = new ArrayList<>();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                    String date = simpleDateFormat.format(new Date());
                    for(ResponseData i : responseDataArrayList){
                        if(i.getRightAnswer() == i.getAnswerChose()){
                            points += 100;
                            statisticUserArrayList.add(new StatisticUser(i.getQuestionId(), Integer.parseInt(date), 100));
                        }
                        /* I don's save statistic about question that were not answered. */
                        else if(i.getAnswerChose() != 5){
                            statisticUserArrayList.add(new StatisticUser(i.getQuestionId(), Integer.parseInt(date), 0));
                        }
                    }
                    points /= responseDataArrayList.size();
                    text.setText(Integer.toString(points) + " / 100");

                    if(!statisticUserArrayList.isEmpty()) {
                        Thread thread = new Thread(new SetStatisticUser(getApplicationContext(), activityExercise, statisticUserArrayList));
                        thread.start();
                    }
                    return;
                }
            });

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doubleButtonPopupWindow.dismiss();
                }
            });

            return;
        }

        Animation fadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        fadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                questionIndex++;

                questionText.setText(responseDataArrayList.get(questionIndex).getQuestionText());
                ResponseData RD = responseDataArrayList.get(questionIndex);
                radioGroupExerciseActivity1.setText(RD.getResponseInOrder().get(0));
                radioGroupExerciseActivity2.setText(RD.getResponseInOrder().get(1));
                radioGroupExerciseActivity3.setText(RD.getResponseInOrder().get(2));
                radioGroupExerciseActivity4.setText(RD.getResponseInOrder().get(3));

                currentQuestionNumberTextView.setText(Integer.toString(questionIndex + 1) + "/" + Integer.toString(numberOfQuestion));

                int indexOfQuestionChose = responseDataArrayList.get(questionIndex).getAnswerChose();
                if(indexOfQuestionChose == 0){
                    radioGroup.check(radioGroupExerciseActivity1.getId());
                }
                else if(indexOfQuestionChose == 1){
                    radioGroup.check(radioGroupExerciseActivity2.getId());
                }
                else if(indexOfQuestionChose == 2){
                    radioGroup.check(radioGroupExerciseActivity3.getId());
                }
                else if(indexOfQuestionChose == 3){
                    radioGroup.check(radioGroupExerciseActivity4.getId());
                }
                else{
                    radioGroup.clearCheck();
                }

                if(questionIndex == (numberOfQuestion - 1)){
                    prevButton.setBackgroundColor(getResources().getColor(R.color.black));
                    nextButton.setBackgroundColor(getResources().getColor(R.color.red));
                    nextButton.setText("End examination");
                }
                else{
                    prevButton.setBackgroundColor(getResources().getColor(R.color.black));
                    nextButton.setBackgroundColor(getResources().getColor(R.color.black));
                }

                Animation fadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                questionText.startAnimation(fadeInAnimation);
                radioGroup.startAnimation(fadeInAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        questionText.startAnimation(fadeOutAnimation);
        radioGroup.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));

    }
    public void prevQuestion(){
        if(questionIndex <= 0){
            /* Do nothing. */
            return;
        }

        if(radioGroupExerciseActivity1.isChecked()){
            responseDataArrayList.get(questionIndex).setAnswerChose(0);
        }
        else if(radioGroupExerciseActivity2.isChecked()){
            responseDataArrayList.get(questionIndex).setAnswerChose(1);
        }
        else if(radioGroupExerciseActivity3.isChecked()){
            responseDataArrayList.get(questionIndex).setAnswerChose(2);
        }
        else if(radioGroupExerciseActivity4.isChecked()){
            responseDataArrayList.get(questionIndex).setAnswerChose(3);
        }

        Animation fadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        fadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                questionIndex--;

                questionText.setText(responseDataArrayList.get(questionIndex).getQuestionText());
                ResponseData RD = responseDataArrayList.get(questionIndex);
                radioGroupExerciseActivity1.setText(RD.getResponseInOrder().get(0));
                radioGroupExerciseActivity2.setText(RD.getResponseInOrder().get(1));
                radioGroupExerciseActivity3.setText(RD.getResponseInOrder().get(2));
                radioGroupExerciseActivity4.setText(RD.getResponseInOrder().get(3));

                currentQuestionNumberTextView.setText(Integer.toString(questionIndex + 1) + "/" + Integer.toString(numberOfQuestion));

                int indexOfQuestionChose = responseDataArrayList.get(questionIndex).getAnswerChose();
                if(indexOfQuestionChose == 0){
                    radioGroup.check(radioGroupExerciseActivity1.getId());
                }
                else if(indexOfQuestionChose == 1){
                    radioGroup.check(radioGroupExerciseActivity2.getId());
                }
                else if(indexOfQuestionChose == 2){
                    radioGroup.check(radioGroupExerciseActivity3.getId());
                }
                else if(indexOfQuestionChose == 3){
                    radioGroup.check(radioGroupExerciseActivity4.getId());
                }
                else{
                    radioGroup.clearCheck();
                }

                if(questionIndex == 0){
                    prevButton.setBackgroundColor(getResources().getColor(R.color.gray));
                    nextButton.setBackgroundColor(getResources().getColor(R.color.black));
                    nextButton.setText("Next");
                }
                else{
                    prevButton.setBackgroundColor(getResources().getColor(R.color.black));
                    nextButton.setBackgroundColor(getResources().getColor(R.color.black));
                    nextButton.setText("Next");
                }

                Animation fadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                questionText.startAnimation(fadeInAnimation);
                radioGroup.startAnimation(fadeInAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        questionText.startAnimation(fadeOutAnimation);
        radioGroup.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));

    }

    public void updateQuestion(ArrayList<QuestionsDataRecurrence> questionsDataRecurrenceArrayList){
        if(questionsDataRecurrenceArrayList.isEmpty()){
            questionText.setText("Errore, nessuna domanda trovata\ncon i parametri specificati,\nprego riprovare con altri parametri.\n");
            questionText.setTextColor(getResources().getColor(R.color.red));
            return;
        }

        if(numberOfQuestion > questionsDataRecurrenceArrayList.size()){
            numberOfQuestion = questionsDataRecurrenceArrayList.size();
        }

        questionsDataRecurrenceArrayList = Utils.CutArray.cutQuestionsDataRecurrence(questionsDataRecurrenceArrayList, 0, numberOfQuestion - 1);

        for(QuestionsDataRecurrence i : questionsDataRecurrenceArrayList){
            List<Integer> index = Arrays.asList(0, 1, 2, 3);
            ArrayList<String> questionShuffled = new ArrayList<>();
            Collections.shuffle(index);
            int indexOfRightOne = 0;

            String[] possibleAnswer = new String[4];
            possibleAnswer[0] = i.getQuestion().getRightAnswer();
            possibleAnswer[1] = i.getQuestion().getWrongAnswer1();
            possibleAnswer[2] = i.getQuestion().getWrongAnswer2();
            possibleAnswer[3] = i.getQuestion().getWrongAnswer3();

            for(int a = 0; a < 4; a++){
                if(index.get(a) == 0){
                    indexOfRightOne = a;
                }
                questionShuffled.add(possibleAnswer[index.get(a)]);
            }

            /* If a question was not answered the value of "answerChose" id 5. */
            responseDataArrayList.add(new ResponseData(i.getQuestion().getQid(), i.getQuestion().getQuestionText(), questionShuffled, indexOfRightOne, 5));
        }

        questionIndex = 0;
        questionText.setText(responseDataArrayList.get(questionIndex).getQuestionText());
        ResponseData RD = responseDataArrayList.get(questionIndex);
        radioGroupExerciseActivity1.setText(RD.getResponseInOrder().get(0));
        radioGroupExerciseActivity2.setText(RD.getResponseInOrder().get(1));
        radioGroupExerciseActivity3.setText(RD.getResponseInOrder().get(2));
        radioGroupExerciseActivity4.setText(RD.getResponseInOrder().get(3));

        nextButton.setBackgroundColor(getResources().getColor(R.color.black));
        prevButton.setBackgroundColor(getResources().getColor(R.color.gray));

        if(numberOfQuestion == 1){
            nextButton.setBackgroundColor(getResources().getColor(R.color.red));
            nextButton.setText("End examination");
        }

        currentQuestionNumberTextView.setText(Integer.toString(questionIndex + 1) + "/" + Integer.toString(numberOfQuestion));

        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
    }

    public void updateQuestionError(String errorString){
        questionText.setText(errorString);
        questionText.setTextColor(getResources().getColor(R.color.red));
    }

    public void goBackHome(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == leaveButton.getId()){
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = layoutInflater.inflate(R.layout.double_button_popup_window, null);

            PopupWindow doubleButtonPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT, true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                doubleButtonPopupWindow.setElevation(20);
            }
            doubleButtonPopupWindow.setAnimationStyle(R.style.AnimationGenericPopupWindow);
            doubleButtonPopupWindow.update();
            /* "v" is used as a parent view to get the View.getWindowToken() token from. */
            doubleButtonPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

            TextView text = (TextView) popupView.findViewById(R.id.Double_Button_Popup_Window_Text);
            TextView title = (TextView) popupView.findViewById(R.id.Double_Button_Popup_Window_Title);
            TextView yesButton = (TextView) popupView.findViewById(R.id.Double_Button_Popup_Window_Yes);
            TextView noButton = (TextView) popupView.findViewById(R.id.Double_Button_Popup_Window_No);

            title.setText("Cancel");
            text.setText("Are you sure you want to\ngo back and lose all\nyour progress?");

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goBackHome();
                }
            });

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doubleButtonPopupWindow.dismiss();
                }
            });
        }
        else if(v.getId() == nextButton.getId()){
            nextQuestion();
        }
        else if(v.getId() == prevButton.getId()){
            prevQuestion();
        }
    }
}