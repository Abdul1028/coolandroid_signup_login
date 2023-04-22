package com.appsnipp.loginsamples;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

public class register extends AppCompatActivity {
    TextView signin_link;
    EditText name_var , email_var , mobile_var , pass_var , reenter_var;
    Button register_var;

    FirebaseAuth auth;
    FirebaseUser user;

     ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signin_link = findViewById(R.id.signin_link);
        signin_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        boolean loggedIn = sharedPreferences.getBoolean("loggedIn", false);

        if (loggedIn) {
            Intent intent = new Intent(register.this, homepage.class);
            startActivity(intent);
            finish();
        }



        progressDialog = new ProgressDialog(this);
        name_var = findViewById(R.id.name);
        email_var = findViewById(R.id.email);
        mobile_var = findViewById(R.id.contact);
        pass_var = findViewById(R.id.pass);
        reenter_var = findViewById(R.id.reenter_pass);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();



        register_var = findViewById(R.id.register_btn);
        register_var.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignIn();
            }
        });


    }

    private void sendToHomePage() {
        Intent to_homepage = new Intent(register.this,homepage.class);
        to_homepage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(to_homepage);
    }

    public void SignIn() {
        String n = name_var.getText().toString();
        String e = email_var.getText().toString();
        String p = pass_var.getText().toString();
        String c = reenter_var.getText().toString();

        Calendar calendar = Calendar.getInstance();
        String date_and_time = DateFormat.getDateInstance().format(calendar.getTime());

        String emailPattern = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$" ;
        if(p.isEmpty() || p.length() < 6 ){
            pass_var.requestFocus();
            pass_var.setError("Password should be of atleast 6 digits");
        }

        else if(!e.matches(emailPattern)){
            email_var.requestFocus();
            email_var.setError("Please enter a valid email");
        }

        else if(!p.equals(c)){
            reenter_var.requestFocus();
            reenter_var.setError("Password not match both fields");
        }
        else {

            progressDialog.setMessage("Please wait Account creation in Progress");
            progressDialog.setTitle("Creating Account");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            auth.createUserWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        progressDialog.dismiss();

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference ref = database.getReference("User_data");
                        Credentials cred = new Credentials (e,n,p,date_and_time);
                        String id = task.getResult().getUser().getUid();
                        database.getReference("User_data").child(id).setValue(cred);



                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        if (user != null) {
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name_var.getText().toString().trim())
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(register.this, "Name also updated", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                        sendBackToLogin();
                        Toast.makeText(register.this, "Account created successfully now you can Login!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(register.this, task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }


    }

    private void sendBackToLogin() {
        Intent to_homepage = new Intent(register.this,LoginActivity.class);
        to_homepage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(to_homepage);
    }

}
