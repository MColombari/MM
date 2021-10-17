package Utils;

import java.util.ArrayList;

import questionSortingAlgorithm.dataStructure.QuestionsDataRecurrence;

public class CutArray{
    public static ArrayList<QuestionsDataRecurrence> cutQuestionsDataRecurrence (ArrayList<QuestionsDataRecurrence> in, int start, int end) throws RuntimeException{
        /* Start and end are both included. */

        /* Check parameters. */
        if((start < 0) || (end < start) || (in.size() <= end)){
            throw new RuntimeException();
        }

        ArrayList<QuestionsDataRecurrence> ret = new ArrayList<>();
        for(int i = start; i <= end; i++){
            ret.add(in.get(i));
        }
        return ret;
    }
}
