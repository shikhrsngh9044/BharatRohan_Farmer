package in.bharatrohan.bharatrohan.Activities.RoomDatabase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "records")
public class Records {

    @PrimaryKey
    @NonNull
    private String id;

    @NonNull
    private String month;

    @NonNull
    private int day;

    @NonNull
    private String dayOfWeek;

    @NonNull
    private int year;

    @NonNull
    private String recordType;

    @NonNull
    private int amount;

    @NonNull
    private String pupose;

    public Records(@NonNull String id, @NonNull String month, @NonNull int day, @NonNull String dayOfWeek, @NonNull int year, @NonNull String recordType, @NonNull int amount, @NonNull String pupose) {
        this.id = id;
        this.month = month;
        this.day = day;
        this.dayOfWeek = dayOfWeek;
        this.year = year;
        this.recordType = recordType;
        this.amount = amount;
        this.pupose = pupose;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getMonth() {
        return month;
    }

    @NonNull
    public int getDay() {
        return day;
    }

    @NonNull
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    @NonNull
    public int getYear() {
        return year;
    }

    @NonNull
    public String getRecordType() {
        return recordType;
    }

    @NonNull
    public int getAmount() {
        return amount;
    }

    @NonNull
    public String getPupose() {
        return pupose;
    }
}
