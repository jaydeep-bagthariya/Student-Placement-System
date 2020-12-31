package com.example.studentplacementsystem;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Modules extends AppCompatActivity {

    Button mStudent, mTPO, mAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modules);

        mStudent = findViewById(R.id.student);
        mTPO = findViewById(R.id.placementOfficer);
        mAdmin = findViewById(R.id.admin);

        mStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

        mTPO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginOfTPO.class));
            }
        });
    }
}
