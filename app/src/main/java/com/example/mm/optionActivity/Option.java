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
import com.example.mm.optionActivity.localDatabaseInteraction.SetQuickResumeData;
import com.example.mm.homeActivity.Home;
import com.example.mm.optionActivity.localDatabaseInteraction.GetCourse;

import java.util.ArrayList;
import java.util.List;

import localDatabase.Tables.Course;

public class Option extends AppCompatActivity implements View.OnClickListener {
    TextView Flag;
    TextView BackButton;
    TextView CourseSelection;
    EditText EditTextNumberOfQuestion;
    RadioButton radioGroupSelection1;
    RadioButton radioGroupSelection2;
    RadioButton radioGroupSelection3;
    Button StartButtonOptionActivity;

    boolean[] selectedCourses;
    ArrayList<String> listOfCoursesNames;
    ArrayList<Course> listOfCourses;
    ArrayList<Course> listOfCoursesSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        Flag = (TextView) findViewById(R.id.FlagOptionActivity);
        BackButton = (TextView) findViewById(R.id.BackButton);
        CourseSelection = (TextView) findViewById(R.id.CoursesSelected);
        EditTextNumberOfQuestion = (EditText) findViewById(R.id.NumberOfQuestions);
        radioGroupSelection1 = (RadioButton) findViewById(R.id.radioGroupSelection1);
        radioGroupSelection2 = (RadioButton) findViewById(R.id.radioGroupSelection2);
        radioGroupSelection3 = (RadioButton) findViewById(R.id.radioGroupSelection3);
        StartButtonOptionActivity = (Button) findViewById(R.id.StartButtonOptionActivity);

        listOfCoursesNames = new ArrayList<>();
        listOfCourses = new ArrayList<>();
        listOfCoursesSelected = new ArrayList<>();

        StartButtonOptionActivity.setOnClickListener(this);
        BackButton.setOnClickListener(this);

        Thread thread = new Thread(new GetCourse(getApplicationContext(), this));
        thread.start();
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == this.BackButton.getId()){
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }
        else if(v.getId() == this.StartButtonOptionActivity.getId()) {
            Intent intent = new Intent(this, Exercise.class);
            Bundle bundle = new Bundle();

            if(this.EditTextNumberOfQuestion.getText().toString().equals("")){
                LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.generic_popup_window, null);

                PopupWindow genericPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT, true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    genericPopupWindow.setElevation(20);
                }
                genericPopupWindow.setAnimationStyle(R.style.AnimationGenericPopupWindow);
                genericPopupWindow.update();
                /* The View "v" is used as a parent view to get the View.getWindowToken() token from. */
                genericPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                TextView text = (TextView) popupView.findViewById(R.id.Generic_Popup_Window_Text);
                TextView title = (TextView) popupView.findViewById(R.id.Generic_Popup_Window_Title);
                TextView button = (TextView) popupView.findViewById(R.id.Generic_Popup_Window_Ok);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        genericPopupWindow.dismiss();
                    }
                });

                title.setText("Error");
                title.setTextColor(getResources().getColor(R.color.red));
                text.setText("The number of question can't\nbe empty.");

                return;
            }
            int numberOfQuestion = Integer.parseInt(this.EditTextNumberOfQuestion.getText().toString());
            if(numberOfQuestion <= 0){
                LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.generic_popup_window, null);

                PopupWindow genericPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT, true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    genericPopupWindow.setElevation(20);
                }
                genericPopupWindow.setAnimationStyle(R.style.AnimationGenericPopupWindow);
                genericPopupWindow.update();
                /* The View "v" is used as a parent view to get the View.getWindowToken() token from. */
                genericPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                TextView text = (TextView) popupView.findViewById(R.id.Generic_Popup_Window_Text);
                TextView title = (TextView) popupView.findViewById(R.id.Generic_Popup_Window_Title);
                TextView button = (TextView) popupView.findViewById(R.id.Generic_Popup_Window_Ok);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        genericPopupWindow.dismiss();
                    }
                });

                title.setText("Error");
                title.setTextColor(getResources().getColor(R.color.red));
                text.setText("The number of question needs to be\ngreater than zero.");

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
            if(listOfCoursesSelectedIds.isEmpty()){
                LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.generic_popup_window, null);

                PopupWindow genericPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT, true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    genericPopupWindow.setElevation(20);
                }
                genericPopupWindow.setAnimationStyle(R.style.AnimationGenericPopupWindow);
                genericPopupWindow.update();
                /* The View "v" is used as a parent view to get the View.getWindowToken() token from. */
                genericPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                TextView text = (TextView) popupView.findViewById(R.id.Generic_Popup_Window_Text);
                TextView title = (TextView) popupView.findViewById(R.id.Generic_Popup_Window_Title);
                TextView button = (TextView) popupView.findViewById(R.id.Generic_Popup_Window_Ok);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        genericPopupWindow.dismiss();
                    }
                });

                title.setText("Error");
                title.setTextColor(getResources().getColor(R.color.red));
                text.setText("The number of courses needs to be\nat least one.");

                return;
            }

            bundle.putInt("QuestionNumber", numberOfQuestion);
            bundle.putInt("SortOption", SortOption);
            bundle.putIntegerArrayList("CoursesId", listOfCoursesSelectedIds);

            /* Save the data for the quick resume. */
            Thread thread = new Thread(new SetQuickResumeData(getApplicationContext(), numberOfQuestion, SortOption, listOfCoursesSelectedIds));
            thread.start();

            intent.putExtras(bundle);
            startActivity(intent);
        }
        else if(v.getId() == this.CourseSelection.getId())
        {
            /* Initialize alert dialog. */
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select Courses"); //Set title.
            builder.setCancelable(false); //Set dialog non cancelable.

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

                    StringBuilder stringBuilder = new StringBuilder();

                    boolean flag = true;
                    for(int a = 0; a < selectedCourses.length; a++){
                        if(selectedCourses[a]){
                            if(flag){
                                flag = false;
                            }
                            else{
                                stringBuilder.append(", ");
                            }
                            stringBuilder.append(listOfCoursesNames.get(a));
                        }
                    }

                    CourseSelection.setText(stringBuilder.toString());
                }
            });

            builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    for(int a = 0; a < selectedCourses.length; a++){
                        selectedCourses[a] = false;
                    }
                    CourseSelection.setText("No selected courses");
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        }
    }


    public void updateDropDown(List<Course> listOfCourses, boolean chkErr) {
        if(chkErr) {
            this.CourseSelection.setText("");
            Flag.setText("Error, getting courses");
            Flag.setTextColor(getResources().getColor(R.color.red));
        }
        else
        {
            if((listOfCourses != null) && (!listOfCourses.isEmpty())) {
                this.CourseSelection.setText("No selected courses");
                this.CourseSelection.setTextColor(getResources().getColor(R.color.gray));
                Flag.setText("Updated");
                Flag.setTextColor(getResources().getColor(R.color.green));

                CourseSelection.setOnClickListener(this);

                for (Course i : listOfCourses) {
                    listOfCoursesNames.add(i.getName());
                }

                this.listOfCourses = (ArrayList<Course>) listOfCourses;
                selectedCourses = new boolean[listOfCoursesNames.size()];
                for(int a = 0; a < selectedCourses.length; a++){
                    selectedCourses[a] = false;
                }
            }
            else{
                this.CourseSelection.setText("");
                Flag.setText("Error, no courses found");
                Flag.setTextColor(getResources().getColor(R.color.red));
            }
        }
    }
}