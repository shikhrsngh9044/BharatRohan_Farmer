package in.bharatrohan.bharatrohan.Activities.MoneyFragments;


import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.UUID;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import in.bharatrohan.bharatrohan.Activities.RoomDatabase.RecordViewModel;
import in.bharatrohan.bharatrohan.Activities.RoomDatabase.Records;
import in.bharatrohan.bharatrohan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddRecord extends Fragment {

    private EditText dateEditText, amtEditText, purEditText;
    private ToggleSwitch toggleSwitch;
    private Button btnDate, btnSave;
    private int mYear, mMonth, mDay, mDayOfWeek;
    private int Year, Month, Day, DayOfWeek;
    private String recordType = "expense";
    private RecordViewModel recordViewModel;

    public AddRecord() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_add_record, container, false);

        init(view1);


        return view1;
    }

    @Override
    public void onViewCreated(@NonNull View view1, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view1, savedInstanceState);

        btnDate.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            mDayOfWeek = c.get(Calendar.DAY_OF_WEEK);


            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), (view, year, month, dayOfMonth) -> {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                dateEditText.setText(date);
                Year = year;
                Month = month;
                Day = dayOfMonth;
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        toggleSwitch.setOnToggleSwitchChangeListener((position, isChecked) -> {
            if (position == 0) {
                recordType = "expense";
            } else if (position == 1) {
                recordType = "earned";
            }
        });

        btnSave.setOnClickListener(v -> {
            String amount = amtEditText.getText().toString().trim();
            String purpose = purEditText.getText().toString().trim();
            String date = dateEditText.getText().toString().trim();
            saveRecord(amount, purpose, date);
        });


    }

    private void init(View v) {
        dateEditText = v.findViewById(R.id.dateEdit);
        amtEditText = v.findViewById(R.id.amount);
        purEditText = v.findViewById(R.id.purpose);
        btnDate = v.findViewById(R.id.btnDate);
        btnSave = v.findViewById(R.id.save);
        toggleSwitch = v.findViewById(R.id.toggleSwitch);

        recordViewModel = ViewModelProviders.of(this).get(RecordViewModel.class);
    }

    private boolean validateForm() {

        String pupose = purEditText.getText().toString().trim();
        String amount = amtEditText.getText().toString().trim();
        String date = dateEditText.getText().toString().trim();


        if (amount.isEmpty()) {
            amtEditText.setError("Amount is required");
            amtEditText.requestFocus();
            return true;
        }


        if (date.isEmpty()) {
            dateEditText.setError("Date is required");
            dateEditText.requestFocus();
            return true;
        }

        if (pupose.isEmpty()) {
            purEditText.setError("Date is required");
            purEditText.requestFocus();
            return true;
        }

        return false;
    }

    private void saveRecord(String amount, String purpose, String date) {


        final String recordId = UUID.randomUUID().toString();

        validateForm();


        Records records = new Records(recordId, DayofWeek(Month + 1), Day, DayofWeek(Month + 1), mYear, recordType, Integer.valueOf(amount), purpose);
        recordViewModel.insert(records);
        Toast.makeText(getActivity(), "Record Added!", Toast.LENGTH_SHORT).show();


    }

    private String DayofWeek(int day) {

        if (day == 1) {
            return "Jan";
        } else if (day == 2) {
            return "Feb";
        } else if (day == 3) {
            return "Mar";
        } else if (day == 4) {
            return "Apr";
        } else if (day == 5) {
            return "May";
        } else if (day == 6) {
            return "Jun";
        } else if (day == 7) {
            return "Jul";
        } else if (day == 8) {
            return "Aug";
        } else if (day == 9) {
            return "Sep";
        } else if (day == 10) {
            return "Oct";
        } else if (day == 11) {
            return "Nov";
        } else if (day == 12) {
            return "Dec";
        }

        return "Monday";
    }


}
