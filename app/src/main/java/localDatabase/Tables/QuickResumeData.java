package localDatabase.Tables;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity()
public class QuickResumeData {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @NonNull
    public int numberOfQuestion;
    @NonNull
    public int sortOption;

    public QuickResumeData(int numberOfQuestion, int sortOption) {
        this.numberOfQuestion = numberOfQuestion;
        this.sortOption = sortOption;
    }

    /*      Getter      */
    public int getNumberOfQuestion() { return numberOfQuestion; }
    public int getSortOption() { return sortOption; }

    /*      Setter      */
    public void setNumberOfQuestion(int numberOfQuestion) { this.numberOfQuestion = numberOfQuestion; }
    public void setSortOption(int sortOption) { this.sortOption = sortOption; }
}
