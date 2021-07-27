package localDatabase;

import android.database.SQLException;
import android.database.sqlite.SQLiteException;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import localDatabase.Tables.Course;
import localDatabase.Tables.Question;
import localDatabase.Tables.StatisticUser;
import localDatabase.Tables.UserInformation;

@Dao
public interface LocalDatabaseDao {
    /* Question Query */
    @Query("SELECT * FROM Question")
    List<Question> getAllQuestion() throws SQLiteException;
    @Query("SELECT * FROM Question WHERE qid IN (:qIds)")
    List<Question> loadAllQuestionByIds(int[] qIds) throws SQLiteException;
    @Query("SELECT * FROM Question WHERE questionText IN (:values)")
    List<Question> loadAllQuestionByValue(int[] values) throws SQLiteException;
    @Insert
    void insertAllQuestion(Question... question) throws SQLiteException;
    @Query("DELETE FROM Question")
    void deleteAllQuestion();
    @Delete
    void deleteQuestion(Question... question) throws SQLiteException;

    /* StatisticUser Query */
    @Query("SELECT * FROM StatisticUser")
    List<StatisticUser> getAllStatisticUser() throws SQLiteException;
    @Query("SELECT * FROM StatisticUser WHERE idCourse == :idCourse")
    List<StatisticUser> getAllStatisticUserByIdCourse(int idCourse) throws SQLiteException;

    /* Course Query */
    @Query("SELECT * FROM Course")
    List<Course> getAllCourse() throws SQLiteException;

    /* User Information Query */
    @Query("SELECT * FROM UserInformation")
    List<UserInformation> getAllUserInformation() throws SQLiteException;
    @Query("DELETE FROM UserInformation")
    void deleteAllUserInformation();
    @Insert
    void insertUserInformation(UserInformation... userInformations) throws SQLiteException;
}
