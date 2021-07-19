package com.example.mm.homeActivity.localDatabaseInteraction;

import android.app.Activity;
import android.content.Context;
import android.database.SQLException;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.Room;

import com.example.mm.R;
import com.example.mm.homeActivity.statisticFragment.MoreStatisticFragment;
import com.example.mm.homeActivity.statisticFragment.RecyclerViewRowData;
import com.example.mm.homeActivity.statisticFragment.RecyclerViewRowRecordData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import localDatabase.LocalDatabase;
import localDatabase.LocalDatabaseDao;
import localDatabase.Tables.Course;
import localDatabase.Tables.StatisticUser;

public class getCourseMoreStatistic implements Runnable{
    Context context;
    LocalDatabaseDao localDatabaseDao;
    LocalDatabase localDatabase;
    MoreStatisticFragment moreStatisticFragment;

    ArrayList<RecyclerViewRowData> recyclerViewRowDataArrayList;

    public getCourseMoreStatistic(Context context, MoreStatisticFragment moreStatisticFragment) {
        this.context = context;
        localDatabase = Room.databaseBuilder(context, LocalDatabase.class, "LocalDatabase")
                .fallbackToDestructiveMigration()
                .build();
        localDatabaseDao = localDatabase.localDatabaseDao();
        this.moreStatisticFragment = moreStatisticFragment;

        recyclerViewRowDataArrayList = new ArrayList<>();
    }

    @Override
    public void run() {
        List<Course> coursesList;
        try {
            coursesList = localDatabaseDao.getAllCourse();
            if(coursesList.isEmpty()) {
                this.updateMoreStatistic("Nessun corso trovato.", R.color.red, true);
                return;
            }
            for (Course c : coursesList){
                List<StatisticUser> statisticUsers = localDatabaseDao.getAllStatisticUserByIdCourse(c.getId());
                ArrayList<RecyclerViewRowRecordData> recordDataArrayList = new ArrayList<>();
                float value = 0;
                float lastValue = 0;
                if(!statisticUsers.isEmpty()) {
                    Collections.sort(statisticUsers);
                    for (StatisticUser s : statisticUsers) {
                        recordDataArrayList.add(new RecyclerViewRowRecordData(s.getPoints(), s.getDate()));
                        value += s.getPoints();
                        lastValue = s.getPoints();
                    }
                    value /= statisticUsers.size();
                }
                if((value - lastValue) > 0){
                    recyclerViewRowDataArrayList.add(new RecyclerViewRowData(c.getName(), value, R.drawable.red_triangle, recordDataArrayList));
                }
                else if((value - lastValue) < 0){
                    recyclerViewRowDataArrayList.add(new RecyclerViewRowData(c.getName(), value, R.drawable.green_triangle, recordDataArrayList));
                }
                else{
                    recyclerViewRowDataArrayList.add(new RecyclerViewRowData(c.getName(), value, R.drawable.orange_rectangle, recordDataArrayList));
                }
            }
        }
        catch(SQLException e){
            this.updateMoreStatistic("Errore, lettura da database", R.color.red, false);
            return;
        }
        this.updateMoreStatistic("Updated", R.color.green, false);
    }

    void updateMoreStatistic(String status, int color, boolean error){
        if (context instanceof Activity) {
            Activity mainActivity = (Activity)context;
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    moreStatisticFragment.updateStatus(status, color);
                    if(!error){
                        Collections.sort(recyclerViewRowDataArrayList);
                        moreStatisticFragment.updateRecycleView(recyclerViewRowDataArrayList);
                    }
                }
            });
        }
    }
}
