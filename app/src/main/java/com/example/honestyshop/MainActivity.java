package com.example.honestyshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity{

    EditText username;
    EditText password;
    Button loginButton;


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
               isUser(v);
            }
        });

    }

    private Boolean validateUsername(){

        String val = username.getText().toString();

        if (val.isEmpty()){
            username.setError("Field cannot be empty");
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
            Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
        }
        else isUser(v);

    }

    private void isUser(View v){

        String userEnteredUsername = username.getText().toString().trim();
        String userEnteredPassword = password.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Employee_Login_Credentials");
        Query checkUser = reference.orderByChild("User_Name").equalTo(userEnteredUsername);


        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String passwordDB = snapshot.child(userEnteredUsername).child("Password").getValue(String.class);
                    if (Objects.equals(passwordDB, userEnteredPassword)){
                            Toast.makeText(MainActivity.this,"Login Successful", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(v.getContext() ,BuyActivity.class);
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
