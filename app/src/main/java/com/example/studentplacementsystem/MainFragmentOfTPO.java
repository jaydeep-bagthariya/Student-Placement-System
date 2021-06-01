package com.example.studentplacementsystem;

import android.app.Activity;
import android.app.ProgressDialog;
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
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainFragmentOfTPO extends Fragment {

    TextView name , email, phone, city;
    CircleImageView imageView;
    FirebaseAuth fAuth;
    private StorageReference  mStorage;
    private FirebaseFirestore rootRef;
    private ProgressDialog loadingBar;
    String userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_of_tpo,container, false);

        name = view.findViewById(R.id.tName);
        email = view.findViewById(R.id.tEmail);
        phone = view.findViewById(R.id.tPhone);
        city = view.findViewById(R.id.tCity);
        imageView = view.findViewById(R.id.profile_image);

        ((TPOActivity) getActivity()).getSupportActionBar().setTitle("Placement Officer Profile");

        fAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference().child("Profile Images");
        rootRef = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();
        loadingBar = new ProgressDialog(getContext());

        setProfilePick();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i,1);
            }
        });

        final DocumentReference documentReference = rootRef.collection("TPOs").document(userId);

        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                name.setText(value.getString("TPOName"));
                email.setText(value.getString("TPOEmail"));
                phone.setText(value.getString("TPOPhone"));
                city.setText(value.getString("TPOCity"));
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

                final StorageReference filepath=mStorage.child(userId + ".jpg");

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

                            Task<Void> documentSnapshot1 = rootRef.collection("TPOs").document(userId)
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
        final DocumentReference docIdRef = rootRef.collection("TPOs").document(userId);
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
