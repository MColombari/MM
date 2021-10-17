package questionSortingAlgorithm.dataStructure;

import localDatabase.Tables.Question;

public class QuestionsDataRecurrence extends QuestionsDataAbstract implements Comparable<QuestionsDataRecurrence> {
    int occurrence;

    public QuestionsDataRecurrence(Question question, int occurrence) {
        super(question);
        this.occurrence = occurrence;
    }

    public QuestionsDataRecurrence(Question question) {
        super(question);
        occurrence = 0;
    }

    public int getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(int occurrence) {
        this.occurrence = occurrence;
    }

    @Override
    public int compareTo(QuestionsDataRecurrence o) {
        if(o.getOccurrence() == this.getOccurrence()){
            return this.getQuestion().getQid() - o.getQuestion().getQid();
        }
        return this.getOccurrence() - o.getOccurrence();
    }
}
