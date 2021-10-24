package com.example.mm.exerciseActivity.localDatabaseInteraction;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteException;

import androidx.room.Room;

import com.example.mm.exerciseActivity.Exercise;

import java.util.ArrayList;
import java.util.List;

import localDatabase.LocalDatabase;
import localDatabase.LocalDatabaseDao;
import localDatabase.Tables.Question;
import questionSortingAlgorithm.PointsSorting;
import questionSortingAlgorithm.dataStructure.QuestionsDataPoints;

/* Debugging purpose:
*   Use this query to check if the average points per question:
*       select su.qidQuestion, q.questionText, avg(points) from statisticuser su left join question q  on (su.qidQuestion == q.qid) group by su.qidQuestion order by 3
*  */

public class GetQuestionByPoints extends GetQuestionAbstract implements Runnable {
    LocalDatabaseDao localDatabaseDao;
    LocalDatabase localDatabase;
    ArrayList<QuestionsDataPoints> questionsDataPointsArrayList;

    public GetQuestionByPoints(Context context, Exercise exerciseActivity, int[] courseIds) {
        super(context, exerciseActivity, courseIds);

        localDatabase = Room.databaseBuilder(context, LocalDatabase.class, "LocalDatabase")
                .fallbackToDestructiveMigration()
                .build();
        localDatabaseDao = localDatabase.localDatabaseDao();

        questionsDataPointsArrayList = new ArrayList<>();
    }

    @Override
    public void run() {
        try{
            List<Question> questionList = localDatabaseDao.getAllQuestionByCourseIds(courseIds);
            if(questionList.isEmpty()){
                this.updateQuestion();
                return;
            }
            for(Question q: questionList){
                int avgPoints = localDatabaseDao.getAveragePointsPerQuestion(q.getQid());
                questionsDataPointsArrayList.add(new QuestionsDataPoints(q, avgPoints));
            }
            PointsSorting pointsSorting = new PointsSorting(questionsDataPointsArrayList);
            questionsDataPointsArrayList = pointsSorting.sortQuestions();
            this.updateQuestion();
        }
        catch (SQLiteException e){
            this.updateQuestionError("Error local database.");
        }
    }

    @Override
    void updateQuestion() {
        if (exerciseActivity != null) {
            Activity mainActivity = exerciseActivity;
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    exerciseActivity.updateQuestion(questionsDataPointsArrayList);
                }
            });
        }
    }

    @Override
    void updateQuestionError(String errorString) {
        if (exerciseActivity != null) {
            Activity mainActivity = exerciseActivity;
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    exerciseActivity.updateQuestionError(errorString);
                }
            });
        }
    }
}