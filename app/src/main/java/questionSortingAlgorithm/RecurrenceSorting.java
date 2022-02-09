package questionSortingAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import questionSortingAlgorithm.dataStructure.QuestionsDataRecurrence;

public class RecurrenceSorting implements SortingAlgorithm{
    ArrayList<QuestionsDataRecurrence> data;

    public RecurrenceSorting(ArrayList<QuestionsDataRecurrence> data) {
        this.data = data;
    }

    public ArrayList<? extends Comparable> sortQuestions() {
        Collections.sort(data);
        return data;
    }
}
