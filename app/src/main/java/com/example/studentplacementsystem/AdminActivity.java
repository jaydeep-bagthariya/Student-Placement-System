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
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        toolbar = findViewById(R.id.toolbar_of_admin);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_of_admin);
        navigationView = findViewById(R.id.navigationView_of_admin);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        //load default fragment

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_fragment_of_admin,new MainFragmentOfAdmin());
        fragmentTransaction.commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
//            case R.id.home:
//
//                fragmentManager = getSupportFragmentManager();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.container_fragment, new MainFragment());
//                fragmentTransaction.commit();
//                break;
            case R.id.homeForAdmin:

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment_of_admin, new MainFragmentOfAdmin());
                fragmentTransaction.commit();
                break;

            case R.id.viewStudentsForAdmin:

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment_of_admin, new ViewStudentForAdminFragment());
                fragmentTransaction.commit();
                break;
            case R.id.viewFeedback:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment_of_admin, new viewFeedbackFragment());
                fragmentTransaction.commit();
                break;
            case R.id.viewTpoForAdmin:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment_of_admin, new viewTpo());
                fragmentTransaction.commit();
                break;
            case R.id.logoutOfAdmin:

                fAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Modules.class));
                finish();
                break;
        }

        return false;
    }
}
