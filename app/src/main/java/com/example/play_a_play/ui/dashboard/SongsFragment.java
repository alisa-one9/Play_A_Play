package com.example.play_a_play.ui.dashboard;



import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.play_a_play.MainActivity;
import com.example.play_a_play.databinding.FragmentSongsBinding;
import com.example.play_a_play.models.MusicFile;

import java.util.ArrayList;

public class SongsFragment extends Fragment {

    private FragmentSongsBinding binding;
    public static final int REQUEST_CODE = 1;
    public static ArrayList<MusicFile> musicFiles =new ArrayList<>();
    RecyclerView recyclerView;
    MusicAdapter musicAdapter;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentSongsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        permission();

        if(!(musicFiles.size()<1)) {

            musicAdapter = new MusicAdapter(getContext(), musicFiles);
            binding.recyclerView.setAdapter(musicAdapter);
        }
        return root;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    public  static ArrayList<MusicFile>getAllAudio(Context context) {

        ArrayList<MusicFile> tempAudioList = new ArrayList<>();

        Uri uri = MediaStore
                .Audio
                .Media
                .EXTERNAL_CONTENT_URI;
        String [] projection ={
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA, // for PATH
                MediaStore.Audio.Media.ARTIST,
        };
        Cursor cursor =  context.getApplicationContext().getContentResolver().query(uri, projection,
                null,null, null);
        if(cursor!=null){
            while (cursor.moveToNext()){
                String album = cursor.getString(0);
                String title = cursor.getString(1);
                String duration = cursor.getString(2);
                String path = cursor.getString(3);
                String artist = cursor.getString(4);

                MusicFile musicFile = new MusicFile(path,title);
                Log.e("Path: "+path, "Album: "+ album);
                tempAudioList.add(musicFile);
            }
            cursor.close();

        }
        return tempAudioList;
    }


    private void permission() {
        if (ContextCompat.checkSelfPermission(requireActivity().getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
        }else {
            Toast.makeText(requireActivity(), "Permission Granted!", Toast.LENGTH_SHORT).show();
            musicFiles = getAllAudio(requireContext());

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireActivity(), "Permission Granted!", Toast.LENGTH_SHORT).show();
                musicFiles = getAllAudio(requireContext());

            } else {
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}