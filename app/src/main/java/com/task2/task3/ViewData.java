package com.task2.task3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

public class ViewData extends AppCompatActivity {

    User user;
    private DatabaseReference mDatabase;
    TextView targetW;
    TextView targetCal;
    TextView currentW;
    TextView currentH;
    ImageView imageView;
    TextView done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        targetW = findViewById(R.id.targetWeightData);
        targetCal = findViewById(R.id.targetCalorieData);
        currentH = findViewById(R.id.currentHeightData);
        currentW = findViewById(R.id.currentWeightData);
        imageView = findViewById(R.id.dataGoBackIV);
        done = findViewById(R.id.homeViewDataTV);

        user = (User) getIntent().getSerializableExtra("User");

        targetW.setText(user.getTargetweight());
        targetCal.setText(user.getCalorieintake());
        currentH.setText(user.getHeight());
        currentW.setText(user.getWeight());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(getApplicationContext(), Welcome.class);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
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