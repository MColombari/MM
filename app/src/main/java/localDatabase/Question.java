package localDatabase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Question {
    @PrimaryKey
    public int qid;

    @NonNull
    @ColumnInfo(name = "questionText")
    public String questionText;

    public Question(int qid, @NonNull String questionText) {
        this.qid = qid;
        this.questionText = questionText;
    }

    public int getQid() {
        return qid;
    }

    @NonNull
    public String getQuestionText() {
        return questionText;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public void setQuestionText(@NonNull String questionText) {
        this.questionText = questionText;
    }

    @Override
    public String toString() {
        return "Question{" +
                "qid=" + qid +
                ", questionText='" + questionText + '\'' +
                '}';
    }
}