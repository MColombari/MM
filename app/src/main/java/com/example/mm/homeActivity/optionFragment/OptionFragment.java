package com.example.mm.homeActivity.optionFragment;

import android.graphics.Paint;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.mm.R;
import com.example.mm.homeActivity.externalServerInteraction.Sync;
import com.example.mm.homeActivity.localDatabaseInteraction.GetUserInfoInterface;
import com.example.mm.homeActivity.localDatabaseInteraction.GetUserInformation;
import com.example.mm.homeActivity.localDatabaseInteraction.SetUserInfoInterface;
import com.example.mm.homeActivity.localDatabaseInteraction.SetUserInformation;
import com.example.mm.homeActivity.localDatabaseInteraction.SingletonDao;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import localDatabase.LocalDatabaseDao;

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

    ImageView imageViewSyncButton;
    ImageView optionFragmentQuestionMarkUserInformation;
    ImageView optionFragmentQuestionMarkSync;
    ImageView optionFragmentQuestionMarkMoreAboutUs;

    View view;
    PopupWindow UserInformationPopupWindow;
    PopupWindow questionMarkPopupWindow;

    LocalDatabaseDao localDatabaseDao;

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

        imageViewSyncButton = (ImageView) view.findViewById(R.id.ImageViewSyncButton);
        optionFragmentQuestionMarkUserInformation = (ImageView) view.findViewById(R.id.Option_Fragment_Question_Mark_User_Information);
        optionFragmentQuestionMarkSync = (ImageView) view.findViewById(R.id.Option_Fragment_Question_Mark_Sync);
        optionFragmentQuestionMarkMoreAboutUs = (ImageView) view.findViewById(R.id.Option_Fragment_Question_Mark_More_About_Us);

        textViewUpdateInfo.setOnClickListener(this);
        imageViewSyncButton.setOnClickListener(this);
        optionFragmentQuestionMarkUserInformation.setOnClickListener(this);
        optionFragmentQuestionMarkSync.setOnClickListener(this);
        optionFragmentQuestionMarkMoreAboutUs.setOnClickListener(this);

        localDatabaseDao = SingletonDao.getDao(getActivity().getApplicationContext());

        Thread t = new Thread(new GetUserInformation(getContext(), (GetUserInfoInterface) localDatabaseDao, this));
        t.start();
    }

    public void updateInfo(String name, String surname, String email, String matr){
        textViewName.setText(name);
        textViewSurname.setText(surname);
        textViewEmail.setText(email);
        textViewMatr.setText(matr);
        textViewUpdateInfo.setText(requireContext().getString(R.string.underline_Update_Information));
        textViewUpdateInfo.setPaintFlags(textViewUpdateInfo.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        textViewUpdateInfo.setTextColor(getResources().getColor(R.color.black));
    }
    public void updateInfo(String name, String surname, String email, String matr, String buttonText){
        textViewName.setText(name);
        textViewSurname.setText(surname);
        textViewEmail.setText(email);
        textViewMatr.setText(matr);
        textViewUpdateInfo.setText(buttonText);
        textViewUpdateInfo.setPaintFlags(textViewUpdateInfo.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        textViewUpdateInfo.setTextColor(getResources().getColor(R.color.red));
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == textViewUpdateInfo.getId()){
            /* Update User Informations. */
            LayoutInflater layoutInflater = (LayoutInflater) requireContext().getSystemService(LAYOUT_INFLATER_SERVICE);
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
        else if (v.getId() == R.id.ImageViewSyncButton){
            LayoutInflater layoutInflater = (LayoutInflater) requireContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = layoutInflater.inflate(R.layout.loading_popup_window, null);

            PopupWindow genericPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT, false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                genericPopupWindow.setElevation(20);
            }
            genericPopupWindow.setAnimationStyle(R.style.AnimationGenericPopupWindow);
            genericPopupWindow.update();
            /* "v" is used as a parent view to get the View.getWindowToken() token from. */
            genericPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

            TextView text = (TextView) popupView.findViewById(R.id.Generic_Popup_Window_Text);
            TextView title = (TextView) popupView.findViewById(R.id.Generic_Popup_Window_Title);
            ProgressBar progressBar = (ProgressBar) popupView.findViewById(R.id.progress_bar);

            progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.cyan), android.graphics.PorterDuff.Mode.SRC_IN);
            progressBar.setMax(100);

            title.setText("Synchronization");
            text.setText("Connection to server...");
            text.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

            Thread thread = new Thread(new Sync(getContext(), getActivity().getApplicationContext(), genericPopupWindow, text, progressBar));
            thread.start();
        }
        else if (v.getId() == R.id.OptionFragmentPopupWindowAddUpdateUserInfo){
            /* Set user informations. */
            try {
                String name = optionFragmentPlainTextName.getText().toString();
                String surname = optionFragmentPlainTextSurname.getText().toString();
                String email = optionFragmentPlainTextEmail.getText().toString();
                int matr = Integer.parseInt(optionFragmentPlainTextMatr.getText().toString());

                if(name.isEmpty() || surname.isEmpty() || email.isEmpty()){
                    optionFragmentTextButton.setText("Error, empty parameter, try again.");
                    optionFragmentTextButton.setPaintFlags(optionFragmentTextButton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                    optionFragmentTextButton.setTextColor(getResources().getColor(R.color.red));
                    return;
                }

                Thread t = new Thread(new SetUserInformation(getContext(), (SetUserInfoInterface) localDatabaseDao, UserInformationPopupWindow, this, name, surname, email, matr));
                t.start();
            }
            catch (NumberFormatException e){
                optionFragmentTextButton.setText("Error, check matr, and try again.");
                optionFragmentTextButton.setPaintFlags(optionFragmentTextButton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                optionFragmentTextButton.setTextColor(getResources().getColor(R.color.red));
            }
        }
        else if (v.getId() == R.id.Option_Fragment_Question_Mark_User_Information ||
                v.getId() == R.id.Option_Fragment_Question_Mark_Sync ||
                v.getId() == R.id.Option_Fragment_Question_Mark_More_About_Us){
            /* Show information about  */
            LayoutInflater layoutInflater = (LayoutInflater) requireContext().getSystemService(LAYOUT_INFLATER_SERVICE);
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
                                    " load backup from the database, all this data" +
                                    " could be see by authorized people only (to check the information" +
                                    " of user, that need to be students of university of UNIMORE.)");
                    break;
                case R.id.Option_Fragment_Question_Mark_Sync:
                    title.setText("More about sync.");
                    text.setText(   "Sync is a function to download the most recent Questions and Courses" +
                                    " from the external database, it also will send to it the user information.");
                    break;
                case R.id.Option_Fragment_Question_Mark_More_About_Us:
                    title.setText("More About Us.");
                    text.setText(   "Information about \"the programmer\" who made this project.");
                    break;
            }
        }
    }
}