package com.task2.task3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class TrackWeightActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    User user;
    long count;
    ImageView backButton;
    ArrayAdapter arrayAdapter;
    ArrayList values;
    ListView listView;
    TextView homeTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_weight);

        //final ScrollView scroll = findViewById(R.id.scrollview);
        final TextView textv = new TextView(this);
        listView = findViewById(R.id.listview);
        homeTV = findViewById(R.id.trackHomeTV);
        backButton = findViewById(R.id.trackWeightBackIV);

        values = new ArrayList();

        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        user = (User) getIntent().getSerializableExtra("User");
        TextView record = findViewById(R.id.logWeightTV);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //this code was directly copied from a friend
                Iterable<DataSnapshot> list = snapshot.child(user.getUsername()).child("dailyweight").getChildren();
                count = snapshot.child(user.getUsername()).child("dailyweight").getChildrenCount();

                Log.i("Child Count", "--------------> " + count);
                for (Iterator<DataSnapshot> i = list.iterator(); i.hasNext();) {
                    DataSnapshot item = i.next();
                    values.add(item.getValue());
                    //Log.i("Long is", "----------------> " + item.getValue());
                }
                //this code was directly copied from a friend

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        arrayAdapter = new ArrayAdapter(TrackWeightActivity.this, android.R.layout.simple_list_item_1, values);
        listView.setAdapter(arrayAdapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText weig = (EditText) findViewById(R.id.weight3);

                String pattern = "dd-MM-yyyy";
                String dateInString = new SimpleDateFormat(pattern).format(new Date());


                mDatabase.child(user.getUsername()).child("dailyweight").child(Long.toString(count+1)).setValue(weig.getText().toString() + " - on the " + dateInString);

                finish();
                startActivity(getIntent());
            }
        });

        homeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(getApplicationContext(), Welcome.class);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });
    }
}