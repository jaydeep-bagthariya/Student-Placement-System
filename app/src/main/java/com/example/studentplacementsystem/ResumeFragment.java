package com.example.studentplacementsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

public class ResumeFragment extends Fragment {

    private Button upload, select;
   // private Text fileName;

    private StorageReference storageReference;
    //private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resume, container, false);

        upload = view.findViewById(R.id.rUpload);
        select = view.findViewById(R.id.rSelect);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Upload Resume");
        //fileName = view.findViewById(R.id.fileName);

        storageReference = FirebaseStorage.getInstance().getReference();
        //databaseReference = FirebaseDatabase.getInstance().getReference("uploadResume");

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectResume();
            }
        });

        return view;
    }

    private void selectResume() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"RESUME"),12);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 12 && data != null && data.getData() != null) {
           // fileName.setTextContent(data.getDataString().substring(data.getDataString().lastIndexOf("/")+1));

            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    uploadResumeInFirebase(data.getData());
                }
            });
        }
    }

    private void uploadResumeInFirebase(Uri data) {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("File is Uploading...");
        progressDialog.show();

        StorageReference reference = storageReference.child("upload"+System.currentTimeMillis());

        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isComplete());
                        Uri uri = uriTask. getResult();

                   //     uploadResume uploadResume = new uploadResume(fileName.getTextContent().toString(), uri.toString());
                   //     databaseReference.child(databaseReference.push().getKey()).setValue(uploadResume);
                        Toast.makeText(getActivity(), "File Upload", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0* taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setMessage("File Uploaded..." +(int) progress+"%");
            }
        });
    }
}
