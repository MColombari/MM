package com.example.mm.exerciseActivity;

import java.util.ArrayList;

public class ResponseData {
    int questionId;
    String questionText;
    ArrayList<String> responseInOrder;
    int rightAnswer;
    int answerChose; /* if is equal to (-1) the answer has not been chose yet. */

    public ResponseData(int questionId, String questionText, ArrayList<String> questionInOrder, int rightAnswer, int answerChose) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.responseInOrder = questionInOrder;
        this.rightAnswer = rightAnswer;
        this.answerChose = answerChose;
    }

    /* Getter */
    public int getQuestionId() { return questionId; }
    public String getQuestionText() { return questionText; }
    public ArrayList<String> getResponseInOrder() { return responseInOrder; }
    public int getRightAnswer() { return rightAnswer; }
    public int getAnswerChose() { return answerChose; }

    /* Setter */
    public void setQuestionId(int questionId) { this.questionId = questionId; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }
    public void setResponseInOrder(ArrayList<String> responseInOrder) { this.responseInOrder = responseInOrder; }
    public void setRightAnswer(int rightAnswer) { this.rightAnswer = rightAnswer; }
    public void setAnswerChose(int answerChose) { this.answerChose = answerChose; }
}
