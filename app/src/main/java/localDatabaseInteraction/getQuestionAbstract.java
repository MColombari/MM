package localDatabaseInteraction;

import java.util.ArrayList;

import localDatabase.Question;

public abstract class getQuestionAbstract implements  getQuestionInterface{
    public ArrayList<Question> Data = new ArrayList<>();
    int countQuestion = 0;
}
