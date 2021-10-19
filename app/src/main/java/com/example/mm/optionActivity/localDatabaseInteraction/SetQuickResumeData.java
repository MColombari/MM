package com.example.mm.optionActivity.localDatabaseInteraction;

import android.content.Context;
import android.database.sqlite.SQLiteException;

import androidx.room.Room;

import java.util.ArrayList;

import localDatabase.LocalDatabase;
import localDatabase.LocalDatabaseDao;
import localDatabase.Tables.QuickResumeData;
import localDatabase.Tables.QuickResumeDataIds;

public class SetQuickResumeData implements Runnable {
    Context context;
    LocalDatabase localDatabase;
    LocalDatabaseDao localDatabaseDao;

    int numQuestion;
    int sortingAlgorithm;
    ArrayList<Integer> coursesIdsArrayList;

    public SetQuickResumeData(Context context, int numQuestion, int sortingAlgorithm, ArrayList<Integer> coursesIdsArrayList){
        this.context = context;
        this.numQuestion = numQuestion;
        this.sortingAlgorithm = sortingAlgorithm;
        this.coursesIdsArrayList = coursesIdsArrayList;

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
            localDatabaseDao.deleteAllQuickResumeData();
            localDatabaseDao.deleteAllQuickResumeDataIds();
            localDatabaseDao.insertQuickResumeData(new QuickResumeData(numQuestion, sortingAlgorithm));
            for(int i: coursesIdsArrayList){
                localDatabaseDao.insertQuickResumeDataIds(new QuickResumeDataIds(i));
            }
        }
        catch(SQLiteException e){
            /* I don't check for error in case this are not saved. */
        }
    }
}
