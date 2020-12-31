package com.example.studentplacementsystem;

import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.w3c.dom.Text;

import java.util.concurrent.Executor;

public class ProfileFragment extends Fragment {
    TextView fullName, email, phone, city, field, qualification, erNo, passingYear;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        fullName = view.findViewById(R.id.profileName);
        email = view.findViewById(R.id.profileEmail);
        phone = view.findViewById(R.id.profilePhone);
        city = view.findViewById(R.id.profileCity);
        qualification = view.findViewById(R.id.profileQualification);
        erNo = view.findViewById(R.id.profileErNo);
        passingYear = view.findViewById(R.id.profilePassingYear);
        field = view.findViewById(R.id.profileField);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        final DocumentReference documentReference = fStore.collection("users").document(userId);

        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                phone.setText("Phone : "+value.getString("StudentPhone"));
                fullName.setText("Name : " + value.getString("StudentName"));
                email.setText("Email : " +value.getString("StudentEmail"));
                city.setText("City : "+value.getString("StudentCity"));
                qualification.setText("Qualification : "+value.getString("StudentQualification"));
                erNo.setText("Er No. : "+value.getString("StudentEr"));
                passingYear.setText("Passing Year : "+value.getString("StudentPassingYear"));
                field.setText("Field : "+value.getString("StudentField"));

            }
        });

//        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if(documentSnapshot.exists()) {
//                    phone.setText(documentSnapshot.getString("phone"));
//                    fullName.setText(documentSnapshot.getString("fName"));
//                    email.setText(documentSnapshot.getString("email"));
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
//            }
//        });
        return view;
    }
}
