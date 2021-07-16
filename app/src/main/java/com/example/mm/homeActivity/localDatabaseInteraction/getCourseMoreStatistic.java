package com.example.mm.homeActivity.localDatabaseInteraction;

import android.app.Activity;
import android.content.Context;
import android.database.SQLException;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import androidx.room.Room;

import com.example.mm.R;
import com.example.mm.homeActivity.MoreStatisticFragment;

import java.util.ArrayList;
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

    ArrayList<String> courses;
    ArrayList<Float> values;
    ArrayList<Integer> imagesTrends;

    public getCourseMoreStatistic(Context context, MoreStatisticFragment moreStatisticFragment) {
        this.context = context;
        localDatabase = Room.databaseBuilder(context, LocalDatabase.class, "LocalDatabase")
                .fallbackToDestructiveMigration()
                .build();
        localDatabaseDao = localDatabase.localDatabaseDao();
        this.moreStatisticFragment = moreStatisticFragment;

        courses = new ArrayList<>();
        values = new ArrayList<>();
        imagesTrends = new ArrayList<>();
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
                courses.add(c.getName());
                List<StatisticUser> statisticUsers = localDatabaseDao.getAllStatisticUserByIdCourse(c.getId());
                float value = 0;
                float lastValue = 0;
                if(!statisticUsers.isEmpty()) {
                    for (StatisticUser s : statisticUsers) {
                        value += s.getPoints();
                        lastValue = s.getPoints();
                    }
                    value /= statisticUsers.size();
                }
                values.add(value);
                if((value - lastValue) > 0){
                    imagesTrends.add(R.drawable.red_triangle);
                }
                else if((value - lastValue) < 0){
                    imagesTrends.add(R.drawable.green_triangle);
                }
                else{
                    imagesTrends.add(R.drawable.orange_triangle);
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
                        moreStatisticFragment.updateRecycleView(courses, values, imagesTrends);
                    }
                }
            });
        }
    }
}
