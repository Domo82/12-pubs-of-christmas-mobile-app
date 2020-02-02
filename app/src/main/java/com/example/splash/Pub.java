package com.example.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

public class Pub extends AppCompatActivity {
    private TextToSpeech mTTS;
    private SeekBar mSeekbarSpeed;
    private SeekBar mSeekbarPitch;
    private Button mButtonSpeak;
    private TextView mTextInfo;



//<-----------------------------This Activity is not in use ----------------------------------->
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub);

    }
}

