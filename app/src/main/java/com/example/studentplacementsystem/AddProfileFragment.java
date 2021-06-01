package com.example.studentplacementsystem;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class AddProfileFragment extends Fragment {
    private  Button add;
    private TextInputLayout sName,sPhone,sCity,sEmail;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    String userID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adddetail,container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Edit Profile");

        add = view.findViewById(R.id.sAdd);

        sName = view.findViewById(R.id.sName);
        sPhone = view.findViewById(R.id.sPhone);
        sCity = view.findViewById(R.id.sCity);
        sEmail = view.findViewById(R.id.sEmail);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        MainActivity activity = (MainActivity) getActivity();
        userID = activity.getMyData();


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = sName.getEditText().getText().toString().trim();
                String phone = sPhone.getEditText().getText().toString().trim();
                String Email = sEmail.getEditText().getText().toString();
                String City = sCity.getEditText().getText().toString();

                if(TextUtils.isEmpty(name)) {
                    sName.setError("Name is Required");
                    return;
                }

                DocumentReference documentReference = fStore.collection("user").document(userID);

                Map<String, Object> user = new HashMap<>();
                user.put("StudentName",name);
                user.put("StudentPhone",phone);
                user.put("StudentEmail",Email);
                user.put("StudentCity",City);

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

        RetrieveUserInfo();

        return view;
    }

    private void RetrieveUserInfo() {

        DocumentReference documentReference = fStore.collection("user").document(userID);

        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                sName.getEditText().setText(value.getString("StudentName"));
                sEmail.getEditText().setText(value.getString("StudentEmail"));
                sCity.getEditText().setText(value.getString("StudentCity"));
                sPhone.getEditText().setText(value.getString("StudentPhone"));
            }
        });
    }

}
