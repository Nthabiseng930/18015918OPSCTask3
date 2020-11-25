package com.task2.task3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TargetActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    User user;
    TextView save;
    TextView targetWeightET;
    TextView caloriesIntakeET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);

        user = (User) getIntent().getSerializableExtra("User");

        save = findViewById(R.id.saveTargetTV);
        targetWeightET = findViewById(R.id.weightTargetET);
        caloriesIntakeET = findViewById(R.id.caLorieIntakeET);

        Log.i("Target", "----------> " + user.getCalorieintake() + " " + user.getTargetweight());
        caloriesIntakeET.setText(user.getCalorieintake());

        targetWeightET.setText(user.getTargetweight());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText weig = (EditText) findViewById(R.id.weightTargetET);
                EditText intake = (EditText) findViewById(R.id.caLorieIntakeET);

                user.setTargetweight(weig.getText().toString());
                user.setCalorieintake(intake.getText().toString());

                mDatabase = FirebaseDatabase.getInstance().getReference("users");

                mDatabase.child(user.getUsername()).child("targetweight").setValue(weig.getText().toString());
                mDatabase.child(user.getUsername()).child("calorieintake").setValue(intake.getText().toString());

                Toast.makeText(TargetActivity.this, "Targets Added", Toast.LENGTH_SHORT).show();

                finish();
                Intent intent = new Intent(getApplicationContext(), Welcome.class);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });
    }
}