package localDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Dao;
import androidx.room.EmptyResultSetException;
import androidx.room.Room;
import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.widget.TextView;

import com.example.mm.Home;
import com.example.mm.R;

import java.sql.SQLException;
import java.util.List;

public class TestApp implements Runnable{
    QuestionDao qDao;
    QuestionDatabase db;
    Home home;
    TextView mainTextView;
    int color;
    public TestApp(Context context, Home home, TextView mainTextView) {
        db = Room.databaseBuilder(context, QuestionDatabase.class, "Questions").build();
        qDao = db.questionDao();
        this.home = home;
        this.mainTextView = mainTextView;
    }

    public void run(){
        List<Question> q = null;
        StringBuilder val = new StringBuilder();

        val.append("Ritorno:\n");

        color = home.getResources().getColor(R.color.green);

        qDao.deleteAll();

        /* Room query can throw "SQLiteException" in case of errors */
        try {
            qDao.insertAll(new Question(1, "Come ti chiami?"));
        }
        catch (SQLiteException e) {
            color = home.getResources().getColor(R.color.red);
            val.append("Errore sulle operazioni preliminari (SQLiteException)\n");
        }

        q = qDao.getAll();

        if(q.isEmpty()){
            val.append("Non sono presenti elementi.\n");
        }

        for (Question i : q) {
            val.append(i.toString());
            val.append('\n');
        }

        /* The interrogation of the local db needs to be in a different Thread than the main one,
        *  but is not possible to change element of a View in a different thread than the view it
        *  self, for this reason i used "runOnUiThread" on the View than contains the element that i
        *  want change, it runs the specified action (change the element) on the UI thread (home).
        *  The action is posted to the event queue of the UI thread.
        * */
        home.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainTextView.setTextColor(color);
                mainTextView.setText(val.toString());
            }
        });
    }
}
