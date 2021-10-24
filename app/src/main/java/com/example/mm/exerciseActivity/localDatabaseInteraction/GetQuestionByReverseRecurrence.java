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
import questionSortingAlgorithm.RecurrenceSorting;
import questionSortingAlgorithm.ReverseRecurrenceSorting;
import questionSortingAlgorithm.dataStructure.QuestionsDataRecurrence;

public class GetQuestionByReverseRecurrence extends GetQuestionAbstract implements Runnable{
    LocalDatabaseDao localDatabaseDao;
    LocalDatabase localDatabase;

    ArrayList<QuestionsDataRecurrence> questionsDataRecurrenceArrayList;

    public GetQuestionByReverseRecurrence(Context context, Exercise exerciseActivity, int[] courseIds) {
        super(context, exerciseActivity, courseIds);

        localDatabase = Room.databaseBuilder(context, LocalDatabase.class, "LocalDatabase")
                .fallbackToDestructiveMigration()
                .build();
        localDatabaseDao = localDatabase.localDatabaseDao();

        questionsDataRecurrenceArrayList = new ArrayList<>();
    }

    @Override
    public void run() {
        List<Question> rawQuestions;
        try{
            rawQuestions = localDatabaseDao.getAllQuestionByCourseIds(courseIds);
            if(rawQuestions.isEmpty()){
                this.updateQuestion();
                return;
            }
            for (Question q: rawQuestions){
                int occurrence = localDatabaseDao.getOccurrencesByQid(q.getQid());
                questionsDataRecurrenceArrayList.add(new QuestionsDataRecurrence(q, occurrence));
            }
            ReverseRecurrenceSorting reverseRecurrenceSorting = new ReverseRecurrenceSorting(questionsDataRecurrenceArrayList);
            questionsDataRecurrenceArrayList = reverseRecurrenceSorting.sortQuestions();
            this.updateQuestion();
        }
        catch(SQLiteException e){
            this.updateQuestionError("Error local database.");
        }
    }

    @Override
    void updateQuestion() {
        if (exerciseActivity instanceof Activity) {
            Activity mainActivity = (Activity)exerciseActivity;
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    exerciseActivity.updateQuestion(questionsDataRecurrenceArrayList);
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
