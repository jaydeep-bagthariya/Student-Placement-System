package com.example.studentplacementsystem;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddDetailOfTpoFragment extends Fragment {

    private Button save;
    private TextInputLayout tpo_name, tpo_email, tpo_number;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    String userID;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_detail_of_tpo,container, false);

        save = view.findViewById(R.id.tAdd);

        tpo_name = view.findViewById(R.id.tpo_name);
        tpo_email = view.findViewById(R.id.tpo_email);
        tpo_number = view.findViewById(R.id.tpo_phone_number);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = tpo_name.getEditText().getText().toString().trim();
                String email = tpo_email.getEditText().getText().toString().trim();
                String phone = tpo_number.getEditText().getText().toString().trim();

                if(TextUtils.isEmpty(name)){
                    tpo_name.setError("Name is Required");
                    return;
                }
                if(TextUtils.isEmpty(phone)) {
                    tpo_number.setError("Number is Required");
                }

                DocumentReference documentReference = fStore.collection("TPOs").document(userID);

                Map<String, Object> user = new HashMap<>();
                user.put("TPOName",name);
                user.put("TPOPhone",phone);
                user.put("TPOEmail",email);

                documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "Detail Successfully Added!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error !" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        return view;
    }
}
