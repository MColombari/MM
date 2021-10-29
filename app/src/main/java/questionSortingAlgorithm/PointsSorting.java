package questionSortingAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import questionSortingAlgorithm.dataStructure.QuestionsDataPoints;

public class PointsSorting {
    ArrayList<QuestionsDataPoints> data;

    public PointsSorting(ArrayList<QuestionsDataPoints> data) { this.data = data; }

    public ArrayList<QuestionsDataPoints> sortQuestions(){
        Collections.sort(data);
        return data;
    }
}
