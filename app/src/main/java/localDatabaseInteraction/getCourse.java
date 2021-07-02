package localDatabaseInteraction;

import android.app.Activity;
import android.content.Context;
import android.database.SQLException;
import android.graphics.Color;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Dao;
import androidx.room.Room;

import com.example.mm.R;
import com.example.mm.homeActivity.Home;
import com.example.mm.homeActivity.StatisticFragment;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import localDatabase.LocalDatabase;
import localDatabase.LocalDatabaseDao;
import localDatabase.Tables.Course;
import localDatabase.Tables.StatisticUser;

public class getCourse implements Runnable {
    Context context;
    LocalDatabaseDao localDatabaseDao;
    LocalDatabase localDatabase;
    StatisticFragment statisticFragment;
    ArrayList<String> courses;
    ArrayList<Float> values;

    public getCourse(Context context, StatisticFragment statisticFragment) {
        this.context = context;
        localDatabase = Room.databaseBuilder(context, LocalDatabase.class, "LocalDatabase")
                .fallbackToDestructiveMigration() /* Is needed to overwrite the old scheme of the
                                                   *  local database, it will ERASE all the current
                                                   *  data.
                                                   * */
                .build();
        localDatabaseDao = localDatabase.localDatabaseDao();
        this.statisticFragment = statisticFragment;

        courses = new ArrayList<String>();
        values = new ArrayList<Float>();
    }

    @Override
    public void run() {
        List<Course> coursesList = new ArrayList<Course>();
        try {
            coursesList = localDatabaseDao.getAllCourse();
            if(coursesList.isEmpty()) {
                this.updateStatistic("Nessun corso trovato.", R.color.red, "None", true);
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
            this.updateStatistic("Errore, lettura da database.", R.color.red, "None", true);
            return;
        }
        this.updateStatistic("Updated", R.color.green, coursesList.get(0).getName(), false);
    }

    void updateStatistic(String statusText, int color, String courseText, boolean error){
        if (context instanceof Activity) {
            Activity mainActivity = (Activity)context;
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    statisticFragment.updateStatistic(statusText, color, courseText);
                    if(!error){
                        statisticFragment.updateGraph(courses, values);
                    }
                }
            });
        }
    }
}
