package com.example.mm.homeActivity.localDatabaseInteraction;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteException;
import androidx.room.Room;
import com.example.mm.R;
import com.example.mm.homeActivity.optionFragment.OptionFragment;
import java.util.List;
import localDatabase.LocalDatabase;
import localDatabase.LocalDatabaseDao;
import localDatabase.Tables.UserInformation;

public class GetUserInformation implements  Runnable {
    Context context;
    OptionFragment optionFragment;
    GetUserInfoInterface getUserInfoInterface;

    List<UserInformation> userInformations;

    public GetUserInformation(Context context, GetUserInfoInterface getUserInfoInterface, OptionFragment optionFragment) {
        this.context = context;
        this.getUserInfoInterface = getUserInfoInterface;
        this.optionFragment = optionFragment;
    }

    @Override
    public void run() {
        try{
            userInformations = getUserInfoInterface.getAllUserInformation();

            if(userInformations.isEmpty()){
                this.updateStatistic("No user information was found.", "");
            }
            else if(userInformations.size() > 1){
                getUserInfoInterface.deleteAllUserInformation();
                this.updateStatistic("Multiple user were found,", "they all have been deleted.");
            }
            else {
                String name = "Name: " + userInformations.get(0).getName();
                String surname = "Surname: " + userInformations.get(0).getSurname();
                String email = "Email: " + userInformations.get(0).getEmail();
                String matr = "Matr.: " + userInformations.get(0).getMatr();

                updateStatistic(name, surname, email, matr);
            }
        }
        catch(SQLiteException e){
            this.updateStatistic("Error,", "Local database");
        }
    }

    void updateStatistic(String name, String surname, String email, String matr){
        if (context instanceof Activity) {
            Activity mainActivity = (Activity)context;
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    optionFragment.updateInfo(name, surname, email, matr);
                }
            });
        }
    }
    void updateStatistic(String name, String email){
        if (context instanceof Activity) {
            Activity mainActivity = (Activity)context;
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    optionFragment.updateInfo(name, "", email, "", context.getString(R.string.underline_Add_User_Information));
                }
            });
        }
    }
}