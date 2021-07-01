package localDatabase.Tables;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Course.class,
        parentColumns = "id",
        childColumns = "idCourse",
        onUpdate = CASCADE))
public class StatisticUser {
    /* Generated id as the primary for being able to insert multiple record on the same date,
    *  Course with the same points. */
    @PrimaryKey(autoGenerate = true)
    public int id;
    @NonNull
    public int idCourse;
    @NonNull
    public int date;
    @NonNull
    public int points; /* [0 - 100] */

    public StatisticUser(@NonNull int idCourse, @NonNull int date, int points) {
        this.idCourse = idCourse;
        this.date = date;
        this.points = points;
    }

    /*      Getter      */
    public int getId() { return id; }
    @NonNull public int getCourse() { return idCourse; }
    @NonNull public int getDate() { return date; }
    public int getPoints() { return points; }

    /*      Setter      */
    public void setCourse(@NonNull int course) { this.idCourse = course; }
    public void setDate(@NonNull int date) { this.date = date; }
    public void setPoints(int points) { this.points = points; }

    /*      toString        */
    @Override
    public String toString() {
        return "StatisticUser{" +
                "id=" + id +
                ", course=" + idCourse +
                ", date=" + date +
                ", points=" + points +
                '}';
    }
}
