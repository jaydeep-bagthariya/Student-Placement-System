package com.example.studentplacementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ViewStudentForAdminFragment extends Fragment implements StudentAdapter.OnListener {

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private CollectionReference jobRef = fStore.collection("user");
    private RecyclerView recyclerView;
    private String currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private StudentAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_student_for_admin, container, false);

        recyclerView = view.findViewById(R.id.recycler_view5);
        Log.d("DONE", "SUCCESS");
        setUpRecyclerView();

        return view;
    }
    private void setUpRecyclerView() {
        Query query = jobRef. orderBy("StudentEr");
        Log.d("DONE", "SUCCESS1");

        FirestoreRecyclerOptions<user> options = new FirestoreRecyclerOptions.Builder<user>()
                .setQuery(query, user.class)
                .build();
        Log.d("DONE", "SUCCESS2");

        adapter = new StudentAdapter(options, this);
        Log.d("DONE", "SUCCESS3");
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        Log.d("DONE", "SUCCESS4");
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
    public void OnClick(int position) {
        Intent intent = new Intent(getContext(),ApplyStudentDetail.class);
        intent.putExtra("user", adapter.getItem(position).getStudentEr());
        startActivity(intent);
    }
}
