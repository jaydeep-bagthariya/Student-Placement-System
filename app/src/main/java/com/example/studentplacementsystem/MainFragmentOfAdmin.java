package com.example.studentplacementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainFragmentOfAdmin extends Fragment implements JobAdapter.OnJobListener{

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private CollectionReference jobRef = fStore.collection("Companies");
    private CollectionReference ApplyRef = fStore.collection("Apply");
    private RecyclerView recyclerView;

    private JobAdapter adapter;
    private FirestoreRecyclerOptions<Job> options;
    private String Er_num;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_of_admin,container, false);

        recyclerView = view.findViewById(R.id.recycler_view7);
        setUpRecyclerView();

        return view;
    }

    private void setUpRecyclerView() {
        Query query = jobRef.orderBy("time", Query.Direction.DESCENDING)
                .orderBy("date", Query.Direction.DESCENDING);


        FirestoreRecyclerOptions<Job> options = new FirestoreRecyclerOptions.Builder<Job>()
                .setQuery(query, Job.class)
                .build();

        adapter = new JobAdapter(options, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
    public void onJobClick(int position) {
        Intent intent = new Intent(getContext(), ApplyStudentList.class);
        intent.putExtra("companyID", adapter.getItem(position).getCompany_id());
        startActivity(intent);

    }
}
