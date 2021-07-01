package localDatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import localDatabase.Tables.Course;
import localDatabase.Tables.Question;
import localDatabase.Tables.StatisticUser;

/* Room serialize async transaction queries, so Room will not use more than one thread for executing
* database transactions.
* https://developer.android.com/jetpack/androidx/releases/room#2.1.0-alpha06 */

@Database(entities = {Question.class, StatisticUser.class, Course.class}, version = 1, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract LocalDatabaseDao localDatabaseDao();
}