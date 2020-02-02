package com.example.splash;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class PubInformation extends AppCompatActivity {
    private Button pubCrawlbutton;
    private Button pubListsButton;
    private TextView textViewAdvert;
    ListView list;




    @RequiresApi(api = Build.VERSION_CODES.N)
    public void goPip(View view) {
        enterPictureInPictureMode();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_information);

        //Crawl button
        pubCrawlbutton = findViewById(R.id.crawlButton);
        pubCrawlbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCrawl();

            }
        });

        //List button
        pubListsButton = findViewById((R.id.infoButton));
        pubListsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPub();
            }
        });


        textViewAdvert = findViewById(R.id.textViewAdvert);

        //Create video advert with controller
        VideoView videoView = findViewById(R.id.videoView);
        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.advert);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
    }
    //Crawl button to direct user to MainActivity
    public void openCrawl(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //Crawl button to direct user to PubList
    public void openPub(){
        Intent intent = new Intent(this, PubList.class);
        startActivity(intent);
    }
}
