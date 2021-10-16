package com.example.mm.optionActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.mm.R;
import com.example.mm.exerciseActivity.Exercise;
import com.example.mm.optionActivity.localDatabaseInteraction.GetCourse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import localDatabase.Tables.Course;

public class Option extends AppCompatActivity implements View.OnClickListener {
    TextView CourseSelection;
    TextView txtNumberSelect;
    TextView Flag;
    boolean[] selectedCourses;

    EditText EditTextNumberOfQuestion;

    ArrayList<String> listOfCoursesNames;
    ArrayList<Course> listOfCourses;
    ArrayList<Course> listOfCoursesSelected;

    RadioButton radioGroupSelection1;
    RadioButton radioGroupSelection2;
    RadioButton radioGroupSelection3;

    TextView textView11; //Debug Purpose.

    Button StartButtonOptionActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        listOfCoursesNames = new ArrayList<>();
        listOfCourses = new ArrayList<>();
        listOfCoursesSelected = new ArrayList<>();

        StartButtonOptionActivity = (Button) findViewById(R.id.StartButtonOptionActivity);
        EditTextNumberOfQuestion = (EditText) findViewById(R.id.NumberOfQuestions);
        Flag = (TextView) findViewById(R.id.FlagOptionActivity);
        CourseSelection = (TextView) findViewById(R.id.CoursesSelected);
        txtNumberSelect = (TextView) findViewById(R.id.NumberOfQuestions);
        radioGroupSelection1 = (RadioButton) findViewById(R.id.radioGroupSelection1);
        radioGroupSelection2 = (RadioButton) findViewById(R.id.radioGroupSelection2);
        radioGroupSelection3 = (RadioButton) findViewById(R.id.radioGroupSelection3);


        StartButtonOptionActivity.setOnClickListener(this);


        textView11 = (TextView) findViewById(R.id.textView11); //Debug Purpose.

        Thread thread = new Thread(new GetCourse(getApplicationContext(), this));
        thread.start();
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == this.StartButtonOptionActivity.getId()) {
            Intent intent = new Intent(this, Exercise.class);
            Bundle bundle = new Bundle();

            int numberOfQuestion = new Integer(this.EditTextNumberOfQuestion.getText().toString());
            if(numberOfQuestion <= 0){
                LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.generic_popup_window, null);

                PopupWindow questionMarkPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT, true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    questionMarkPopupWindow.setElevation(20);
                }
                questionMarkPopupWindow.setAnimationStyle(R.style.AnimationGenericPopupWindow);
                questionMarkPopupWindow.update();
                /* "v" is used as a parent view to get the View.getWindowToken() token from. */
                questionMarkPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                TextView text = (TextView) popupView.findViewById(R.id.Generic_Popup_Window_Text);
                TextView title = (TextView) popupView.findViewById(R.id.Generic_Popup_Window_Title);
                TextView button = (TextView) popupView.findViewById(R.id.Generic_Popup_Window_Ok);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        questionMarkPopupWindow.dismiss();
                    }
                });

                title.setText("Error");
                title.setTextColor(getResources().getColor(R.color.red));
                text.setText("The numeber of question needs to be\ngreater than zero.");

                return;
            }

            int SortOption;
            if(radioGroupSelection1.isChecked()) { SortOption = 1; }
            else if(radioGroupSelection2.isChecked()) { SortOption = 2; }
            else{ SortOption = 3; }

            ArrayList<Integer> listOfCoursesSelectedIds = new ArrayList<>();
            for(Course i : listOfCoursesSelected){
                listOfCoursesSelectedIds.add(i.getId());
            }

            bundle.putInt("QuestionNumber", numberOfQuestion);
            bundle.putInt("SortOption", SortOption);
            bundle.putIntegerArrayList("CoursesId", listOfCoursesSelectedIds);

            intent.putExtras(bundle);
            startActivity(intent);
        }
        if(v.getId() == this.CourseSelection.getId())
        {
            // Initialize alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // Set title
            builder.setTitle("Select Courses");
            // Set dialog non cancelable
            builder.setCancelable(false);

            String[] listOfCoursesNamesStrings = listOfCoursesNames.toArray(new String[0]);

            builder.setMultiChoiceItems(listOfCoursesNamesStrings, selectedCourses, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                    if (b) {
                        /* Checked */
                        listOfCoursesSelected.add(listOfCourses.get(i));
                    } else {
                        /* Unchecked */
                        listOfCoursesSelected.remove(listOfCourses.get(i));
                    }
                }
            });

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    for(int a = 0; a < selectedCourses.length; a++){
                        selectedCourses[a] = false;
                    }

                    dialogInterface.dismiss();
                }
            });

            builder.show();
        }


    }


    public void updateDropDown(List<Course> listOfCourses, boolean chkErr) {
        //chkErr = boolean passato per verifica errore
        if(chkErr) {
            this.CourseSelection.setText("Errore lettura corsi");
            Flag.setText("Errore lettura corsi");
            Flag.setTextColor(getResources().getColor(R.color.red));
        }
        else
        {
            if((listOfCourses != null) && (!listOfCourses.isEmpty())) {
                this.CourseSelection.setText("Seleziona i corsi");
                Flag.setText("Updated");
                Flag.setTextColor(getResources().getColor(R.color.green));

                CourseSelection.setOnClickListener(this);

                for (Course i : listOfCourses) {
                    listOfCoursesNames.add(i.getName());
                }

                this.listOfCourses = (ArrayList) listOfCourses;
                selectedCourses = new boolean[listOfCoursesNames.size()];
                for(int a = 0; a < selectedCourses.length; a++){
                    selectedCourses[a] = false;
                }

                /* --- Start Debug Purpose Code --- */

                StringBuilder stringBuilder = new StringBuilder();
                for(String i : listOfCoursesNames){
                    stringBuilder.append(i + "\n");
                }
                textView11.setText("ChkErr = " + (chkErr ? "True" : "False") + "\nCourse Names =\n" + stringBuilder.toString());

                /* --- End Debug Purpose Code --- */
            }
            else{
                this.CourseSelection.setText("Errore, nessun corso trovato");
                Flag.setText("Errore, nessun corso trovato");
                Flag.setTextColor(getResources().getColor(R.color.red));
            }
        }
    }
}