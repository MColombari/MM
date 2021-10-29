package utils;

import java.util.ArrayList;
import questionSortingAlgorithm.dataStructure.QuestionsDataAbstract;

public class CutArray{
    public static ArrayList<QuestionsDataAbstract> cutQuestionsDataRecurrence (ArrayList<? extends QuestionsDataAbstract> in, int start, int end){
        ArrayList<QuestionsDataAbstract> ret = new ArrayList<>();
        for(int i = start; i <= end; i++){
            ret.add(in.get(i));
        }
        return ret;
    }
}
