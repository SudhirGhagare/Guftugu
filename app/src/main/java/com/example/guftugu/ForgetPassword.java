package com.example.guftugu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    EditText emailText;
    AppCompatButton sendLink;
    AppCompatButton backButton;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        emailText = findViewById(R.id.EmailAddress);
        sendLink = findViewById(R.id.buttonLink);
        backButton = findViewById(R.id.buttonBack);
        mAuth = FirebaseAuth.getInstance();


        sendLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailText.getText().toString().trim();

                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ForgetPassword.this, "Check you email inbox, Link had been send on your email ID",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(ForgetPassword.this,task.getException().getMessage().toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ForgetPassword.this, e.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        emailText.setOnTouchListener(new View.OnTouchListener() {
            final int DRAWABLE_RIGHT = 2;
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP ){
                    if(event.getRawX() >= (emailText.getRight() - emailText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())){
                        emailText.setText("");
                        return true;
                    }
                }
                return false;
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgetPassword.this, LoginActivity.class));
            }
        });
    }
}