package com.example.studentplacementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ViewJobForStudentFragment extends Fragment implements JobAdapter.OnJobListener{

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private CollectionReference jobRef = fStore.collection("Companies");
    private CollectionReference ApplyRef = fStore.collection("Apply");
    private RecyclerView recyclerView;

    private JobAdapter adapter;
    private String currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private FirestoreRecyclerOptions<Job> options;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_job_for_student, container, false);

        recyclerView = view.findViewById(R.id.recycler_view2);
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
//        Toast.makeText(getContext(), "position"+position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), ApplyForJob.class);
        intent.putExtra("jobPost", adapter.getItem(position).getJobPost());
        intent.putExtra("companyName", adapter.getItem(position).getCompanyName());
        intent.putExtra("description", adapter.getItem(position).getCompanyDescription());
        intent.putExtra("workType", adapter.getItem(position).getWorkType());
        intent.putExtra("userId", adapter.getItem(position).getUser_id());
        intent.putExtra("companyId",adapter.getItem(position).getCompany_id());

        startActivity(intent);
    }

}
