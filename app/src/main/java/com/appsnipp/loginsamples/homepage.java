package com.appsnipp.loginsamples;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        Button logout , raise;

        logout = findViewById(R.id.logout_btn);
        raise = findViewById(R.id.complaint_btn);


        raise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),raise_complaint.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("loggedIn");
                editor.apply();


                Intent intent = new Intent(homepage.this, LoginActivity.class);
                startActivity(intent);

                Toast.makeText(homepage.this, "Logged Out successfully!", Toast.LENGTH_SHORT).show();

                finish();
            }
        });



    }
}