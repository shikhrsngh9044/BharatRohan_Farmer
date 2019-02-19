package in.bharatrohan.bharatrohan.Activities.RoomDatabase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;
import java.util.Observable;

import in.bharatrohan.bharatrohan.Activities.MoneyFragments.MyRecords;

@Dao
public interface RecordsDao {
    @Insert
    void insert(Records records);

    @Query("SELECT * FROM records")
    LiveData<List<Records>> getAllRecords();

    @Query("SELECT SUM(amount) FROM records")
    int getExpenseSum();
}
