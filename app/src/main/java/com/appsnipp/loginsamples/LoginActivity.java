package com.appsnipp.loginsamples;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

    TextView signup;
    EditText email_var , pass_var;
    Button login_var;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        email_var = findViewById(R.id.loginemail);
        pass_var = findViewById(R.id.loginpass);
        login_var = findViewById(R.id.login_btn);

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();



        login_var.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logIn();

            }
        });




        signup = findViewById(R.id.signup_link);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),register.class));
            }
        });
    }

    private void logIn() {
        String username = email_var.getText().toString();
        String password = pass_var.getText().toString();



        if (username.isEmpty()){
            email_var.setError("Username cannot be empty!");
            email_var.requestFocus();
            Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
        }

        else if(password.isEmpty()){
            pass_var.setError("Password cannot be empty!");
            pass_var.requestFocus();

        }

        else {
            progressDialog.setTitle("Logging IN");
            progressDialog.setMessage("Logging you in please wait");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("loggedIn", true);
                        editor.apply();
                        sendToHomePage();
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                    }

                    else{
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendToHomePage() {
        Intent to_homepage = new Intent(LoginActivity.this,homepage.class);
        to_homepage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(to_homepage);
    }
}
