package com.example.mm.homeActivity.optionFragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.mm.R;
import com.example.mm.homeActivity.localDatabaseInteraction.getUserInformation;
import com.example.mm.homeActivity.localDatabaseInteraction.setUserInformation;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class OptionFragment extends Fragment implements View.OnClickListener {
    TextView textViewName;
    TextView textViewSurname;
    TextView textViewEmail;
    TextView textViewMatr;
    TextView textViewUpdateInfo;

    TextView optionFragmentPlainTextName;
    TextView optionFragmentPlainTextSurname;
    TextView optionFragmentPlainTextEmail;
    TextView optionFragmentPlainTextMatr;
    TextView optionFragmentTextButton;

    ImageView optionFragmentQuestionMarkUserInformation;
    ImageView optionFragmentQuestionMarkBackup;
    ImageView optionFragmentQuestionMarkManageCourse;

    View view;
    PopupWindow UserInformationPopupWindow;
    PopupWindow questionMarkPopupWindow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_option, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        textViewName = (TextView) view.findViewById(R.id.TextViewName);
        textViewSurname = (TextView) view.findViewById(R.id.TextViewSurname);
        textViewEmail = (TextView) view.findViewById(R.id.TextViewEmail);
        textViewMatr = (TextView) view.findViewById(R.id.TextViewMatr);
        textViewUpdateInfo = (TextView) view.findViewById(R.id.TexrViewUpdateUserInfo);

        optionFragmentQuestionMarkUserInformation = (ImageView) view.findViewById(R.id.Option_Fragment_Question_Mark_User_Information);
        optionFragmentQuestionMarkBackup = (ImageView) view.findViewById(R.id.Option_Fragment_Question_Mark_Backup);
        optionFragmentQuestionMarkManageCourse = (ImageView) view.findViewById(R.id.Option_Fragment_Question_Mark_Manage_Course);

        textViewUpdateInfo.setOnClickListener(this);
        optionFragmentQuestionMarkUserInformation.setOnClickListener(this);
        optionFragmentQuestionMarkBackup.setOnClickListener(this);
        optionFragmentQuestionMarkManageCourse.setOnClickListener(this);

        Thread t = new Thread(new getUserInformation(getContext(), this));
        t.start();
    }

    public void updateInfo(String name, String surname, String email, String matr){
        textViewName.setText(name);
        textViewSurname.setText(surname);
        textViewEmail.setText(email);
        textViewMatr.setText(matr);
        textViewUpdateInfo.setText("Update Information");
        textViewUpdateInfo.setTextColor(getResources().getColor(R.color.black));
    }
    public void updateInfo(String name, String surname, String email, String matr, String buttonText){
        textViewName.setText(name);
        textViewSurname.setText(surname);
        textViewEmail.setText(email);
        textViewMatr.setText(matr);
        textViewUpdateInfo.setText(buttonText);
        textViewUpdateInfo.setTextColor(getResources().getColor(R.color.red));
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == textViewUpdateInfo.getId()){
            /* Update User Information */
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = layoutInflater.inflate(R.layout.fragment_option_popup_window, null);

            UserInformationPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT, true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                UserInformationPopupWindow.setElevation(20);
            }
            UserInformationPopupWindow.setAnimationStyle(R.style.AnimationGenericPopupWindow);
            UserInformationPopupWindow.update();
            /* "v" is used as a parent view to get the View.getWindowToken() token from. */
            UserInformationPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

            optionFragmentPlainTextName = (TextView) popupView.findViewById(R.id.OptionFragmentPlainTextName);
            optionFragmentPlainTextSurname = (TextView) popupView.findViewById(R.id.OptionFragmentPlainTextSurname);
            optionFragmentPlainTextEmail = (TextView) popupView.findViewById(R.id.OptionFragmentPlainTextEmail);
            optionFragmentPlainTextMatr = (TextView) popupView.findViewById(R.id.OptionFragmentPlainTextMatr);
            optionFragmentTextButton = (TextView) popupView.findViewById(R.id.OptionFragmentPopupWindowAddUpdateUserInfo);
            optionFragmentTextButton.setOnClickListener(this);
        }
        else if (v.getId() == R.id.OptionFragmentPopupWindowAddUpdateUserInfo){
            /* Set user information */
            try {
                String name = optionFragmentPlainTextName.getText().toString();
                String surname = optionFragmentPlainTextSurname.getText().toString();
                String email = optionFragmentPlainTextEmail.getText().toString();
                int matr = Integer.parseInt(optionFragmentPlainTextMatr.getText().toString());

                Thread t = new Thread(new setUserInformation(getContext(), UserInformationPopupWindow, this, name, surname, email, matr));
                t.start();
            }
            catch (NumberFormatException e){
                optionFragmentTextButton.setText("Error, check matr, and try again.");
                optionFragmentTextButton.setTextColor(getResources().getColor(R.color.red));
            }
        }
        else if (v.getId() == R.id.Option_Fragment_Question_Mark_User_Information ||
                v.getId() == R.id.Option_Fragment_Question_Mark_Backup ||
                v.getId() == R.id.Option_Fragment_Question_Mark_Manage_Course){
            /* Show information about  */
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = layoutInflater.inflate(R.layout.generic_popup_window, null);

            questionMarkPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT, true);
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

            switch (v.getId()){
                case R.id.Option_Fragment_Question_Mark_User_Information:
                    title.setText("More about user information.");
                    text.setText(   "User information are information need by the app for download or" +
                                    " load progress backup form the database, all this data" +
                                    " could be see by authorized people only (to check the information" +
                                    " of user, that need to be students of university of UNIMORE.)" +
                                    " if you aren't a student of UNIMORE you could louse all your progress.");
                    break;
                case R.id.Option_Fragment_Question_Mark_Backup:
                    title.setText("More about backup.");
                    text.setText(   "Backup is essential to keep your progress save, and being able to " +
                                    "change device without lose some important feature of this app " +
                                    "such as the algorithms to sort question as you needed, or " +
                                    "the statistic showed with graph to monitor your progress.\n" +
                                    "Downloading the backup will override your local progress " +
                                    "and uploading your backup will override your cloud backup.\n" +
                                    "All the information store in the cloud are not seen by any " +
                                    "professor or used to set school grades, but they can be used " +
                                    "to make generic (non individual) graph for the professor or " +
                                    "anyone else.");
                    break;
                case R.id.Option_Fragment_Question_Mark_Manage_Course:
                    title.setText("More about manage courses.");
                    text.setText(   "Manage your courses, such as remove courses, add courses or erase " +
                                    "information about courses.\n" +
                                    "Deleting a course will automatically delete all your statistic " +
                                    "about it.");
                    break;
            }
        }
    }
}