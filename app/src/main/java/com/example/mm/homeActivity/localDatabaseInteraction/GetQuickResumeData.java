package com.example.mm.homeActivity.localDatabaseInteraction;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import androidx.room.Room;
import com.example.mm.homeActivity.homeFragment.HomeFragment;
import java.util.ArrayList;
import java.util.List;
import utils.GetAlgorithmsName;
import localDatabase.LocalDatabase;
import localDatabase.LocalDatabaseDao;
import localDatabase.Tables.Course;
import localDatabase.Tables.QuickResumeData;
import localDatabase.Tables.QuickResumeDataIds;

public class GetQuickResumeData implements Runnable {
    Context context;
    LocalDatabase localDatabase;
    LocalDatabaseDao localDatabaseDao;

    HomeFragment homeFragment;
    ArrayList<Integer> coursesIdsArrayList;
    int numQuestion;
    int sortingAlgorithm;

    public GetQuickResumeData(Context context, Context contextDatabase, HomeFragment homeFragment) {
        this.context = context;
        this.homeFragment = homeFragment;

        coursesIdsArrayList = new ArrayList<>();

        localDatabase = Room.databaseBuilder(contextDatabase, LocalDatabase.class, "LocalDatabase")
                .fallbackToDestructiveMigration()  /* Is needed to overwrite the old scheme of the
         *  local database, it will ERASE all the current
         *  data.
         * */
                .build();
        localDatabaseDao = localDatabase.localDatabaseDao();
    }

    @Override
    public void run() {
        try{
            List<QuickResumeData> quickResumeDataList = localDatabaseDao.getAllQuickResumeData();
            if(quickResumeDataList.isEmpty()){
                SpannableString spannableString=  new SpannableString("Quick Resume not available\n until al least one exercise is done");
                spannableString.setSpan(new RelativeSizeSpan(2f), 0,26, 0); /* Set size of the first 26 character. */

                setQuickResume(false, spannableString);
                return;
            }
            numQuestion = quickResumeDataList.get(0).getNumberOfQuestion();
            sortingAlgorithm = quickResumeDataList.get(0).getSortOption();

            List<QuickResumeDataIds> quickResumeDataIdsList = localDatabaseDao.getAllQuickResumeDataIds();
            if(quickResumeDataIdsList.isEmpty()){
                throw new SQLiteException();
            }
            for(QuickResumeDataIds i: quickResumeDataIdsList){
                coursesIdsArrayList.add(i.getCourseId());
            }

            int[] coursesIdsArray = new int[coursesIdsArrayList.size()];

            for(int i = 0; i < coursesIdsArrayList.size(); i++){
                coursesIdsArray[i] = coursesIdsArrayList.get(i);
            }

            List<Course> courseList = localDatabaseDao.getCourseById(coursesIdsArray);

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("Quick Resume\nSettings:\n\t - Number of question = ");
            stringBuilder.append(numQuestion);
            stringBuilder.append("\n\t - Sorting Algorithm = \"");
            stringBuilder.append(GetAlgorithmsName.get(sortingAlgorithm));
            stringBuilder.append("\"\n\t - Courses = ");

            boolean flag = true;
            for(Course c: courseList){
                if(flag){
                    flag = false;
                }
                else{
                    stringBuilder.append(", ");
                }
                stringBuilder.append(c.getName());
            }

            SpannableString spannableString=  new SpannableString(stringBuilder.toString());
            spannableString.setSpan(new RelativeSizeSpan(2f), 0,12, 0);

            setQuickResume(true, spannableString);
        }
        catch(SQLiteException e){
            SpannableString spannableString=  new SpannableString("Error\n loading data.");
            spannableString.setSpan(new RelativeSizeSpan(2f), 0,5, 0); // set size.
            spannableString.setSpan(new ForegroundColorSpan(Color.RED), 0, 5, 0);// set color of the first 5 character.

            setQuickResume(false, spannableString);
        }
    }

    void setQuickResume(boolean setClickListener, SpannableString spannableString){
        if (context instanceof Activity) {
            Activity mainActivity = (Activity)context;
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    homeFragment.setQuickResume(setClickListener, spannableString, numQuestion, sortingAlgorithm, coursesIdsArrayList);
                }
            });
        }
    }
}
