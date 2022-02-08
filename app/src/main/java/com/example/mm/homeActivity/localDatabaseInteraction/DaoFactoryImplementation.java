package com.example.mm.homeActivity.localDatabaseInteraction;

import android.content.Context;

import androidx.room.Room;

import localDatabase.LocalDatabase;
import localDatabase.LocalDatabaseDao;

public class DaoFactoryImplementation implements DaoFactory {
    Context applicationContext;

    public DaoFactoryImplementation(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public LocalDatabaseDao getDao() {
        LocalDatabase localDatabase = Room.databaseBuilder(applicationContext, LocalDatabase.class, "LocalDatabase")
                .fallbackToDestructiveMigration()
                .build();
        return localDatabase.localDatabaseDao();
    }
}
