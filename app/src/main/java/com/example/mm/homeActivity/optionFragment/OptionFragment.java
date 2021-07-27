package com.example.mm.homeActivity.optionFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.mm.R;
import com.example.mm.homeActivity.localDatabaseInteraction.getUserInformation;

import org.w3c.dom.Text;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class OptionFragment extends Fragment implements View.OnClickListener {
    TextView textViewName;
    TextView textViewSurname;
    TextView textViewEmail;
    TextView textViewMatr;
    TextView textViewUpdateInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_option, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

            PopupWindow popupWindow = new PopupWindow(popupView, 750,900, true);



            /* "v" is used as a parent view to get the View.getWindowToken() token from. */
            popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        }
    }
}