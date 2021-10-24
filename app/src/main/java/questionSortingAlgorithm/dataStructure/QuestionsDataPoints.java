package questionSortingAlgorithm.dataStructure;

import localDatabase.Tables.Question;

public class QuestionsDataPoints extends QuestionsDataAbstract implements Comparable<QuestionsDataPoints>{
    int avgPoints;

    public QuestionsDataPoints(Question question, int avgPoints) {
        super(question);
        this.avgPoints = avgPoints;
    }

    public int getAvgPoints() { return avgPoints; }

    public void setAvgPoints(int avgPoints) { this.avgPoints = avgPoints; }

    @Override
    public int compareTo(QuestionsDataPoints o) {
        if(o.getAvgPoints() == this.getAvgPoints()){
            return this.getQuestion().getQid() - o.getQuestion().getQid();
        }
        return this.getAvgPoints() - o.getAvgPoints();
    }
}
