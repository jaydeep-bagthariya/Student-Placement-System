package com.example.studentplacementsystem;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class AddJobFragment extends Fragment {

    private TextInputLayout cName, cDescription, jobPost, workType;
    private Button addJob;

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_job, container, false);

        cName = view.findViewById(R.id.cName);
        cDescription = view.findViewById(R.id.cDescription);
        jobPost = view.findViewById(R.id.jobPost);
        workType = view.findViewById(R.id.workType);
        addJob = view.findViewById(R.id.addJob);


        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        addJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = cName.getEditText().getText().toString().trim();
                String description = cDescription.getEditText().getText().toString().trim();
                String jobpost = jobPost.getEditText().getText().toString().trim();
                String type = workType.getEditText().getText().toString().trim();
                String id = UUID.randomUUID().toString();
                String userID;

               userID = fAuth.getCurrentUser().getUid();

                if(TextUtils.isEmpty(name)) {
                    cName.setError("Company Name is required!");
                    return;
                }
                if(TextUtils.isEmpty(description)) {
                    cDescription.setError("Company Description is required!");
                    return;
                }
                if(TextUtils.isEmpty(jobpost)) {
                    jobPost.setError("Job Post is required!");
                    return;
                }
                if(TextUtils.isEmpty(type)) {
                    workType.setError("Work Type is required!");
                    return;
                }

                DocumentReference documentReference = fStore.collection("Companies").document(id);

                Map<String,Object> companies = new HashMap<>();
                companies.put("companyName",name);
                companies.put("companyDescription",description);
                companies.put("jobPost",jobpost);
                companies.put("workType",type);
                companies.put("user_id",userID);

                documentReference.set(companies).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "Company Added!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        return view;
    }
}
