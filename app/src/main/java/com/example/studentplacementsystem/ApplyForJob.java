package com.example.studentplacementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class ApplyForJob extends AppCompatActivity {

    TextView companyName, jobPost, description, workType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_for_job);

        companyName = findViewById(R.id.company_name);
        jobPost = findViewById(R.id.job_post);
        description = findViewById(R.id.company_description);
        workType = findViewById(R.id.working_type);

        Intent intent = getIntent();
        String name = intent.getStringExtra("companyName");
        String post = intent.getStringExtra("jobPost");
        String des = intent.getStringExtra("description");
        String type = intent.getStringExtra("workType");

        companyName.setText("Company Name : " + name);
        jobPost.setText("Job Post : " + post);
        description.setText("Company Description : " + des);
        workType.setText("Working Type : " + type);



    }
}
