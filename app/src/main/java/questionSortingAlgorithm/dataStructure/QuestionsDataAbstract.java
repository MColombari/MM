package questionSortingAlgorithm.dataStructure;

import localDatabase.Tables.Question;

public abstract class QuestionsDataAbstract {
    Question question;

    public QuestionsDataAbstract(Question question) {
        this.question = question;
    }

    /* Getter. */
    public Question getQuestion() {
        return question;
    }

    /* Setter. */
    public void setQuestion(Question question) { this.question = question; }

    /* toString. */
    @Override
    public String toString() {
        return "QuestionsDataAbstract{" +
                "question=" + question +
                '}';
    }
}
