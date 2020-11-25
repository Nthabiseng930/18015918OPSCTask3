package com.task2.task3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Register extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Button register = (Button) findViewById(R.id.registerButton);

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText name = (EditText) findViewById(R.id.nameTextViewReg);
                EditText username = (EditText) findViewById(R.id.emailTextViewReg);
                EditText password = (EditText) findViewById(R.id.passwordTextViewReg);

                User user = new User();
                user.setFirstName(name.getText().toString());
                user.setUsername(username.getText().toString());
                user.setPassword(password.getText().toString());
                user.setCalorieintake("0");
                user.setDailyweight(new ArrayList<Double>());
                user.setHeight("0");
                user.setTargetweight("0");
                user.setWeight("0");
                user.setSystem("metric");

                mDatabase.child("users").child(username.getText().toString()).setValue(user);
                Toast.makeText(Register.this, "Register!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), Login.class);
                startActivity(intent);
            }
        });
    }
}