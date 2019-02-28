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
    private int month;


    @NonNull
    private String monthName;

    @NonNull
    private int day;

    @NonNull
    private int year;

    @NonNull
    private String recordType;

    @NonNull
    private int amount;

    @NonNull
    private String purpose;

    public Records(@NonNull String id, @NonNull int month, @NonNull String monthName, @NonNull int day, @NonNull int year, @NonNull String recordType, @NonNull int amount, @NonNull String purpose) {
        this.id = id;
        this.month = month;
        this.monthName = monthName;
        this.day = day;
        this.year = year;
        this.recordType = recordType;
        this.amount = amount;
        this.purpose = purpose;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public int getMonth() {
        return month;
    }

    @NonNull
    public int getDay() {
        return day;
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
    public String getMonthName() {
        return monthName;
    }

    @NonNull
    public String getPurpose() {
        return purpose;
    }
}
