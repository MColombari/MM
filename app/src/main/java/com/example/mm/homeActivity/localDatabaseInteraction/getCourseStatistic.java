package com.example.mm.homeActivity.localDatabaseInteraction;

import android.app.Activity;
import android.content.Context;
import android.database.SQLException;
import androidx.room.Room;

import com.example.mm.R;
import com.example.mm.homeActivity.StatisticFragment;

import java.util.ArrayList;
import java.util.List;

import localDatabase.LocalDatabase;
import localDatabase.LocalDatabaseDao;
import localDatabase.Tables.Course;
import localDatabase.Tables.StatisticUser;

public class getCourseStatistic implements Runnable {
    Context context;
    LocalDatabaseDao localDatabaseDao;
    LocalDatabase localDatabase;
    StatisticFragment statisticFragment;
    ArrayList<String> courses;
    ArrayList<Float> values;

    public getCourseStatistic(Context context, StatisticFragment statisticFragment) {
        this.context = context;
        localDatabase = Room.databaseBuilder(context, LocalDatabase.class, "LocalDatabase")
                .fallbackToDestructiveMigration() /* Is needed to overwrite the old scheme of the
                                                   *  local database, it will ERASE all the current
                                                   *  data.
                                                   * */
                .build();
        localDatabaseDao = localDatabase.localDatabaseDao();
        this.statisticFragment = statisticFragment;

        courses = new ArrayList<>();
        values = new ArrayList<>();
    }

    @Override
    public void run() {
        List<Course> coursesList;
        try {
            coursesList = localDatabaseDao.getAllCourse();
            if(coursesList.isEmpty()) {
                this.updateStatistic("Nessun corso trovato.", R.color.red, true);
                return;
            }
            for (Course c : coursesList){
                courses.add(c.getName());
                List<StatisticUser> statisticUsers = localDatabaseDao.getAllStatisticUserByIdCourse(c.getId());
                float value = 0;
                if(!statisticUsers.isEmpty()) {
                    for (StatisticUser s : statisticUsers) {
                        value += s.getPoints();
                    }
                    value /= statisticUsers.size();
                }
                values.add(value);
            }
        }
        catch(SQLException e){
            this.updateStatistic("Errore, lettura da database.", R.color.red, true);
            return;
        }
        this.updateStatistic("Updated", R.color.green, false);
    }

    void updateStatistic(String statusText, int color, boolean error){
        if (context instanceof Activity) {
            Activity mainActivity = (Activity)context;
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    statisticFragment.updateStatus(statusText, color);
                    if(!error){
                        statisticFragment.updateGraph(courses, values);
                    }
                }
            });
        }
    }
}
