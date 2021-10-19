package com.example.mm.homeActivity.homeFragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.mm.R;
import com.example.mm.exerciseActivity.Exercise;
import com.example.mm.homeActivity.Home;
import com.example.mm.homeActivity.localDatabaseInteraction.GetQuickResumeData;
import com.example.mm.optionActivity.Option;
import com.example.mm.optionActivity.localDatabaseInteraction.SetQuickResumeData;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class HomeFragment extends Fragment implements View.OnClickListener {
    Button startButton;
    Button quickResumeButton;
    ImageView questionMark;

    int numbQuestion;
    int sortingAlgorithm;
    ArrayList<Integer> coursesIdsArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startButton = (Button) view.findViewById(R.id.Start_Button);
        quickResumeButton = (Button) view.findViewById(R.id.Resume_Button);
        questionMark = (ImageView) view.findViewById(R.id.Home_Fragment_Question_Mark);

        startButton.setOnClickListener(this);
        questionMark.setOnClickListener(this);

        Thread thread = new Thread(new GetQuickResumeData(getContext(), getActivity().getApplicationContext(), this));
        thread.start();
    }

    public void setQuickResume(boolean setClickListener, SpannableString buttonName, int numbQuestion, int sortingAlgorithm, ArrayList<Integer> coursesIdsArrayList){
        this.numbQuestion = numbQuestion;
        this.sortingAlgorithm = sortingAlgorithm;
        this.coursesIdsArrayList = coursesIdsArrayList;
        if(setClickListener){
            quickResumeButton.setOnClickListener(this);
            quickResumeButton.setBackgroundColor(getContext().getResources().getColor(R.color.light_green));
        }
        quickResumeButton.setText(buttonName);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.Start_Button){
            startActivity(new Intent((Home) getActivity(), Option.class));
        }
        else if(v.getId() == R.id.Home_Fragment_Question_Mark){
            /* Show information about  */
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = layoutInflater.inflate(R.layout.generic_popup_window, null);

            PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT, true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                popupWindow.setElevation(20);
            }
            popupWindow.setAnimationStyle(R.style.AnimationGenericPopupWindow);
            popupWindow.update();
            /* "v" is used as a parent view to get the View.getWindowToken() token from. */
            popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

            TextView text = (TextView) popupView.findViewById(R.id.Generic_Popup_Window_Text);
            TextView title = (TextView) popupView.findViewById(R.id.Generic_Popup_Window_Title);
            TextView button = (TextView) popupView.findViewById(R.id.Generic_Popup_Window_Ok);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });

            title.setText("More about home.");
            text.setText(   "The start button will let you start a new quiz witch settings will " +
                    "be chose by you.\n" +
                    "The resume button (if green) will start a new quiz with the last setting " +
                    "used for the last quiz, if the button is grey no previous settings are found.");
        }
        else if(v.getId() == quickResumeButton.getId()){
            Intent intent = new Intent(getActivity().getApplicationContext(), Exercise.class);
            Bundle bundle = new Bundle();
            bundle.putInt("QuestionNumber", numbQuestion);
            bundle.putInt("SortOption", sortingAlgorithm);
            bundle.putIntegerArrayList("CoursesId", coursesIdsArrayList);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}