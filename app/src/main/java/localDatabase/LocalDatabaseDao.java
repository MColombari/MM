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
    List<Question> getAllQuestionByIds(int[] qIds) throws SQLiteException;
    @Query("SELECT * FROM Question Q WHERE Q.idCourse IN (:courseIds)")
    List<Question> getAllQuestionByCourseIds(int[] courseIds) throws SQLiteException;
    @Query("SELECT * FROM Question Q LEFT JOIN Course C ON (Q.idCourse == C.id) WHERE C.name IN (:courseNames)")
    List<Question> getAllQuestionByCoursesName(String[] courseNames) throws SQLiteException;
    @Insert
    void insertAllQuestion(Question... question) throws SQLiteException;
    @Query("DELETE FROM Question")
    void deleteAllQuestion();
    @Delete
    void deleteQuestion(Question... question) throws SQLiteException;

    /* StatisticUser Query */
    @Query("SELECT * FROM StatisticUser")
    List<StatisticUser> getAllStatisticUser() throws SQLiteException;
    @Query("SELECT * FROM StatisticUser S LEFT JOIN Question Q On (S.qidQuestion == Q.qid) WHERE idCourse == :idCourse")
    List<StatisticUser> getAllStatisticUserByIdCourse(int idCourse) throws SQLiteException;
    @Query("SELECT COUNT(*) FROM StatisticUser WHERE qidQuestion == :qid")
    int getOccurrencesByQid(int qid) throws SQLiteException;
    @Insert
    void insertStatisticUser(StatisticUser statisticUsers) throws SQLiteException;
    @Insert
    void insertAllStatisticUser(StatisticUser... statisticUsers) throws SQLiteException;

    /* Course Query */
    @Query("SELECT * FROM Course")
    List<Course> getAllCourse() throws SQLiteException;

    /* User Information Query */
    @Query("SELECT * FROM UserInformation")
    List<UserInformation> getAllUserInformation() throws SQLiteException;
    @Query("DELETE FROM UserInformation")
    void deleteAllUserInformation();
    @Insert
    void insertUserInformation(UserInformation... userInformation) throws SQLiteException;
}
