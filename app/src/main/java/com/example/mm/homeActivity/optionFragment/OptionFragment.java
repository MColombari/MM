package com.example.mm.homeActivity.optionFragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    View view;
    PopupWindow popupWindow;

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

        textViewUpdateInfo.setOnClickListener(this);

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

            popupWindow = new PopupWindow(popupView, 900,1200, true);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                popupWindow.setElevation(20);
            }

            /* "v" is used as a parent view to get the View.getWindowToken() token from. */
            popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

            optionFragmentPlainTextName = (TextView) popupView.findViewById(R.id.OptionFragmentPlainTextName);
            optionFragmentPlainTextSurname = (TextView) popupView.findViewById(R.id.OptionFragmentPlainTextSurname);
            optionFragmentPlainTextEmail = (TextView) popupView.findViewById(R.id.OptionFragmentPlainTextEmail);
            optionFragmentPlainTextMatr = (TextView) popupView.findViewById(R.id.OptionFragmentPlainTextMatr);
            optionFragmentTextButton = (TextView) popupView.findViewById(R.id.OptionFragmentTextButton);
            optionFragmentTextButton.setOnClickListener(this);
        }
        else if (v.getId() == R.id.OptionFragmentTextButton){
            try {
                String name = optionFragmentPlainTextName.getText().toString();
                String surname = optionFragmentPlainTextSurname.getText().toString();
                String email = optionFragmentPlainTextEmail.getText().toString();
                int matr = Integer.parseInt(optionFragmentPlainTextMatr.getText().toString());

                Thread t = new Thread(new setUserInformation(getContext(), popupWindow, this, name, surname, email, matr));
                t.start();
            }
            catch (NumberFormatException e){
                optionFragmentTextButton.setText("Error, check matr, and try again.");
                optionFragmentTextButton.setTextColor(getResources().getColor(R.color.red));
            }
        }
    }
}