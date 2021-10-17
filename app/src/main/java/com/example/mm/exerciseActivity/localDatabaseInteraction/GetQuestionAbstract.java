package com.example.mm.exerciseActivity.localDatabaseInteraction;

import android.content.Context;

import com.example.mm.exerciseActivity.Exercise;

public abstract class GetQuestionAbstract implements Runnable {
    Context context;
    Exercise exerciseActivity;
    int[] courseIds;

    public GetQuestionAbstract(Context context, Exercise exerciseActivity, int[] courseIds) {
        this.context = context;
        this.exerciseActivity = exerciseActivity;
        this.courseIds = courseIds;
    }

    abstract void updateQuestion();
    abstract void updateQuestionError(String errorString);
}
