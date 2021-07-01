package localDatabaseInteraction;

import android.app.Activity;
import android.content.Context;
import android.database.SQLException;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Dao;
import androidx.room.Room;

import com.example.mm.homeActivity.Home;
import com.example.mm.homeActivity.StatisticFragment;

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
    TextView textView;

    StringBuilder stringBuilder;

    public getCourse(Context context, StatisticFragment statisticFragment, TextView textView) {
        this.context = context;
        localDatabase = Room.databaseBuilder(context, LocalDatabase.class, "LocalDatabase")
                .fallbackToDestructiveMigration() /* Is needed to overwrite the old scheme of the
                                                   *  local database, it will ERASE all the current
                                                   *  data.
                                                   * */
                .build();
        localDatabaseDao = localDatabase.localDatabaseDao();
        this.statisticFragment = statisticFragment;
        this.textView = textView;
    }

    @Override
    public void run() {
        stringBuilder = new StringBuilder();
        List<Course> courses = new ArrayList<Course>();
        try {
            courses = localDatabaseDao.getAllCourse();
            if(courses.isEmpty()) {
                this.updateGraph("Nessun corso trovato.");
                return;
            }
        }
        catch(SQLException e){
            this.updateGraph("Errore, lettura da database.");
            return;
        }
        for (Course c : courses){
            stringBuilder.append(c.getName());
            stringBuilder.append("\n");
        }
        this.updateGraph(stringBuilder.toString());
        return;
    }

    void updateGraph(String string){
        if (context instanceof Activity) {
            Activity mainActivity = (Activity)context;
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText(string);
                }
            });
        }
    }
}
