package com.olla.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.olla.app.R;
import com.olla.app.models.Profile;
import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {
    private List<Profile> profileList;
    private OnProfileClickListener listener;

    public interface OnProfileClickListener {
        void onProfileClick(Profile profile);
    }

    public ProfileAdapter(List<Profile> profileList, OnProfileClickListener listener) {
        this.profileList = profileList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_profile, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Profile profile = profileList.get(position);
        holder.tvUsername.setText(profile.getUsername());
        holder.tvLanguages.setText(profile.getNativeLanguage() + " → " + profile.getTargetLanguage());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onProfileClick(profile);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvUsername, tvLanguages;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvLanguages = itemView.findViewById(R.id.tvLanguages);
        }
    }
}

