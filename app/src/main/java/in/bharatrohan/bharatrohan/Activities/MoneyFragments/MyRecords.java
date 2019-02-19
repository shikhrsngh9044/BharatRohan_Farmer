package in.bharatrohan.bharatrohan.Activities.MoneyFragments;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import in.bharatrohan.bharatrohan.Activities.RoomDatabase.RecordViewModel;
import in.bharatrohan.bharatrohan.Activities.RoomDatabase.Records;
import in.bharatrohan.bharatrohan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyRecords extends Fragment {

    //private MaterialSpinner monthSpinner;
    private String month_name;
    public String smonth_name;
    private RecyclerView recyclerView;
    private RecordRecyclerAdapter recyclerAdapter;
    private TextView Exp;
    private RecordViewModel recordViewModel;

    public MyRecords() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_records, container, false);

        initViews(view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        /*ArrayList<String> monthList = new ArrayList<String>();
        monthList.add("-SELECT MONTH-");
        monthList.add("January");
        monthList.add("February");
        monthList.add("March");
        monthList.add("April");
        monthList.add("May");
        monthList.add("June");
        monthList.add("July");
        monthList.add("August");
        monthList.add("September");
        monthList.add("October");
        monthList.add("November");
        monthList.add("December");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, monthList);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(adapter1);

        monthSpinner.setOnItemSelectedListener((view1, position, id, item) -> {
            month_name = monthList.get(position);
            smonth_name = month_name.substring(0, 3);
            //Toast.makeText(getActivity(), "Month Selected : " + smonth_name, Toast.LENGTH_SHORT).show();

        });*/
        recordViewModel = ViewModelProviders.of(this).get(RecordViewModel.class);
        recordViewModel.getAllRecords().observe(this, records -> recyclerAdapter.setRecords(records));

        //Exp.setText(recordViewModel.getExpSum());


    }

    private void initViews(View v) {
        //monthSpinner = v.findViewById(R.id.monthSpinner);
        //Exp = v.findViewById(R.id.tvExp);
        recyclerView = v.findViewById(R.id.recordRecycler);
        recyclerAdapter = new RecordRecyclerAdapter(getActivity());
    }
}
