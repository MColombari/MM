package questionSortingAlgorithm.dataStructure;

import localDatabase.Tables.Question;

public abstract class QuestionsDataAbstract {
    Question question;

    public QuestionsDataAbstract(Question question) {
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "QuestionsDataAbstract{" +
                "question=" + question +
                '}';
    }
}
