package com.task2.task3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PhysiologicalInfoActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    User user;
    EditText weig;
    EditText heig;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physiological_info);

        user = (User) getIntent().getSerializableExtra("User");
        weig = (EditText) findViewById(R.id.weightET);
        heig = (EditText) findViewById(R.id.heightTV);
        imageView = findViewById(R.id.physioGoBackIV);

        weig.setText(user.getWeight());
        heig.setText(user.getHeight());

        TextView save = findViewById(R.id.savePhysioTV);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user.setWeight(weig.getText().toString());
                user.setHeight(heig.getText().toString());

                mDatabase = FirebaseDatabase.getInstance().getReference("users");

                mDatabase.child(user.getUsername()).child("weight").setValue(weig.getText().toString());
                mDatabase.child(user.getUsername()).child("height").setValue(heig.getText().toString());

                finish();
                Intent intent = new Intent(getApplicationContext(), Welcome.class);
                startActivity(intent);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Welcome.class);
                startActivity(intent);
            }
        });
    }
}