package in.bharatrohan.bharatrohan.Activities.RoomDatabase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface RecordsDao {
    @Insert
    void insert(Records records);

    @Query("SELECT * FROM records ORDER BY month")
    LiveData<List<Records>> getAllRecords();

    @Query("SELECT SUM(amount) FROM records")
    int getExpenseSum();
}
