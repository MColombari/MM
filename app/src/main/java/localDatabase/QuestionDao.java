package localDatabase;

import android.database.sqlite.SQLiteException;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.EmptyResultSetException;
import androidx.room.Insert;
import androidx.room.Query;

import java.sql.SQLException;
import java.util.List;

@Dao
public interface QuestionDao {
    @Query("SELECT * FROM question")
    List<Question> getAll() throws SQLiteException;

    @Query("SELECT * FROM question WHERE qid IN (:qIds)")
    List<Question> loadAllByIds(int[] qIds) throws EmptyResultSetException, SQLiteException;

    @Query("SELECT * FROM question WHERE questionText IN (:values)")
    List<Question> loadAllByValue(int[] values) throws EmptyResultSetException, SQLiteException;

    @Insert
    void insertAll(Question... question) throws SQLiteException;

    @Query("DELETE FROM question")
    void deleteAll();

    @Delete
    void delete(Question... question) throws SQLiteException;


}
