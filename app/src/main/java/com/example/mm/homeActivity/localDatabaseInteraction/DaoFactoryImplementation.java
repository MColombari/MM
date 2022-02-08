package com.example.mm.homeActivity.localDatabaseInteraction;

import android.content.Context;

import androidx.room.Room;

import localDatabase.LocalDatabase;
import localDatabase.LocalDatabaseDao;

public class DaoFactoryImplementation implements DaoFactory {
    @Override
    public LocalDatabaseDao getDao(Context context) {
        LocalDatabase localDatabase = Room.databaseBuilder(context, LocalDatabase.class, "LocalDatabase")
                .fallbackToDestructiveMigration()
                .build();
        return localDatabase.localDatabaseDao();
    }
}
