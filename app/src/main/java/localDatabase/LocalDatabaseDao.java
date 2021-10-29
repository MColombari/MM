package localDatabase;

import android.database.sqlite.SQLiteException;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import java.util.List;
import localDatabase.Tables.Course;
import localDatabase.Tables.Question;
import localDatabase.Tables.QuickResumeData;
import localDatabase.Tables.QuickResumeDataIds;
import localDatabase.Tables.StatisticUser;
import localDatabase.Tables.UserInformation;

/* DAO: Database Access Object. */
@Dao
public interface LocalDatabaseDao {
    /* Question Query. */
    @Transaction
    @Query("SELECT * FROM Question")
    List<Question> getAllQuestion() throws SQLiteException;
    @Transaction
    @Query("SELECT * FROM Question WHERE qid IN (:qIds)")
    List<Question> getAllQuestionByIds(int[] qIds) throws SQLiteException;
    @Transaction
    @Query("SELECT * FROM Question WHERE qid == :qIds")
    List<Question> getAllQuestionByIds(int qIds) throws SQLiteException;
    @Transaction
    @Query("SELECT * FROM Question Q WHERE Q.idCourse IN (:courseIds)")
    List<Question> getAllQuestionByCourseIds(int[] courseIds) throws SQLiteException;
    @Transaction
    @Query("SELECT * FROM Question Q LEFT JOIN Course C ON (Q.idCourse == C.id) WHERE C.name IN (:courseNames)")
    List<Question> getAllQuestionByCoursesName(String[] courseNames) throws SQLiteException;
    @Transaction
    @Insert
    void insertAllQuestion(Question... question) throws SQLiteException;
    @Transaction
    @Insert
    void insertAllQuestion(List<Question> questionList) throws SQLiteException;
    @Transaction
    @Query("DELETE FROM Question")
    void deleteAllQuestion();
    @Transaction
    @Delete
    void deleteQuestion(Question... question) throws SQLiteException;

    /* StatisticUser Query. */
    @Transaction
    @Query("SELECT * FROM StatisticUser")
    List<StatisticUser> getAllStatisticUser() throws SQLiteException;
    @Transaction
    @Query("SELECT * FROM StatisticUser S LEFT JOIN Question Q On (S.qidQuestion == Q.qid) WHERE idCourse == :idCourse")
    List<StatisticUser> getAllStatisticUserByIdCourse(int idCourse) throws SQLiteException;
    @Transaction
    @Query("SELECT COUNT(*) FROM StatisticUser WHERE qidQuestion == :qid")
    int getOccurrencesByQid(int qid) throws SQLiteException;
    @Transaction
    @Query("SELECT AVG(points) FROM StatisticUser WHERE qidQuestion == :qid")
    int getAveragePointsPerQuestion(int qid) throws SQLiteException;
    @Transaction
    @Insert
    void insertStatisticUser(StatisticUser statisticUsers) throws SQLiteException;
    @Transaction
    @Insert
    void insertAllStatisticUser(StatisticUser... statisticUsers) throws SQLiteException;

    /* Course Query. */
    @Transaction
    @Query("SELECT * FROM Course")
    List<Course> getAllCourse() throws SQLiteException;
    @Transaction
    @Query("SELECT * FROM Course WHERE id IN (:id)")
    List<Course> getCourseById(int[] id) throws SQLiteException;
    @Transaction
    @Query("SELECT * FROM Course WHERE id  == :id")
    List<Course> getCourseById(int id) throws SQLiteException;
    @Transaction
    @Insert
    void insertCourses(Course course) throws SQLiteException;

    /* UserInformation Query. */
    @Transaction
    @Query("SELECT * FROM UserInformation")
    List<UserInformation> getAllUserInformation() throws SQLiteException;
    @Transaction
    @Query("DELETE FROM UserInformation")
    void deleteAllUserInformation();
    @Transaction
    @Insert
    void insertUserInformation(UserInformation... userInformation) throws SQLiteException;

    /* QuickResumeData Query. */
    @Transaction
    @Insert
    void insertQuickResumeData(QuickResumeData quickResumeData);
    @Transaction
    @Query("SELECT * FROM QuickResumeData")
    List<QuickResumeData> getAllQuickResumeData();
    @Transaction
    @Query("DELETE FROM QuickResumeData")
    void deleteAllQuickResumeData();

    /* QuickResumeDataIds Query. */
    @Transaction
    @Insert
    void insertQuickResumeDataIds(QuickResumeDataIds quickResumeDataIds);
    @Transaction
    @Query("SELECT * FROM QuickResumeDataIds")
    List<QuickResumeDataIds> getAllQuickResumeDataIds();
    @Transaction
    @Query("DELETE FROM QuickResumeDataIds")
    void deleteAllQuickResumeDataIds();
}
