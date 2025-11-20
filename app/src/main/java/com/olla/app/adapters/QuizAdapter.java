package com.olla.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.olla.app.R;
import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.ViewHolder> {
    private List<String> quizNames;
    private OnQuizSelectedListener listener;

    public interface OnQuizSelectedListener {
        void onQuizSelected(String quizName);
    }

    public QuizAdapter(List<String> quizNames, OnQuizSelectedListener listener) {
        this.quizNames = quizNames;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_quiz, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String quizName = quizNames.get(position);
        holder.tvQuizName.setText(quizName);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onQuizSelected(quizName);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return quizNames.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvQuizName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            tvQuizName = itemView.findViewById(R.id.tvQuizName);
        }
    }
}

