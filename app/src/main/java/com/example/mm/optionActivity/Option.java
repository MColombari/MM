package com.example.mm.optionActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mm.R;
import com.example.mm.exerciseActivity.Exercise;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import localDatabase.Tables.Course;

public class Option extends AppCompatActivity implements View.OnClickListener {
    // initialize variables
    TextView textView;
    boolean[] selectedLanguage;
    ArrayList<Integer> langList = new ArrayList<>();
    String[] langArray = {"Informatica", "OOP", "Basi di Dati", "Geometria", "Diritto del Lavoro", "D U M M Y"}; //DUMMY

    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        //TO DO riuscire a mettere le mani sui dati da inserire nella lista

        // assign variable
        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);

        textView = findViewById(R.id.textView);
        textView.setOnClickListener(this);

        // initialize selected language array //DUMMY
        selectedLanguage = new boolean[langArray.length];
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == this.btnNext.getId()) {
            Intent intent = new Intent(this, Exercise.class);
            Bundle b = new Bundle();
            //b.putInt("values", 1);
            b.putString("lista", (String) this.textView.getText());
            intent.putExtras(b);
            startActivity(intent);
        }
        if(v.getId() == this.textView.getId())
        {
            // Initialize alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            // set title
            builder.setTitle("Seleziona Materia");

            // set dialog non cancelable
            builder.setCancelable(false);

            builder.setMultiChoiceItems(langArray, selectedLanguage, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                    // check condition
                    if (b) {
                        // when checkbox selected
                        // Add position  in lang list
                        langList.add(i);
                        // Sort array list
                        Collections.sort(langList);
                    } else {
                        // when checkbox unselected
                        // Remove position from langList
                        langList.remove(i);
                    }
                }
            });

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Initialize string builder
                    StringBuilder stringBuilder = new StringBuilder();
                    // use for loop
                    for (int j = 0; j < langList.size(); j++) {
                        // concat array value
                        stringBuilder.append(langArray[langList.get(j)]);
                        // check condition
                        if (j != langList.size() - 1) {
                            // When j value  not equal
                            // to lang list size - 1
                            // add comma
                            stringBuilder.append(", ");
                        }
                    }
                    // set text on textView
                    textView.setText(stringBuilder.toString());
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // dismiss dialog
                    dialogInterface.dismiss();
                }
            });

            builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // use for loop
                    for (int j = 0; j < selectedLanguage.length; j++) {
                        // remove all selection
                        selectedLanguage[j] = false;
                        // clear language list
                        langList.clear();
                        // clear text view value
                        textView.setText("");
                    }
                }
            });
            // show dialog
            builder.show();
        }


    }


    public void updateDropDown(List<Course> courseName, boolean chkErr) {
        //chkErr = boolean passato per verifica errore
        if(chkErr)
            this.textView.setText("errore lettura lista");
        else
        {
            this.textView.setText("va bene, cancella questo messaggio");
            //lavori normalmente
            //this.langArray = courseName.stream().toArray();
        }
    }
}