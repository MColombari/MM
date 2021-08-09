package questionSortingAlgorithm;

import java.util.ArrayList;
import java.util.Collections;

import questionSortingAlgorithm.dataStructure.QuestionsDataAbstract;
import questionSortingAlgorithm.dataStructure.QuestionsDataRecurrence;

public class RecurrenceSorting{
    ArrayList<QuestionsDataRecurrence> data;

    public RecurrenceSorting(ArrayList<QuestionsDataRecurrence> data) {
        this.data = data;
    }

    public ArrayList<QuestionsDataRecurrence> sortQuestions() {
        Collections.sort(data);
        return data;
    }
}
