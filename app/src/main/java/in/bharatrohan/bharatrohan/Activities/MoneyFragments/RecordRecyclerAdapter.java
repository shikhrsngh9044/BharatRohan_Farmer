package in.bharatrohan.bharatrohan.Activities.MoneyFragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import in.bharatrohan.bharatrohan.Activities.RoomDatabase.Records;
import in.bharatrohan.bharatrohan.R;

public class RecordRecyclerAdapter extends RecyclerView.Adapter<RecordRecyclerAdapter.RecordViewHolder> {

    private Context mCtx;
    private final LayoutInflater layoutInflater;
    private List<Records> mList;

    public RecordRecyclerAdapter(Context context) {
        mCtx = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.money_record_list, parent, false);
        RecordViewHolder recordViewHolder = new RecordViewHolder(itemView);
        return recordViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        if (mList != null) {
            Records records = mList.get(position);
            holder.setData(records.getDay(), records.getDayOfWeek(), records.getPupose(), records.getRecordType(), records.getAmount(), position);
        }
    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        } else {
            return 0;
        }

    }

    public void setRecords(List<Records> records) {
        mList = records;
        notifyDataSetChanged();
    }

    public class RecordViewHolder extends RecyclerView.ViewHolder {

        private TextView tvDay, tvDayofWeek, tvPurpose, tvRecordType, tvAmount;
        private int mPosition;

        public RecordViewHolder(View itemView) {
            super(itemView);

            tvDay = itemView.findViewById(R.id.tvDay);
            tvDayofWeek = itemView.findViewById(R.id.tvDayOfWeek);
            tvPurpose = itemView.findViewById(R.id.tvPupose);
            tvRecordType = itemView.findViewById(R.id.tvRecType);
            tvAmount = itemView.findViewById(R.id.tvAmt);
        }

        public void setData(int day, String dayOfWeek, String pupose, String recType, int amt, int pos) {
            tvDay.setText(String.valueOf(day));
            tvDayofWeek.setText(dayOfWeek);
            tvPurpose.setText(pupose);
            tvRecordType.setText(recType);
            tvAmount.setText(String.valueOf(amt));
            mPosition = pos;
        }
    }
}
