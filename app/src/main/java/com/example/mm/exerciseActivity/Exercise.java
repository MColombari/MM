package com.example.mm.exerciseActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.view.View;


import com.example.mm.exerciseActivity.localDatabaseInteraction.GetQuestionByRecurrence;
import com.example.mm.homeActivity.Home;

import com.example.mm.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import questionSortingAlgorithm.dataStructure.QuestionsDataRecurrence;

public class Exercise extends AppCompatActivity implements View.OnClickListener {
    Button leaveButton;
    Button nextButton;
    Button prevButton;
    TextView questionText;
    RadioButton radioGroupExerciseActivity1;
    RadioButton radioGroupExerciseActivity2;
    RadioButton radioGroupExerciseActivity3;
    RadioButton radioGroupExerciseActivity4;

    int numberOfQuestion;
    int sortOption;
    ArrayList<Integer> listOfCoursesSelectedIds;

    ArrayList<ResponseData> responseDataArrayList;
    int QuestionIndex;

    Runnable sortingAlgorithm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        responseDataArrayList = new ArrayList<>();

        leaveButton = (Button) findViewById(R.id.LeaveButton);
        nextButton = (Button) findViewById(R.id.NextButton);
        prevButton = (Button) findViewById(R.id.PrevButton);
        questionText = (TextView) findViewById(R.id.QuestionText);
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
    }

    public void nextQuestion(){}
    public void prevQuestion(){}

    public void updateQuestion(ArrayList<QuestionsDataRecurrence> questionsDataRecurrenceArrayList){
        if(questionsDataRecurrenceArrayList.isEmpty()){
            questionText.setText("Errore, nessuna domanda trovata\ncon i parametri specificati,\nprego riprovare con altri parametri.\n");
            questionText.setTextColor(getResources().getColor(R.color.red));
            return;
        }

        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);

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

            responseDataArrayList.add(new ResponseData(i.getQuestion().getQid(), i.getQuestion().getQuestionText(), questionShuffled, indexOfRightOne, -1));
        }

        QuestionIndex = 0;
        questionText.setText(responseDataArrayList.get(QuestionIndex).getQuestionText());
        ResponseData RD = responseDataArrayList.get(QuestionIndex);
        radioGroupExerciseActivity1.setText(RD.getResponseInOrder().get(0));
        radioGroupExerciseActivity2.setText(RD.getResponseInOrder().get(1));
        radioGroupExerciseActivity3.setText(RD.getResponseInOrder().get(2));
        radioGroupExerciseActivity4.setText(RD.getResponseInOrder().get(3));
    }

    public void updateQuestionError(String errorString){
        questionText.setText(errorString);
        questionText.setTextColor(getResources().getColor(R.color.red));
    }

    public void goBackHome(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}