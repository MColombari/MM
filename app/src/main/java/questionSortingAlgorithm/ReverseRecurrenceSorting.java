package questionSortingAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import questionSortingAlgorithm.dataStructure.QuestionsDataRecurrence;

public class ReverseRecurrenceSorting {
    ArrayList<QuestionsDataRecurrence> data;

    public ReverseRecurrenceSorting(ArrayList<QuestionsDataRecurrence> data) { this.data = data; }

    public ArrayList<QuestionsDataRecurrence> sortQuestions(){
        Collections.sort(data, new Comparator<QuestionsDataRecurrence>() {
            @Override
            public int compare(QuestionsDataRecurrence o1, QuestionsDataRecurrence o2) {
                if(o1.getOccurrence() == o2.getOccurrence()){
                    return o2.getQuestion().getQid() - o1.getQuestion().getQid();
                }
                return o2.getOccurrence() - o1.getOccurrence();
            }
        });
        return data;
    }
}
