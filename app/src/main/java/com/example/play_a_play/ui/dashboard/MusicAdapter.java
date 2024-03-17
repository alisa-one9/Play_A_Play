package com.example.play_a_play.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.play_a_play.R;
import com.example.play_a_play.databinding.MusicItemBinding;
import com.example.play_a_play.models.MusicFile;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {
    MusicItemBinding binding;
    Context mContext;
   ArrayList<MusicFile> musicFiles = new ArrayList<>();

    public MusicAdapter(Context mContext, ArrayList<MusicFile> musicFiles) {
        this.mContext = mContext;
        this.musicFiles = musicFiles;
    }

    @NonNull
    @Override
    public MusicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view =LayoutInflater.from(mContext).inflate(R.layout.music_item, parent, false );

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicAdapter.ViewHolder holder, int position) {
       holder.file_name.setText(musicFiles.get(position).getTitle());

       holder.file_image.setImageResource(R.drawable.ic_launcher_background);

    }

    @Override
    public int getItemCount() {
        return musicFiles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView file_name;
        ImageView file_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            file_name = itemView.findViewById(R.id.music_name);
            file_image =itemView.findViewById(R.id.music_image);


        }


    }
}
