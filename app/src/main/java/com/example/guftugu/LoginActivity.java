package com.example.guftugu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwrd;
    AppCompatButton login;
    AppCompatButton signUp;
    FirebaseAuth mAuth;
    TextView forgetPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        passwrd = findViewById(R.id.editTextTextPassword);
        signUp = findViewById(R.id.button2);
        login = findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();
        forgetPassword = findViewById(R.id.textForgetPassword);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,Sign_Up_activity.class));
            }
        });




        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgetPassword.class));

            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(LoginActivity.this, "Login Button", Toast.LENGTH_SHORT).show();
                String email = emailEditText.getText().toString();
                String passwordS = passwrd.getText().toString();
                mAuth.signInWithEmailAndPassword(email,passwordS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(LoginActivity.this, DashBoard.class));
                        }else{
                            Toast.makeText(LoginActivity.this, task.getException().getMessage().toString(),Toast.LENGTH_SHORT).show();
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


    }



}