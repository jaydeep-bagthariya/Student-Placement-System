package com.example.studentplacementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ViewJobFragment extends Fragment implements JobAdapter.OnJobListener {

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private CollectionReference jobRef = fStore.collection("Companies");
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String currentUser;
    private RecyclerView recyclerView;

    private JobAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_job, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        currentUser = fAuth.getCurrentUser().getUid();
        setUpRecyclerView();

        ((TPOActivity) getActivity()).getSupportActionBar().setTitle("View Companies");

        return view;
    }

    private void setUpRecyclerView() {
        Query query = jobRef.orderBy("companyName", Query.Direction.DESCENDING);
        Query query1 = jobRef.whereEqualTo("user_id",currentUser).orderBy("time", Query.Direction.DESCENDING)
                .orderBy("date", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Job> options = new FirestoreRecyclerOptions.Builder<Job>()
                .setQuery(query1, Job.class)
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
//        String s = "jaydeep";
//        Job job = adapter.getItem(position);
//        Intent intent = new Intent(getContext(), ApplyForJob.class);
//        intent.putExtra("value", s);
//
        startActivity(intent);
    }
}
