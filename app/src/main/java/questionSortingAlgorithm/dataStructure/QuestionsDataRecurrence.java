package questionSortingAlgorithm.dataStructure;

import localDatabase.Tables.Question;

public class QuestionsDataRecurrence extends QuestionsDataAbstract implements Comparable<QuestionsDataRecurrence> {
    int occurrence;

    public QuestionsDataRecurrence(Question question, int occurrence) {
        super(question);
        this.occurrence = occurrence;
    }

    /* Getter. */
    public int getOccurrence() {
        return occurrence;
    }

    /* Setter. */
    public void setOccurrence(int occurrence) {
        this.occurrence = occurrence;
    }

    /* compareTo. */
    @Override
    public int compareTo(QuestionsDataRecurrence o) {
        if(o.getOccurrence() == this.getOccurrence()){
            return this.getQuestion().getQid() - o.getQuestion().getQid();
        }
        return this.getOccurrence() - o.getOccurrence();
    }
}
