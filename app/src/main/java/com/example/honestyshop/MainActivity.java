package com.example.honestyshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity{

    EditText username;
    EditText password;
    Button loginButton;

    TextView signupText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username= findViewById(R.id.username);
        password= findViewById(R.id.password);
        loginButton= findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(v);
            }
        });

    }

    private Boolean validateUsername(){

        String val = username.getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()){
            username.setError("Field cannot be enpty");
            return false;
        } else {
            username.setError(null);
            return true;
        }
    }

    private Boolean validatePassword(){

        String val = password.getText().toString();

        if (val.isEmpty()){
            password.setError("Field Cannot be Empty");
            return false;
        }else {
            username.setError(null);
            return true;
        }
    }

    public void loginUser(View v){
        if (!validateUsername() | !validatePassword()){
            return;
        } else { isUser();}
    }

    private void isUser(){

        String userEnteredUsername = username.getText().toString().trim();
        String userEnteredPassword = password.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Employee_Login_Credentials");
        Query checkUser = reference.orderByChild("User_Name").equalTo(userEnteredUsername);


        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String passwordDB = snapshot.child(userEnteredUsername).child("Password").getValue(String.class);
                    if (passwordDB.equals(userEnteredPassword)){
                        Intent i = new Intent(getApplicationContext(),BuyActivity.class);
                        startActivity(i);
                    } else {
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }
                } else{
                    username.setError("No such Username");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}