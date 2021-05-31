package com.example.callcrowd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText emailBox,passwordBox;
    Button loginButton,createButton;
    FirebaseAuth auth;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dialog=new ProgressDialog(this);
        dialog.setMessage("Please wait...");


        auth=FirebaseAuth.getInstance();

        emailBox=(EditText) findViewById(R.id.emailBox);
        passwordBox=(EditText) findViewById(R.id.passwordBox);

        loginButton=(Button) findViewById(R.id.loginButton);
        createButton=(Button) findViewById(R.id.createButton);


        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.show();
                String email, pass;
                email = emailBox.getText().toString();
                pass = passwordBox.getText().toString();

                if(email.length()==0){
                    email=" ";
                }

                if(pass.length()==0){
                    pass=" ";
                }

                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();
                        if(task.isSuccessful())
                        {
                            startActivity(new Intent(LoginActivity.this,DashboardActivity.class));

                        } else {
                            Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });
    }
}