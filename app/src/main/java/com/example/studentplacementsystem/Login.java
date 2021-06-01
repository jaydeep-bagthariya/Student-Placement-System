package com.example.studentplacementsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {

    EditText mEmail, mPassword;
    Button mloginBtn;
    TextView mCreateBtn, forgotTextLink;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mloginBtn = findViewById(R.id.loginBtn);
        mCreateBtn = findViewById(R.id.createText);
        progressBar = findViewById(R.id.progressBar);
        forgotTextLink = findViewById(R.id.forgotPassword);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        mloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is required");
                    return;
                }
                if (password.length() < 6) {
                    mPassword.setError("Password Must Be Atleast 6 Characters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                //Authenticate the user

                FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                final DocumentReference docIdRef = rootRef.collection("user").document(email);
                docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("BADSHAH", "Document exists!" + email + password);
                                String mail = document.getString("StudentEr");
                                String pass = document.getString("Password");
                                Log.d("BADSHAH", "Document exists!" + mail + pass);
                                if(email.equals(mail) && password.equals(pass)) {
                                    progressBar.setVisibility(View.GONE);

                                    Toast.makeText(Login.this, "Successfully Login", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    intent.putExtra("jaydeep",email);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                                else
                                {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(Login.this, "Invalid User !!!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Log.d("BADSHAH", "Document does not exist!");
                            }
                        } else {
                            Log.d("BADSHAH", "Failed with: ", task.getException());
                        }
                    }
                });

//                userId = fAuth.getCurrentUser().getUid();
//                Log.d("BADSHAH","LET'S SEE" + email);
//                DocumentReference documentReference = fStore.collection("users").document(userId);
//                documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                        String Er = value.getString("StudentEr");
//                        String pass = value.getString("Password");
//
//                        if(mEmail.equals(Er) && mPassword.equals(pass)) {
//
//                            progressBar.setVisibility(View.GONE);
//
//                            Toast.makeText(Login.this, "Successfully Login", Toast.LENGTH_SHORT).show();
//
//                            Intent intent = new Intent(Login.this, MainActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);;
//
//                        }
//                        else {
//                            Toast.makeText(Login.this, "Invalid User !!!", Toast.LENGTH_SHORT).show();
//                            progressBar.setVisibility(View.GONE);
//                        }
//                    }
//                });

//                fAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                    @Override
//                    public void onSuccess(AuthResult authResult) {
//                        progressBar.setVisibility(View.GONE);
//                        checkStudent(authResult.getUser().getUid());
////                        Toast.makeText(Login.this, "Successfully Login", Toast.LENGTH_SHORT).show();
//
////                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
////                        Intent intent = new Intent(Login.this, MainActivity.class);
////                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
////                        startActivity(intent);
//                    }
//
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(Login.this, "Error !" + e.getMessage(), Toast.LENGTH_SHORT).show();
//                        progressBar.setVisibility(View.GONE);
//                    }
//                });
            }

        //        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        //            @Override
        //            public void onComplete(@NonNull Task<AuthResult> task) {
        //                if(task.isSuccessful()) {
        //                    progressBar.setVisibility(View.GONE);
        //                    Toast.makeText(Login.this, "Successfully Login", Toast.LENGTH_SHORT).show();
        //                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
        //                } else {
        //                    Toast.makeText(Login.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
        //                    progressBar.setVisibility(View.GONE);
        //                }
        //            }
        //        });
        //    }
        });

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });
//
//        forgotTextLink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                final EditText resetMail = new EditText((view.getContext()));
//                AlertDialog.Builder passowordResetDialog = new AlertDialog.Builder(view.getContext());
//                passowordResetDialog.setTitle("Reset Password ?");
//                passowordResetDialog.setMessage("Enter Your Email To Received Reset Link.");
//                passowordResetDialog.setView(resetMail);
//
//                passowordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        //extract the email and send reset link
//
//                        String mail = resetMail.getText().toString();
//
//                        if(TextUtils.isEmpty(mail)){
//                            Toast.makeText(Login.this, "Email Id is required!", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(Login.this, "Reset Link Sent To Your Email", Toast.LENGTH_SHORT).show();
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(Login.this, "Error ! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        //close the dialog
//                    }
//                });
//                passowordResetDialog.create().show();
//            }
//        });
    }

//    private void checkStudent(String uid) {
//        DocumentReference df = fStore.collection("users").document(uid);

        //extract the data from the document
//        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if(documentSnapshot.getString("isStudent")!=null) {
//                    Intent intent = new Intent(Login.this, MainActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);;
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(Login.this, "Error :"+ e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

//        df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if(task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if(document.exists()) {
//                        if(document.get("isStudent")!=null) {
//
//                            Log.d("JAYD","SUCCESS");
//
//                            Toast.makeText(Login.this, "Successfully Login", Toast.LENGTH_SHORT).show();
//
//                            Intent intent = new Intent(Login.this, MainActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);;
//                        }
//                        else {
//                            Log.d("JAYD","SUCCESS");
//                        }
//
//                    }
//                    else {
//                        Toast.makeText(Login.this, "User Does Not Exist", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//                else {
//                    Toast.makeText(Login.this, "Error :" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(Login.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}