package com.olla.app.adapters;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.olla.app.R;
import com.olla.app.models.Vocabulary;
import java.util.List;

public class VocabularyAdapter extends RecyclerView.Adapter<VocabularyAdapter.ViewHolder> {
    private List<Vocabulary> vocabList;
    private TextToSpeech tts;

    public VocabularyAdapter(List<Vocabulary> vocabList, TextToSpeech tts) {
        this.vocabList = vocabList;
        this.tts = tts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vocabulary, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Vocabulary vocab = vocabList.get(position);
        holder.tvNativeWord.setText(vocab.getNativeWord());
        holder.tvTargetWord.setText(vocab.getTargetWord());

        holder.btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tts != null) {
                    tts.speak(vocab.getTargetWord(), TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return vocabList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNativeWord, tvTargetWord;
        ImageButton btnSpeak;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNativeWord = itemView.findViewById(R.id.tvNativeWord);
            tvTargetWord = itemView.findViewById(R.id.tvTargetWord);
            btnSpeak = itemView.findViewById(R.id.btnSpeak);
        }
    }
}

