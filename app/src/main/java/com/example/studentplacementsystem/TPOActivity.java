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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class TPOActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    TextView fullName, email, phone;
    CircleImageView imageView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    View headerView;
    FragmentTransaction fragmentTransaction;

    FirebaseAuth fAuth;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpo);
        toolbar = findViewById(R.id.toolbar_of_tpo);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_of_tpo);
        navigationView = findViewById(R.id.navigationView_of_tpo);
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.getHeaderView(0);
        fullName = headerView.findViewById(R.id.TPOName);
        email = headerView.findViewById(R.id.TPOEmail);
        imageView = headerView.findViewById(R.id.profile_image);

        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        //load default fragment

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_fragment_of_tpo,new MainFragmentOfTPO());
        fragmentTransaction.commit();


        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        final DocumentReference docIdRef = rootRef.collection("TPOs").document(userID);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists() && document.getString("profileImage")!=null) {

                        String mail = document.getString("TPOEmail");
                        String name = document.getString("TPOName");
                        String profileImage = document.getString("profileImage");
                        Glide.with(TPOActivity.this).load(profileImage).into(imageView);

                        fullName.setText(name);
                        email.setText(mail);
                    } else if(document.exists()){
                        String mail = document.getString("TPOEmail");
                        String name = document.getString("TPOName");

                        fullName.setText(name);
                        email.setText(mail);
                    }
                } else {
                    Log.d("BADSHAH", "Failed with: ", task.getException());
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);

        switch (item.getItemId()) {

            case R.id.addDetailOfTpo:

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment_of_tpo,new AddDetailOfTpoFragment());
                fragmentTransaction.commit();
                break;

            case R.id.homeOfTpo:

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment_of_tpo,new MainFragmentOfTPO());
                fragmentTransaction.commit();
                break;

//            case R.id.detailOfTpo:
//
//                fragmentManager = getSupportFragmentManager();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.container_fragment_of_tpo,new ViewDetailForTPO());
//                fragmentTransaction.commit();
//                break;

            case R.id.addCompany:

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment_of_tpo,new AddJobFragment());
                fragmentTransaction.commit();
                break;

            case R.id.logoutOfTpo:

                fAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Modules.class));
                finish();
                break;

//            case R.id.viewStudent:
//                fragmentManager = getSupportFragmentManager();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.container_fragment_of_tpo,new ViewStudentFragment());
//                fragmentTransaction.commit();
//                break;

            case R.id.viewCompany:

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment_of_tpo,new ViewJobFragment());
                fragmentTransaction.commit();
                break;
            case R.id.viewFeedbackTpo:

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment_of_tpo,new FeedbackOfTpoFragment());
                fragmentTransaction.commit();
                break;

        }

        return false;
    }

    public static class MainFragmentOfAdmin {
    }
}
