package com.example.studentplacementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ApplyForJob extends AppCompatActivity {

    TextView companyName, jobPost, description, workType;
    private Button applyBut;
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_for_job);

        getSupportActionBar().setTitle("Company Details");

        currentUser = getIntent().getStringExtra("Er_num");
        companyName = findViewById(R.id.company_name);
        jobPost = findViewById(R.id.job_post);
        description = findViewById(R.id.company_description);
        workType = findViewById(R.id.working_type);

        applyBut = findViewById(R.id.apply_for_job);

        Intent intent = getIntent();
        final String[] name = {intent.getStringExtra("companyName")};
        final String post = intent.getStringExtra("jobPost");
        final String des = intent.getStringExtra("description");
        final String type = intent.getStringExtra("workType");
        final String companyId = intent.getStringExtra("companyId");
        final String tpoId = intent.getStringExtra("userId");

        companyName.setText( name[0]);
        jobPost.setText( post);
        description.setText(des);
        workType.setText(type);

        applyBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String id = UUID.randomUUID().toString();

//                DocumentReference documentReference = fStore.collection("Companies").document(companyId)
//                                                        .collection("Apply").document(id);
//
//                Map<String,Object> apply = new HashMap<>();
//                apply.put("user_id",currentUser);
//
//                documentReference.set(apply).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(ApplyForJob.this, "Successfully Applied", Toast.LENGTH_SHORT).show();
//                        applyBut.setText("Applied");
//                    }
//                });
                final String[] sName = new String[1];
                final String[] sEmail = new String[1];
                final String[] sCgpa = new String[1];
                final DocumentReference documentReference = fStore.collection("Apply").document(id);
                Task<DocumentSnapshot> documentReference1 = fStore.collection("user").document(currentUser)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                sName[0] = documentSnapshot.getString("StudentName");
                                sEmail[0] = documentSnapshot.getString("StudentEmail");
                                sCgpa[0] = documentSnapshot.get("Cgpa").toString();

                                Log.d("JJJ",sName[0] + " " + sEmail[0]);
                                final Map<String,Object> apply = new HashMap<>();
                                apply.put("company_id",companyId);
                                apply.put("user_id",currentUser);
                                apply.put("tpo_id",tpoId);
                                apply.put("status","applied");
//                                apply.put("companyDescription",des);
//                                apply.put("companyName", name[0]);
//                                apply.put("jobPost",post);
//                                apply.put("workType",type);
//                                apply.put("studentName", sName[0]);
//                                apply.put("studentEmail",sEmail[0]);
                                apply.put("Cgpa",sCgpa[0]);



                                documentReference.set(apply).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(ApplyForJob.this, "Successfully Applied", Toast.LENGTH_SHORT).show();
                                        applyBut.setText("Applied");
                                    }
                                });
                            }
                        });
            }
        });

    }
}
