package in.bharatrohan.bharatrohan;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import in.bharatrohan.bharatrohan.Models.CropProblem;

public class SolutionRecyclerAdapter extends RecyclerView.Adapter<SolutionRecyclerAdapter.SolutionViewHolder> {

    private Context mCtx;
    private final LayoutInflater layoutInflater;
    private List<CropProblem.Data.Solution.SolutionData> dataList;

    public SolutionRecyclerAdapter(Context context, List<CropProblem.Data.Solution.SolutionData> dataList) {
        mCtx = context;
        layoutInflater = LayoutInflater.from(context);
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public SolutionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.soloution_row, parent, false);
        SolutionViewHolder solutionViewHolder = new SolutionViewHolder(itemView);
        return solutionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SolutionViewHolder holder, int position) {

        holder.sol.setText(dataList.get(position).getSolText());
        holder.solNo.setText(String.valueOf(position + 1));
        holder.itemView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(dataList.get(position).getSolColor())));

        if (dataList.get(position).get_status()) {
            holder.tick.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class SolutionViewHolder extends RecyclerView.ViewHolder {

        TextView solNo, sol;
        ImageView tick;

        public SolutionViewHolder(View itemView) {
            super(itemView);

            solNo = itemView.findViewById(R.id.solution_no);
            sol = itemView.findViewById(R.id.tv_solution);

            tick = itemView.findViewById(R.id.imgTick);
        }

    }
}
