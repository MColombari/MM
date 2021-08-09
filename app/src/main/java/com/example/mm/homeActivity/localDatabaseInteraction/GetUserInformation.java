package com.example.mm.homeActivity.localDatabaseInteraction;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteException;

import androidx.room.Room;

import com.example.mm.R;
import com.example.mm.homeActivity.optionFragment.OptionFragment;
import com.example.mm.optionActivity.Option;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import localDatabase.LocalDatabase;
import localDatabase.LocalDatabaseDao;
import localDatabase.Tables.UserInformation;

public class GetUserInformation implements  Runnable {
    Context context;
    OptionFragment optionFragment;
    LocalDatabase localDatabase;
    LocalDatabaseDao localDatabaseDao;

    List<UserInformation> userInformations;

    public GetUserInformation(Context context, OptionFragment optionFragment) {
        this.context = context;
        this.optionFragment = optionFragment;

        localDatabase = Room.databaseBuilder(context, LocalDatabase.class, "LocalDatabase")
                .fallbackToDestructiveMigration()  /* Is needed to overwrite the old scheme of the
                                                    *  local database, it will ERASE all the current
                                                    *  data.
                                                    * */
                .build();
        localDatabaseDao = localDatabase.localDatabaseDao();
    }

    @Override
    public void run() {
        try{
            userInformations = localDatabaseDao.getAllUserInformation();

            if(userInformations.isEmpty()){
                this.updateStatistic("No user information was found.", "", "", "", "Add User Information");
            }
            else if(userInformations.size() > 1){
                localDatabaseDao.deleteAllUserInformation();
                this.updateStatistic("Multiple user were found,", "", "they all have been deleted.", "", "Add User Information");
            }
            else {
                String name = "Name: " + userInformations.get(0).getName();
                String surname = "Surname: " + userInformations.get(0).getSurname();
                String email = "Email: " + userInformations.get(0).getEmail();
                String matr = "Matr.: " + Integer.toString(userInformations.get(0).getMatr());

                updateStatistic(name, surname, email, matr);
            }
        }
        catch(SQLiteException e){
            this.updateStatistic("Errore,", "", "Lettura/Scrittura database", "", "Add User Information");
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
    void updateStatistic(String name, String surname, String email, String matr, String button){
        if (context instanceof Activity) {
            Activity mainActivity = (Activity)context;
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    optionFragment.updateInfo(name, surname, email, matr, context.getString(R.string.underline_Add_User_Information));
                }
            });
        }
    }
}