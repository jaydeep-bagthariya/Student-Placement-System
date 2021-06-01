package com.example.studentplacementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ApplyStudentList extends AppCompatActivity implements ViewStudentAdapter.OnListener{

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private CollectionReference jobRef = fStore.collection("Apply");
    private RecyclerView recyclerView;
    private String currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private ViewStudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_student_list);

        getSupportActionBar().setTitle("Students who have applied");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String companyID = getIntent().getStringExtra("companyID");
        Log.d("JAY","SUCCESS " + companyID);

        recyclerView = findViewById(R.id.recycler_view6);
        setUpRecyclerView(companyID);
    }

    private void setUpRecyclerView(String companyID) {

        Query query = jobRef.whereEqualTo("company_id",companyID).orderBy("Cgpa", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Apply> options = new FirestoreRecyclerOptions.Builder<Apply>()
                .setQuery(query, Apply.class)
                .build();
        Log.d("JAY","SUCCESS " + companyID);
        adapter = new ViewStudentAdapter(options, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void ONClick(int position) {
        Intent intent = new Intent(this,ApplyStudentDetail.class);
        intent.putExtra("user", adapter.getItem(position).getUser_id());
        startActivity(intent);
    }
}
