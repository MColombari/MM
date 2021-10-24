package Utils;

import java.util.ArrayList;

import questionSortingAlgorithm.dataStructure.QuestionsDataAbstract;
import questionSortingAlgorithm.dataStructure.QuestionsDataRecurrence;

public class CutArray{
    public static ArrayList<QuestionsDataAbstract> cutQuestionsDataRecurrence (ArrayList<? extends QuestionsDataAbstract> in, int start, int end) throws RuntimeException{
        /* Start and end are both included. */

        /* Check parameters. */
        if((start < 0) || (end < start) || (in.size() <= end)){
            throw new RuntimeException();
        }

        ArrayList<QuestionsDataAbstract> ret = new ArrayList<>();
        for(int i = start; i <= end; i++){
            ret.add(in.get(i));
        }
        return ret;
    }
}
