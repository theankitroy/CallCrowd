package com.example.callcrowd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {
    FirebaseFirestore databse;
    FirebaseAuth auth;
    EditText emailBox,passwordBox,nameBox;
    Button loginButton,createButton;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        dialog=new ProgressDialog(this);
        dialog.setMessage("Please wait...");


        auth=FirebaseAuth.getInstance();
        databse=FirebaseFirestore.getInstance();

        emailBox= (EditText)findViewById(R.id.emailBox);
        passwordBox=(EditText)findViewById(R.id.passwordBox);
        nameBox= (EditText)findViewById(R.id.nameBox);

        loginButton=findViewById(R.id.loginButton);
        createButton=findViewById(R.id.createButton);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                String pass=null,email=null,name=null;
                email=emailBox.getText().toString();

                pass=passwordBox.getText().toString();
                name=nameBox.getText().toString();

                if(email.length()==0){
                    email=" ";
                }
                if(name.length()==0){
                    name=" ";
                }
                if(pass.length()==0){
                    pass=" ";
                }

                User user=new User();
                user.setEmail(email);
                user.setPass(pass);
                user.setName(name);
                auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();
                        if(task.isSuccessful()){
                            databse.collection("Users")
                                    .document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                                    Toast.makeText(SignupActivity.this, "Account Created. Please Login", Toast.LENGTH_SHORT).show();

                                }
                            });


                        }
                        else{
                            Toast.makeText(SignupActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
            }
        });
    }
}