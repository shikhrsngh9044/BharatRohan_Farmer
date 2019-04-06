package in.bharatrohan.bharatrohan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import in.bharatrohan.bharatrohan.Activities.FarmDetails;
import in.bharatrohan.bharatrohan.Models.Farmer;

public class LandRecyclerAdapter extends RecyclerView.Adapter<LandRecyclerAdapter.LandViewHolder> {

    private Context mCtx;
    private final LayoutInflater layoutInflater;
    private List<Farmer.FarmerFarm> dataList;

    public LandRecyclerAdapter(Context context, List<Farmer.FarmerFarm> dataList) {
        mCtx = context;
        layoutInflater = LayoutInflater.from(context);
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public LandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.land_row, parent, false);
        LandViewHolder solutionViewHolder = new LandViewHolder(itemView);
        return solutionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LandViewHolder holder, int position) {

        holder.landNo.setText("Land " + (position + 1) + " :");
        holder.landName.setText(dataList.get(position).getFarm_name());
        holder.cropName.setText(dataList.get(position).getCrop().getCrop_name());

        if (dataList.get(position).getVerified()) {
            holder.landStatus.setText("Verified");
            holder.landStatus.setBackgroundResource(android.R.color.holo_green_dark);
        } else {
            holder.landStatus.setText("Not Verified");
            holder.landStatus.setBackgroundResource(android.R.color.holo_red_dark);
        }

        holder.itemView.setOnClickListener(v -> {
            //Toast.makeText(mCtx, "Land " + (position + 1), Toast.LENGTH_SHORT).show();
            new PrefManager(mCtx).saveFarmId(dataList.get(position).getFarmid());
            mCtx.startActivity(new Intent(mCtx, FarmDetails.class));
            ((Activity) mCtx).finish();
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class LandViewHolder extends RecyclerView.ViewHolder {

        TextView landNo, landName, cropName, landStatus;

        public LandViewHolder(View itemView) {
            super(itemView);

            landNo = itemView.findViewById(R.id.tvLandNo);
            landName = itemView.findViewById(R.id.tvLandName);
            landStatus = itemView.findViewById(R.id.tvLandStatus);
            cropName = itemView.findViewById(R.id.tvCropName);
        }

    }
}
