package in.bharatrohan.bharatrohan.Activities.RoomDatabase;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;


public class RecordViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private RecordsDao recordsDao;
    private RecordRoomDatabase recordDB;
    private LiveData<List<Records>> mAllRecords;
    private int expSum;

    public RecordViewModel(@NonNull Application application) {
        super(application);

        recordDB = RecordRoomDatabase.getDatabases(application);
        recordsDao = recordDB.monthDao();
        mAllRecords = recordsDao.getAllRecords();
//        expSum = recordsDao.getExpenseSum();
    }

    public void insert(Records records) {
        new InsertAsyncTask(recordsDao).execute(records);
    }

    public LiveData<List<Records>> getAllRecords() {
        return mAllRecords;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "ViewModel Destroyed");
    }

    private class InsertAsyncTask extends AsyncTask<Records, Void, Void> {

        RecordsDao mRecordsDao;


        public InsertAsyncTask(RecordsDao mRecordsDao) {
            this.mRecordsDao = mRecordsDao;
        }

        @Override
        protected Void doInBackground(Records... records) {
            mRecordsDao.insert(records[0]);
            return null;
        }
    }
}
