package localDatabase.Tables;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Course.class,
        parentColumns = "id",
        childColumns = "idCourse",
        onUpdate = CASCADE))
public class Question {
    @PrimaryKey
    public int qid;
    @NonNull
    public int idCourse;
    @NonNull
    @ColumnInfo(name = "lastChanges")
    public String lastChanges;
    @NonNull
    @ColumnInfo(name = "questionText")
    public String questionText;
    @NonNull
    @ColumnInfo(name = "rightAnswer")
    public String rightAnswer;
    @NonNull
    @ColumnInfo(name = "wrongAnswer1")
    public String wrongAnswer1;
    @NonNull
    @ColumnInfo(name = "wrongAnswer2")
    public String wrongAnswer2;
    @NonNull
    @ColumnInfo(name = "wrongAnswer3")
    public String wrongAnswer3;

    public Question(@NonNull int qid, @NonNull int idCourse, @NonNull String lastChanges, @NonNull String questionText, @NonNull String rightAnswer, @NonNull String wrongAnswer1, @NonNull String wrongAnswer2, @NonNull String wrongAnswer3) {
        this.qid = qid;
        this.idCourse = idCourse;
        this.lastChanges = lastChanges;
        this.questionText = questionText;
        this.rightAnswer = rightAnswer;
        this.wrongAnswer1 = wrongAnswer1;
        this.wrongAnswer2 = wrongAnswer2;
        this.wrongAnswer3 = wrongAnswer3;
    }

    /*      Getter      */
    public int getQid() {
        return qid;
    }
    public int getIdCourse() {
        return idCourse;
    }
    @NonNull public String getLastChanges() {
        return lastChanges;
    }
    @NonNull public String getQuestionText() {
        return questionText;
    }
    @NonNull public String getRightAnswer() {
        return rightAnswer;
    }
    @NonNull public String getWrongAnswer1() {
        return wrongAnswer1;
    }
    @NonNull public String getWrongAnswer2() {
        return wrongAnswer2;
    }
    @NonNull public String getWrongAnswer3() {
        return wrongAnswer3;
    }

    /*      Setter      */
    public void setQid(int qid) {
        this.qid = qid;
    }
    public void setIdCourse(int idCourse) {
        this.idCourse = idCourse;
    }
    public void setLastChanges(@NonNull String lastChanges) {
        this.lastChanges = lastChanges;
    }
    public void setQuestionText(@NonNull String questionText) {
        this.questionText = questionText;
    }
    public void setRightAnswer(@NonNull String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }
    public void setWrongAnswer1(@NonNull String wrongAnswer1) {
        this.wrongAnswer1 = wrongAnswer1;
    }
    public void setWrongAnswer2(@NonNull String wrongAnswer2) {
        this.wrongAnswer2 = wrongAnswer2;
    }
    public void setWrongAnswer3(@NonNull String wrongAnswer3) {
        this.wrongAnswer3 = wrongAnswer3;
    }

    /*      toString        */
    @NonNull
    @Override
    public String toString() {
        return "Question{" +
                "qid=" + qid +
                ", lastChanges='" + lastChanges + '\'' +
                ", questionText='" + questionText + '\'' +
                ", rightAnswer='" + rightAnswer + '\'' +
                ", wrongAnswer1='" + wrongAnswer1 + '\'' +
                ", wrongAnswer2='" + wrongAnswer2 + '\'' +
                ", wrongAnswer3='" + wrongAnswer3 + '\'' +
                '}';
    }
}