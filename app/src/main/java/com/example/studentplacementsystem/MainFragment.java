package com.example.studentplacementsystem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainFragment extends Fragment {
    TextView fullName, email, phone, city, field, qualification, erNo, passingYear, CGPA;
    String userId;
    CircleImageView imageView;
    private StorageReference  mStorage;
    private FirebaseFirestore rootRef;
    private ProgressDialog loadingBar;
    String Er_num;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container, false);

        MainActivity activity = (MainActivity) getActivity();
        Er_num = activity.getMyData();

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Student Profile");


        rootRef = FirebaseFirestore.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference().child("Profile Images");
        loadingBar = new ProgressDialog(getContext());

        imageView = view.findViewById(R.id.profile_image);
        fullName = view.findViewById(R.id.profileName);
        email = view.findViewById(R.id.profileEmail);
        phone = view.findViewById(R.id.profilePhone);
        city = view.findViewById(R.id.profileCity);
        qualification = view.findViewById(R.id.profileQualification);
        erNo = view.findViewById(R.id.profileErNo);
        passingYear = view.findViewById(R.id.profilePassingYear);
        field = view.findViewById(R.id.profileField);
        CGPA = view.findViewById(R.id.profileCGPA);

        setProfilePick();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i,1);
            }
        });
        


        final DocumentReference docIdRef = rootRef.collection("user").document(Er_num);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        phone.setText(document.getString("StudentPhone"));
                        fullName.setText( document.getString("StudentName"));
                        email.setText(document.getString("StudentEmail"));
                        city.setText(document.getString("StudentCity"));
                        qualification.setText(document.getString("StudentQualification"));
                        erNo.setText(document.getString("StudentEr"));
                        passingYear.setText(document.getString("StudentPassingYear"));
                        field.setText(document.getString("StudentField"));
//                        CGPA.setText("CGPA : " + document.getString("Cgpa").toString());
                        CGPA.setText(document.get("Cgpa").toString());
                    } else {
                        Log.d("BADSHAH", "Document does not exist!");
                    }
                } else {
                    Log.d("BADSHAH", "Failed with: ", task.getException());
                }
            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode== Activity.RESULT_OK) {
            Uri uri = data.getData();
            Log.d("JAY", "message" + uri);
            CropImage.activity(uri)
                    .setAspectRatio(1, 1)
                    .start(getContext(),this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(resultCode == Activity.RESULT_OK){

                loadingBar.setTitle("Set Profile Image");
                loadingBar.setMessage("Please wait your profile image is updating");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                Uri resultUri = result.getUri();

                final StorageReference filepath=mStorage.child(Er_num + ".jpg");

                UploadTask uploadTask = filepath.putFile(resultUri);

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {

                            throw task.getException();
                        }

                        // Continue the task to get a download url
                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()) {
                            final String downloadUri = task.getResult().toString();

                            Task<Void> documentSnapshot1 = rootRef.collection("user").document(Er_num)
                                    .update("profileImage",downloadUri);
                            Toast.makeText(getContext(), "Profile Successfully Upload", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                        else {
                            loadingBar.dismiss();
                            Toast.makeText(getContext(), "Error While Uploading", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

        }
    }

    private void setProfilePick() {

        final DocumentReference docIdRef = rootRef.collection("user").document(Er_num);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if(document.exists() && document.getString("profileImage")!=null){
                        String profileImage = document.getString("profileImage");
                        Glide.with(getContext()).load(profileImage).into(imageView);
                    }

                }

            }
        });
    }

}
