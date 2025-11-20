package com.olla.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.olla.app.R;
import com.olla.app.models.Progress;
import java.util.List;

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ViewHolder> {
    private List<Progress> progressList;

    public ProgressAdapter(List<Progress> progressList) {
        this.progressList = progressList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_progress, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Progress progress = progressList.get(position);
        holder.tvQuizName.setText(progress.getQuizName());
        holder.tvScore.setText(progress.getScore() + " / " + progress.getTotalQuestions());
        holder.tvDate.setText(progress.getDate());
        
        double percentage = (double) progress.getScore() / progress.getTotalQuestions() * 100;
        holder.tvPercentage.setText(String.format("%.1f%%", percentage));
    }

    @Override
    public int getItemCount() {
        return progressList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvQuizName, tvScore, tvDate, tvPercentage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            tvQuizName = itemView.findViewById(R.id.tvQuizName);
            tvScore = itemView.findViewById(R.id.tvScore);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvPercentage = itemView.findViewById(R.id.tvPercentage);
        }
    }
}

