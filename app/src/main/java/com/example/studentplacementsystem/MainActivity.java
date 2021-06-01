package com.example.studentplacementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    TextView fullName, email, phone;
    CircleImageView imageView;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    View headerView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    String Er;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.getHeaderView(0);
        fullName = headerView.findViewById(R.id.StudentName);
        email = headerView.findViewById(R.id.StudentEmail);
        imageView = headerView.findViewById(R.id.profile_image);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        //load default fragment

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_fragment,new MainFragment());
        fragmentTransaction.commit();

//        fAuth = FirebaseAuth.getInstance();
//        userId = fAuth.getCurrentUser().getUid();

        Er = getIntent().getStringExtra("jaydeep");
        Log.d("BAD","looK AT THIS" + Er);
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        final DocumentReference docIdRef = rootRef.collection("user").document(Er);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists() && document.getString("profileImage")!=null) {

                        String mail = document.getString("StudentEmail");
                        String name = document.getString("StudentName");
                        String profileImage = document.getString("profileImage");
                        Glide.with(MainActivity.this).load(profileImage).into(imageView);

                        fullName.setText(name);
                        email.setText(mail);
                    } else if(document.exists()){
                        String mail = document.getString("StudentEmail");
                        String name = document.getString("StudentName");

                        fullName.setText(name);
                        email.setText(mail);
                    }
                } else {
                    Log.d("BADSHAH", "Failed with: ", task.getException());
                    Toast.makeText(MainActivity.this, "Failed with : " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });


//        DocumentReference documentReference = fStore.collection("user").document(userId);
//        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                fullName.setText(value.getString("StudentName"));
//                email.setText(value.getString("StudentEmail"));
//            }
//        });


//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        if(documentSnapshot.exists())
//                        {
//                            String name = documentSnapshot.getString("StudentName");
//                            String mail = documentSnapshot.getString("StudentEmail");
//
//                            Log.d("BADSHAH","LET'S SEE" + name);
//
//                            fullName.setText("Jay");
//                            email.setText("jay@gmail.com");
//                        }
//                        else
//                        {
//                            Toast.makeText(MainActivity.this, "Something went wrong !!!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.home:

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment,new MainFragment());
                fragmentTransaction.commit();
                break;
//            case R.id.another:
//
//                fragmentManager = getSupportFragmentManager();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.container_fragment,new FragmentSecond());
//                fragmentTransaction.commit();
//                break;
            case R.id.logout:

//                fAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Modules.class));
                finish();
                break;
            case R.id.addDetail:

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment,new AddProfileFragment());
                fragmentTransaction.commit();
                break;

            case R.id.myApplication:

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment,new MyApplicationFragment());
                fragmentTransaction.commit();
                break;

            case R.id.viewCompanyForStudent:

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment,new ViewJobForStudentFragment());
                fragmentTransaction.commit();
                break;
            case R.id.feedback:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment,new FeedbackFragment());
                fragmentTransaction.commit();
                break;


//            case R.id.detail:
//
//                fragmentManager = getSupportFragmentManager();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.container_fragment,new ProfileFragment());
//                fragmentTransaction.commit();
//                break;

            case R.id.resume:

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment,new ResumeFragment());
                fragmentTransaction.commit();
                break;

                /*fullName = findViewById(R.id.profileame);
                email = findViewById(R.id.profilEmail);
                phone = findViewById(R.id.profilePhone);

                fAuth = FirebaseAuth.getInstance();
                fStore = FirebaseFirestore.getInstance();

                userId = fAuth.getCurrentUser().getUid();

                final DocumentReference documentReference = fStore.collection("users").document(userId);
                documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        phone.setText(value.getString("phone"));
                        fullName.setText(value.getString("fName"));
                        email.setText(value.getString("email"));
                    }
                }); */
        }

        return true;
    }


    public String getMyData() {
        return Er;
    }



}
