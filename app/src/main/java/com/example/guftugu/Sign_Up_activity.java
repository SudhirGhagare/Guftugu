package com.example.guftugu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Sign_Up_activity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwrd;
    EditText nameBox;
    AppCompatButton login;
    AppCompatButton signUp;
    FirebaseAuth mAuth;
    FirebaseFirestore database;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up_activity);

        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        passwrd = findViewById(R.id.editTextTextPassword);
        signUp = findViewById(R.id.button2);
        login = findViewById(R.id.button);
        nameBox = findViewById(R.id.editTextTextNameBox);
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Sign_Up_activity.this, LoginActivity.class));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email,passwors,name;

                Email = emailEditText.getText().toString();
                passwors = passwrd.getText().toString();
                name = nameBox.getText().toString();
                progressBar.setVisibility(View.VISIBLE);

                Users user = new Users();
                user.setEmail(Email);
                user.setPassword(passwors);
                user.setUsername(name);

                mAuth.createUserWithEmailAndPassword(Email,passwors).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            database.collection("User").document("Login Details")
                                    .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    startActivity(new Intent(Sign_Up_activity.this, LoginActivity.class));
                                }
                            });
                            
                            progressBar.setVisibility(View.INVISIBLE);
                            nameBox.setText("");
                            emailEditText.setText("");
                            passwrd.setText("");

                        }else{
                            Toast.makeText(Sign_Up_activity.this, task.getException().getLocalizedMessage(),Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });

            }
        });

        emailEditText.setOnTouchListener(new View.OnTouchListener() {
            final int DRAWABLE_RIGHT = 2;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(event.getRawX() >= (emailEditText.getRight() - emailEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())){
                        emailEditText.setText("");
                        return true;
                    }
                }
                return false;
            }
        });

        nameBox.setOnTouchListener(new View.OnTouchListener() {
            final int DRAWABLE_RIGHT = 2;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(event.getRawX() >= (nameBox.getRight() - nameBox.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())){
                        nameBox.setText("");
                        return true;
                    }
                }
                return false;
            }
        });


    }
}