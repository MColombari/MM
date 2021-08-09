package localDatabase.Tables;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Question.class,
            parentColumns = "qid",
            childColumns = "qidQuestion",
            onUpdate = CASCADE)
)
public class StatisticUser implements Comparable<StatisticUser> {
    /* Generated id as the primary for being able to insert multiple record on the same date,
    *  Course with the same points. */
    @PrimaryKey(autoGenerate = true)
    public int id;
    @NonNull
    public int qidQuestion;
    /* Date format "yyyyMMdd". */
    @NonNull
    public int date;
    @NonNull
    public int points; /* [0 - 100] */

    public StatisticUser(@NonNull int qidQuestion, @NonNull int date, int points) {
        this.qidQuestion = qidQuestion;
        this.date = date;
        this.points = points;
    }

    /*      Getter      */
    public int getId() { return id; }
    public int getQidQuestion() { return qidQuestion; }
    @NonNull public int getDate() { return date; }
    public int getPoints() { return points; }

    /*      Setter      */
    public void setQidQuestion(int qidQuestion) { this.qidQuestion = qidQuestion; }
    public void setDate(@NonNull int date) { this.date = date; }
    public void setPoints(int points) { this.points = points; }

    /*      toString        */
    @Override
    public String toString() {
        return "StatisticUser{" +
                "id=" + id +
                ", qidQuestion=" + qidQuestion +
                ", date=" + date +
                ", points=" + points +
                '}';
    }

    @Override
    public int compareTo(StatisticUser o) {
        return this.getDate() - o.getDate();
    }
}
