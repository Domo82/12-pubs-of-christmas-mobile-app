package com.example.splash;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Crawl extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void goPip(View view) {
        enterPictureInPictureMode();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crawl);

        //Array that adds a new location to ListView
        ListView listView = findViewById(R.id.listView);
        ArrayList<String> places = new ArrayList<String>();
        places.add("Add a pub to your list!...");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, places);
        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), Map.class);
                intent.putExtra("placeNumber", 0);
                startActivity(intent);
            }
        });

    }
}
