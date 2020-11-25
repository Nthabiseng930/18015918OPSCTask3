package com.task2.task3;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.util.Calendar;

public class Welcome extends AppCompatActivity {

    User user;
    public static LoggedIn isLoggedIn;
    public static String usernameG;
    public static String passwordG;
    String online;
    User active;
    String username;
    String password;
    String first = "";

    TextView physioTV;
    TextView recordMealTV;
    TextView settingTV;
    TextView viewTV;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        active = new User();


        logout = findViewById(R.id.logoutButton);
        physioTV = findViewById(R.id.physioInfoTV);
        recordMealTV = findViewById(R.id.recordMealTV);
        settingTV = findViewById(R.id.settingsTV);
        viewTV = findViewById(R.id.viewTV);

        user = (User) getIntent().getSerializableExtra("User");

        online = getIntent().getStringExtra("status");
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");

        isLoggedIn = new LoggedIn();
        isLoggedIn.setLoggedI(Boolean.parseBoolean(online));

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });

        if (username != null && password != null) {

            // Get a reference to our posts
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference users = database.getReference("users");

            Query getUser = users.orderByChild("username").equalTo(username);
            getUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String passwordEntered = snapshot.child(username).child("password").getValue().toString();
                    String u = username;

                    if (passwordEntered.equals(password)){
                        String first = snapshot.child(u).child("firstName").getValue(String.class);
                        String last = snapshot.child(u).child("lastName").getValue(String.class);
                        String system = snapshot.child(u).child("system").getValue(String.class);
                        String currentW = snapshot.child(u).child("targetweight").getValue(String.class);
                        String currentH = snapshot.child(u).child("height").getValue(String.class);
                        String targetWeight = snapshot.child(u).child("targetweight").getValue(String.class);
                        String intakeCal = snapshot.child(u).child("calorieintake").getValue(String.class);
                        active.setUsername(u);
                        active.setPassword(passwordEntered);
                        active.setFirstName(first);
                        active.setLastName(last);
                        active.setSystem(system);
                        active.setTargetweight(targetWeight);
                        active.setCalorieintake(intakeCal);
                        active.setHeight(currentH);
                        active.setWeight(currentW);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


        physioTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Welcome.this, PhysiologicalInfoActivity.class);
                intent.putExtra("User", active);
                startActivity(intent);
            }
        });

        recordMealTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Welcome.this, RecordMealActivity.class);
                intent.putExtra("User", active);
                startActivity(intent);
            }
        });

        settingTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Welcome.this, SettingActivity.class);

                if (active != null) {
                    intent.putExtra("User", active);
                }
                if (user != null) {
                    intent.putExtra("User", user);
                }

                startActivity(intent);
            }
        });

        viewTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Welcome.this, ViewData.class);

                if (active != null) {
                    Log.i("Data Sent", "-----------> " + active.getHeight() + " " + active.getWeight());
                    intent.putExtra("User", active);
                }
                if (user != null) {
                    Log.i("Data Sent", "New Teq -----------> " + active.getHeight() + " " + active.getWeight());
                    intent.putExtra("User", user);
                }

                startActivity(intent);
            }
        });
    }
}