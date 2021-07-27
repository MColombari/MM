package com.example.mm.homeActivity.localDatabaseInteraction;

import android.content.Context;
import android.database.sqlite.SQLiteException;

import androidx.room.Room;

import com.example.mm.homeActivity.optionFragment.OptionFragment;

import localDatabase.LocalDatabase;
import localDatabase.LocalDatabaseDao;

public class setUserInformation implements Runnable {
    Context context;
    OptionFragment optionFragment;
    LocalDatabase localDatabase;
    LocalDatabaseDao localDatabaseDao;

    public setUserInformation(Context context, OptionFragment optionFragment, String name, String surname, String email, String matr) {
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

        }
        catch(SQLiteException e){

        }
    }
}
