package id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.entity.WaterConsumption;
import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.R;

public class WaterConsumptionAdapter extends RecyclerView.Adapter<WaterConsumptionAdapter.WaterConsumptionViewHolder> {

    public final LayoutInflater mInflater;
    public List<WaterConsumption> mLog = new ArrayList<>(); // Cached copy of Log
    private OnItemClickListener listener;

    public WaterConsumptionAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @NonNull
    @Override
    public WaterConsumptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.log_item, parent, false);
        return new WaterConsumptionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WaterConsumptionViewHolder holder, int position) {
            WaterConsumption current = mLog.get(position);
            holder.textViewAmount.setText(String.valueOf(current.getWater_drunk()));
            holder.textViewDate.setText(current.getDate());
            holder.textViewTime.setText(current.getTime());
    }

    public void setLogs(List<WaterConsumption> logs){
        this.mLog = logs;
        notifyDataSetChanged();
    }

    public WaterConsumption getLogAt(int position) {
        return mLog.get(position);
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mLog != null)
            return mLog.size();
        else return 0;
    }

    class WaterConsumptionViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewAmount;
        private TextView textViewDate;
        private TextView textViewTime;
        private TextView textViewProgress;

        public WaterConsumptionViewHolder(View itemView) {
            super(itemView);
            textViewAmount = itemView.findViewById(R.id.water_drunk);
            textViewDate = itemView.findViewById(R.id.date);
            textViewTime = itemView.findViewById(R.id.time);
            textViewProgress = itemView.findViewById(R.id.progress_textView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(mLog.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(WaterConsumption waterConsumption);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}