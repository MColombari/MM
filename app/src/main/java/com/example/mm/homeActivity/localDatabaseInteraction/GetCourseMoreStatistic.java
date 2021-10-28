package com.example.mm.homeActivity.localDatabaseInteraction;

import android.app.Activity;
import android.content.Context;
import android.database.SQLException;
import androidx.room.Room;
import com.example.mm.R;
import com.example.mm.homeActivity.statisticFragment.MoreStatisticFragment;
import com.example.mm.homeActivity.statisticFragment.RecyclerViewRowData;
import com.example.mm.homeActivity.statisticFragment.RecyclerViewRowRecordData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import localDatabase.LocalDatabase;
import localDatabase.LocalDatabaseDao;
import localDatabase.Tables.Course;
import localDatabase.Tables.StatisticUser;

public class GetCourseMoreStatistic implements Runnable{
    Context context;
    LocalDatabaseDao localDatabaseDao;
    LocalDatabase localDatabase;
    MoreStatisticFragment moreStatisticFragment;

    ArrayList<RecyclerViewRowData> recyclerViewRowDataArrayList;

    public GetCourseMoreStatistic(Context context, Context contextDatabase, MoreStatisticFragment moreStatisticFragment) {
        this.context = context;
        localDatabase = Room.databaseBuilder(contextDatabase, LocalDatabase.class, "LocalDatabase")
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
                this.updateMoreStatistic("No courses found", R.color.red, true);
                return;
            }
            for (Course c : coursesList){
                List<StatisticUser> statisticUsers = localDatabaseDao.getAllStatisticUserByIdCourse(c.getId());
                ArrayList<RecyclerViewRowRecordData> recordDataArrayListTmp = new ArrayList<>();
                ArrayList<RecyclerViewRowRecordData> recordDataArrayListFinal = new ArrayList<>();
                float value = 0;
                float lastValue = 0;
                if(!statisticUsers.isEmpty()) {
                    Collections.sort(statisticUsers);
                    for (StatisticUser s : statisticUsers) {
                        recordDataArrayListTmp.add(new RecyclerViewRowRecordData(s.getPoints(), s.getDate()));
                        value += s.getPoints();
                        lastValue = s.getPoints();
                    }
                    value /= statisticUsers.size();

                    int prevDate = statisticUsers.get(0).getDate();
                    recordDataArrayListFinal.add(new RecyclerViewRowRecordData(0, prevDate));
                    int num = 0;
                    for(RecyclerViewRowRecordData r : recordDataArrayListTmp) {
                        if(prevDate == r.getDate()){
                            recordDataArrayListFinal.get(recordDataArrayListFinal.size() - 1).setValue(recordDataArrayListFinal.get(recordDataArrayListFinal.size() - 1).getValue() + r.getValue());
                            num++;
                            continue;
                        }
                        recordDataArrayListFinal.get(recordDataArrayListFinal.size() - 1).setValue(recordDataArrayListFinal.get(recordDataArrayListFinal.size() - 1).getValue() / num);
                        num = 1;
                        recordDataArrayListFinal.add(new RecyclerViewRowRecordData(r.getValue(), r.getDate()));
                        prevDate = r.getDate();
                    }
                    recordDataArrayListFinal.get(recordDataArrayListFinal.size() - 1).setValue(recordDataArrayListFinal.get(recordDataArrayListFinal.size() - 1).getValue() / num);
                }
                if((value - lastValue) > 0){
                    recyclerViewRowDataArrayList.add(new RecyclerViewRowData(c.getName(), value, R.drawable.red_triangle, recordDataArrayListFinal));
                }
                else if((value - lastValue) < 0){
                    recyclerViewRowDataArrayList.add(new RecyclerViewRowData(c.getName(), value, R.drawable.green_triangle, recordDataArrayListFinal));
                }
                else{
                    recyclerViewRowDataArrayList.add(new RecyclerViewRowData(c.getName(), value, R.drawable.orange_rectangle, recordDataArrayListFinal));
                }
            }
        }
        catch(SQLException e){
            this.updateMoreStatistic("Error, local database", R.color.red, false);
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
