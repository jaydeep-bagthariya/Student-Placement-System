package com.example.studentplacementsystem;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ViewStudentFragment extends Fragment implements ViewStudentAdapter.OnListener {

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private CollectionReference jobRef = fStore.collection("Apply");
    private RecyclerView recyclerView;
    private String currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private ViewStudentAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_student, container, false);

        recyclerView = view.findViewById(R.id.recycler_view4);
        setUpRecyclerView();

        return view;
    }

    private void setUpRecyclerView() {
        Query query = jobRef.whereEqualTo("tpo_id",currentUserID). orderBy("companyName");

        FirestoreRecyclerOptions<Apply> options = new FirestoreRecyclerOptions.Builder<Apply>()
                .setQuery(query, Apply.class)
                .build();

        adapter = new ViewStudentAdapter(options, this);
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
    public void ONClick(int position) {

    }
}
