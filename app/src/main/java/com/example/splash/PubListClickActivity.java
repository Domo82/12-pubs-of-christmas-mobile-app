package com.example.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.awt.font.TextAttribute;
import java.util.Locale;

public class PubListClickActivity extends AppCompatActivity {

    private TextToSpeech TTS;
    private SeekBar SeekBarPitch;
    private SeekBar SeekBarSpeed;
    private Button buttonSpeak;
    TextView textViewName;
    TextView textViewDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_list_click);

        textViewName = findViewById(R.id.textItem);
        textViewDescription = findViewById(R.id.textItem2);
        buttonSpeak = findViewById(R.id.button_speak);

        String TempHolder = getIntent().getStringExtra("ListViewTitleClickValue");
        textViewName.setText(TempHolder);

        String TempHolder3 = getIntent().getStringExtra("ListViewInformationValue");
        textViewDescription.setText(TempHolder3);

        TTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                //If TTS is successful, set language to U.S English
                if (status == TextToSpeech.SUCCESS) {
                    int result = TTS.setLanguage(Locale.US);

                    //If Language is missing, log error
                    if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        buttonSpeak.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });

        SeekBarPitch = findViewById(R.id.seek_bar_pitch);
        SeekBarSpeed = findViewById(R.id.seek_bar_speed);
        buttonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
    }
    private void speak() {
        String text = textViewDescription.getText().toString();

        //If Pitch & speed are less than 0.1, set to 0.1 so it is never minus
        float pitch = (float) SeekBarPitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;

        float speed = (float) SeekBarSpeed.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;

        TTS.setPitch(pitch);
        TTS.setSpeechRate(speed);

        TTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    //destroy TTS layout
    @Override
    protected void onDestroy() {
        if (TTS != null) {
            TTS.stop();
            TTS.shutdown();
        }
        super.onDestroy();
    }
}
