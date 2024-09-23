package com.example.camera;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.VideoView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class ideoActivity extends AppCompatActivity {

    VideoView video_view;
    Button button_camera, button_gallery;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        video_view = findViewById(R.id.video_view);
        button_camera = findViewById(R.id.button_camera);
        button_gallery = findViewById(R.id.button_gallery);

        button_camera.setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            videoResultLauncher.launch(intent);
        });

        button_gallery.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
            galleryVideoResultLauncher.launch(intent);
        });
    }

    ActivityResultLauncher<Intent> videoResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri videoUri = result.getData().getData();
                        playVideo(videoUri);
                    }
                }
            }
    );

    ActivityResultLauncher<Intent> galleryVideoResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri videoUri = result.getData().getData();
                        playVideo(videoUri);
                    }
                }
            }
    );

    private void playVideo(Uri videoUri) {
        video_view.setVideoURI(videoUri);
        video_view.setOnPreparedListener(mp -> video_view.start());
        video_view.setOnCompletionListener(mp -> {
            // Handle completion if needed
            mp.reset();
        });
    }
}
