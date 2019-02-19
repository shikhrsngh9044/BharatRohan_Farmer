package in.bharatrohan.bharatrohan.Activities.RoomDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = Records.class, version = 1)
public abstract class RecordRoomDatabase extends RoomDatabase {
    public abstract RecordsDao monthDao();

    private static volatile RecordRoomDatabase monthRoomInstance;

    static RecordRoomDatabase getDatabases(final Context context) {

        if (monthRoomInstance == null) {
            synchronized (RecordRoomDatabase.class) {
                if (monthRoomInstance == null) {
                    monthRoomInstance = Room.databaseBuilder(context.getApplicationContext(),
                            RecordRoomDatabase.class, "records_database")
                            .build();
                }
            }
        }


        return monthRoomInstance;
    }
}
