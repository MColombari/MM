package com.example.mm.homeActivity.localDatabaseInteraction;

import android.content.Context;

import androidx.room.Room;

import localDatabase.LocalDatabase;
import localDatabase.LocalDatabaseDao;

public class SingletonDao {
    private static LocalDatabaseDao localDatabaseDao = null;

    public static LocalDatabaseDao getDao(Context context){
        if(localDatabaseDao == null){
            LocalDatabase localDatabase = Room.databaseBuilder(context, LocalDatabase.class, "LocalDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
            localDatabaseDao = localDatabase.localDatabaseDao();
        }
        return localDatabaseDao;
    }
}
