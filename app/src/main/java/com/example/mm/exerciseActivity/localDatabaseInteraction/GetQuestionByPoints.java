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
import questionSortingAlgorithm.dataStructure.QuestionsDataRecurrence;

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
        if (exerciseActivity instanceof Activity) {
            Activity mainActivity = (Activity) exerciseActivity;
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
        if (exerciseActivity instanceof Activity) {
            Activity mainActivity = (Activity)exerciseActivity;
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    exerciseActivity.updateQuestionError(errorString);
                }
            });
        }
    }
}