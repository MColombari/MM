package localDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Dao;
import androidx.room.Room;
import android.content.Context;
import android.widget.TextView;

import com.example.mm.Home;
import com.example.mm.R;

import java.util.List;

public class TestApp implements Runnable{
    QuestionDao qDao;
    QuestionDatabase db;
    TextView mainTextView;
    public TestApp(Context context, Home T) {
        db = Room.databaseBuilder(context, QuestionDatabase.class, "Questions").build();
        qDao = db.questionDao();
        this.mainTextView = T.findViewById(R.id.mainText);
    }

    public void run(){
        //qDao.insertAll(new Question(1, "Come ti chiami???"));
        //qDao.deleteAll();
        List<Question> q = qDao.getAll();

        StringBuilder val = new StringBuilder();

        if(q.isEmpty()) {
            val.append("Non sono presenti elementi");
            val.append('\n');
        }
        else {
            for (Question i : q) {
                val.append(i.toString());
                val.append('\n');
            }
        }

        mainTextView.setText(val.toString());
    }
}
