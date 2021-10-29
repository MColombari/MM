package localDatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import localDatabase.Tables.Course;
import localDatabase.Tables.Question;
import localDatabase.Tables.QuickResumeData;
import localDatabase.Tables.QuickResumeDataIds;
import localDatabase.Tables.StatisticUser;
import localDatabase.Tables.UserInformation;

/* Room serialize async transaction queries, so Room will not use more than one thread for executing
* database transactions.
* https://developer.android.com/jetpack/androidx/releases/room#2.1.0-alpha06
*
* Other source:
* https://stackoverflow.com/questions/47525082/can-i-use-the-same-roomdatabase-object-from-multiple-threads-on-android*/

@Database(entities = {Question.class, StatisticUser.class, Course.class, UserInformation.class, QuickResumeData.class, QuickResumeDataIds.class}, version = 4, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract LocalDatabaseDao localDatabaseDao();
}