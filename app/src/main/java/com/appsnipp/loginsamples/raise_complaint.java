package com.appsnipp.loginsamples;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class raise_complaint extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raise_complaint);

        Button cc_btn ;

        cc_btn = findViewById(R.id.complaint_btn);
        cc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText name_var, email_var, contact_var, query_var;

                name_var = findViewById(R.id.comp_name);
                email_var = findViewById(R.id.comp_email);
                contact_var = findViewById(R.id.comp_contact);
                query_var = findViewById(R.id.comp_query);

                String name = name_var.getText().toString();
                String email = email_var.getText().toString();
                String contact = contact_var.getText().toString();
                String query = query_var.getText().toString();
                String emailPattern = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";


                if (name.isEmpty()) {
                    name_var.requestFocus();
                    name_var.setError("Please enter your name");
                }
                else if (contact.isEmpty()) {
                    contact_var.requestFocus();
                    contact_var.setError("Please enter your contact number");
                }else if (!email.matches(emailPattern)) {
                    email_var.requestFocus();
                    email_var.setError("Please enter a valid email");
                }  else if (query.isEmpty()) {
                    query_var.requestFocus();
                    query_var.setError("Please enter your Query");
                } else {

                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    if (currentUser != null) {
                        String uid = currentUser.getUid();
                        database.getReference("Complaint_data");
                        Complaints comp = new Complaints(name, contact, email, query);
                        database.getReference("Complaint_data").child(uid).setValue(comp);
                        Toast.makeText(raise_complaint.this, name + " your complaint raised successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), homepage.class));
                    } else {
                        Toast.makeText(raise_complaint.this, "Someone tried to use complaints without login", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }
}