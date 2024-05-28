package huce.duriu.durifyandroid.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import huce.duriu.durifyandroid.Activity.MainActivity;
import huce.duriu.durifyandroid.File.FileDownloadTask;
import huce.duriu.durifyandroid.Model.Music;
import huce.duriu.durifyandroid.R;
import huce.duriu.durifyandroid.RecyclerView.AudioView;
import huce.duriu.durifyandroid.RecyclerView.MusicView;
import huce.duriu.durifyandroid.Service.AudioService;

public class AudioAdapter extends RecyclerView.Adapter<AudioView> {
    private List<Music> audios;
    public List<Music> getAudios() { return audios; }
    public void setAudios(List<Music> audios) { this.audios = audios; }
    public AudioAdapter(List<Music> audios) { this.audios = audios; }
    public void updateUI() { notifyDataSetChanged(); }
    private int selectedPosition = -1;

    @NonNull
    @Override
    public AudioView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_audio, parent, false);
        return new AudioView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AudioView holder, int position) {
        Music audio = audios.get(position);
        holder.getAudioName().setText(audios.get(position).getMusicName());

        if (position == selectedPosition) {
            holder.getPlayAudio().setVisibility(View.VISIBLE);
            holder.getDeleteAudio().setVisibility(View.VISIBLE);
        } else {
            holder.getPlayAudio().setVisibility(View.GONE);
            holder.getDeleteAudio().setVisibility(View.GONE);
        }

        holder.getPlayAudio().setOnClickListener(v -> {

        });

        holder.itemView.setOnClickListener(v -> {
            int previousPosition = selectedPosition;
            selectedPosition = holder.getAdapterPosition();
            if( selectedPosition == previousPosition) selectedPosition = -1;
            notifyItemChanged(previousPosition);
            notifyItemChanged(selectedPosition);
        });

        holder.getPlayAudio().setOnClickListener(v -> {
            MainActivity.bottomNavigationView.setSelectedItemId(R.id.playing);
        });

        holder.getDeleteAudio().setOnClickListener(v -> {
            String delAudioName = audio.getMusicName();
            String delAudioPath = AudioService.path + delAudioName;
            File fileToDelete = new File(delAudioPath);
            //if (fileToDelete.exists()) {
                if (fileToDelete.delete()) {
                    Toast.makeText(v.getContext(), "Deleted " + delAudioName + " successfully", Toast.LENGTH_SHORT).show();
                    audios.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, audios.size());
                } else {
                    Toast.makeText(v.getContext(), "Failed to delete file", Toast.LENGTH_SHORT).show();
                }
            //} else {
                //Toast.makeText(v.getContext(), "File not found", Toast.LENGTH_SHORT).show();
            //}
        });
    }

    @Override
    public int getItemCount() { return audios.size(); }

    public void updateList(List<Music> audios) {
        this.audios = audios;
        notifyDataSetChanged();
    }
}