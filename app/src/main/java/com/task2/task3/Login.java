package com.task2.task3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login = findViewById(R.id.loginButton);
        Button register = findViewById(R.id.registerButton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText usernameValue = findViewById(R.id.emailTextViewLog);
                final EditText passwordValue = findViewById(R.id.passwordTextViewLog);

                Log.i("Logged In & Data ----> ", usernameValue.getText().toString() + " _-_ " + passwordValue.getText().toString());

                // Get a reference to our posts
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference users = database.getReference("users");

                // Attach a listener to read the data at posts reference
                Query getUser = users.orderByChild("username").equalTo(usernameValue.getText().toString());
                getUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String passwordEntered = snapshot.child(usernameValue.getText().toString()).child("password").getValue().toString();

                        if (passwordEntered.equals(passwordValue.getText().toString())){
                            Log.i("User Logged ?", " --------------> Yes, username and password match. Logged in!");
                            Intent intent = new Intent(getBaseContext(), Welcome.class);
                            intent.putExtra("status", "true");
                            intent.putExtra("username", usernameValue.getText().toString());
                            intent.putExtra("password", passwordEntered);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });
    }
}