package com.task2.task3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingActivity extends AppCompatActivity {

    User user;
    private DatabaseReference mDatabase;
    RadioButton m;
    RadioButton i;
    TextView save;
    TextView targets;
    TextView weightTrack;
    ImageView goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        m = findViewById(R.id.metric);
        i = findViewById(R.id.imperial);
        RadioGroup rg = findViewById(R.id.rbGroup);
        save = findViewById(R.id.saveSettingsTV);
        targets = findViewById(R.id.targetTV);
        weightTrack = findViewById(R.id.weighTrackTV);
        goBack = findViewById(R.id.settingsGoBackIV);

        user = (User) getIntent().getSerializableExtra("User");

        Log.i("System", " ----------------------------> " + user.getSystem());

        if (user.getSystem().equals("Metric")) {
            m.setChecked(true);
            i.setChecked(false);
        }

        if (user.getSystem().equals("Imperial")) {
            i.setChecked(true);
            m.setChecked(false);
        }

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Welcome.class);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });

//        if (m.getText().toString().equals(status) || m.getText().toString().toLowerCase().equals(status)){
//            m.setChecked(true);
//            i.setChecked(false);
//        }
//        if (i.getText().toString().equals(status) || i.getText().toString().toLowerCase().equals(status)){
//            i.setChecked(true);
//            i.setChecked(false);
//        }

        m.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                i.setSelected(false);
                m.setSelected(true);

                user.setSystem(m.getText().toString());
            }
        });

        i.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                i.setSelected(true);
                m.setSelected(false);

                user.setSystem(i.getText().toString());
            }
        });

        targets.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), TargetActivity.class);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });
        weightTrack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), TrackWeightActivity.class);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (m.isSelected()) {
                    Toast.makeText(SettingActivity.this, "Change successful", Toast.LENGTH_SHORT).show();
                    mDatabase = FirebaseDatabase.getInstance().getReference("users");
                    mDatabase.child(user.getUsername()).child("system").setValue("Metric");

                    Intent intent = new Intent(getApplicationContext(), Welcome.class);
                    intent.putExtra("User", user);
                    startActivity(intent);
                }
                if (i.isSelected()) {
                    Toast.makeText(SettingActivity.this, "Change successful", Toast.LENGTH_SHORT).show();
                    mDatabase = FirebaseDatabase.getInstance().getReference("users");
                    mDatabase.child(user.getUsername()).child("system").setValue("Imperial");

                    Intent intent = new Intent(getApplicationContext(), Welcome.class);
                    intent.putExtra("User", user);
                    startActivity(intent);
                }
            }
        });
    }
}