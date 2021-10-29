package localDatabase.Tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity()
public class QuickResumeDataIds {
    @PrimaryKey
    public int courseId;

    public QuickResumeDataIds(int courseId) {
        this.courseId = courseId;
    }

    /*      Getter      */
    public int getCourseId() { return courseId; }

    /*      Setter      */
    public void setCourseId(int courseId) { this.courseId = courseId; }
}
