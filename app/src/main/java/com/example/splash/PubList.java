package com.example.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

    public class PubList extends AppCompatActivity {
    //create array data to store listview
    //Title
    String[] listviewTitle = new String[] {
            "O' Donoghues", "The Confession Box", "The Celt", "The Palace", "O'Neills",
            "Mulligans", "Toners"
    } ;
    //Description
    String[] listviewDescription = new String[] {
            "15 Suffolk St, Dublin", "88 Marlborough St, Dublin", "81 Talbot St, Dublin", "21 Fleet St, Dublin",
            "2 Suffolk St, Dublin", "8 Poolbeg St, Dublin", "139 Baggot St Lower, Dublin"
    } ;
    //Images
    int[] listviewImages = new int[] {
            R.drawable.odonoghuesresize, R.drawable.confessionboxresize, R.drawable.theceltresize,
            R.drawable.palaceresize, R.drawable.oneillsresize, R.drawable.mulligansresize, R.drawable.tonersresize
    };
    //information
        String[] listviewInfo = new String[] {
                // O Donoghues
            "Popular with people of all ages, locals and tourists alike. O’Donoghue’s offers a lively environment to with friends or work colleagues. " +
                    " A warm welcome and the best pints in town are always the order of the fay.",
                // Confession Box
            "The Confession Box is the authentic cosy Dublin pub frequented by local Dublin regulars and many repeat visitors from afar.",
                // The Celt
            "If you’re looking for somewhere to jog the night away then look no further than The Celt Bar. Here you’ll be treated to music every night of the week!. " +
                    "When you come to The Celt Bar you can expect a warm and friendly welcome and don’t forget to ask one of our servers for a pint of the Black Stuff to really feel like you’re at home. " +
                    "The Celt is located in the north side of Dublin and it’s as appealing to visitors to the area as it is to the locals.",
                // The Palace
            "Untainted, unspoiled and unperturbed by the passage of time, that’s what makes The Palace Bar one of Dublin’s best-loved original Victorian pubs. " +
                    "Today the Palace is run by Bill’s son Liam and his grandson William. The decor of the Palace is the same as that which greeted the first customers 189 years ago.",
                // O'Neills
            "O’Neill’s Bar is a genuine traditional Old Irish pub renowned for its friendly staff and patrons. Its great atmosphere and world-famous food are why people find it hard to leave. " +
                    "The O’Neill’s Bar and family have become synonymous with Suffolk Street with over 300 years of history in the association. It looks like a regular pub from the outside. " +
                    "But inside it’s a pleasure to drink in, with its various nooks and crannies, upstairs and down. Whether you want to enjoy the great pub atmosphere or find a quite corner for yourself, O’Neill’s Bar have it all.",
                // Mulligans
            "Mulligan’s is more than a Dublin pub, it is an Irish cultural phenomenon. It has a unique and colourful history, spanning over two hundred years. Mulligan’s has hosted the famous Judy Garland, Seamus Heaney, Con Houlihan, James Joyce and John F.Kennedy.",
                // Toners
            "Situated on Baggot Street, Toners is one of Dublin’s oldest and most famous traditional pubs. To prove this, they were the overall winners of “Best Traditional Pub” in the National Hospitality Awards 2014. In September 2015 they also won Dublin Bar of the Year at the Sky Bar of the Year Awards. " +
                    "Original features in the pub take visitors back in time, including the old stock drawers behind the bar from when Toners first opened in 1818 as a bar and grocery shop.",

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_list);

        //slistviewImages = findViewById(R.id.list_view);

        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
        for (int x = 0; x < 7; x++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("ListTitle", listviewTitle[x]);
            hm.put("ListDescription", listviewDescription[x]);
            hm.put("ListInformation", listviewInfo[x]);
            hm.put("ListImages", Integer.toString(listviewImages[x]));
            aList.add(hm);
        }

        String[] from = {
                "ListImages", "ListTitle", "ListDescription","ListInformation",
        };

        int[] to = {
                R.id.listview_images, R.id.Title, R.id.SubTitle
        };

        //On click listeners to provide data for specific item clicks
        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_items, from, to);
        final ListView simpleListView = findViewById(R.id.list_view);
        simpleListView.setAdapter(simpleAdapter);
        simpleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String TempTitleView = listviewTitle[position].toString();
                String TempInfoView = listviewInfo[position].toString();

                Intent intent = new Intent(PubList.this, PubListClickActivity.class);
                intent.putExtra("ListViewTitleClickValue", TempTitleView);
                intent.putExtra("ListViewInformationValue", TempInfoView);

                startActivity(intent);
            }
        });

    }

    }
