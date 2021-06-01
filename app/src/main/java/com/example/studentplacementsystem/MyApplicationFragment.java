package com.example.studentplacementsystem;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class MyApplicationFragment extends Fragment implements ApplyAdapter.OnApplyListener {

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private CollectionReference jobRef = fStore.collection("Apply");
    private RecyclerView recyclerView;
    String currentUserID;

    private ApplyAdapter adapter;
    private FirestoreRecyclerOptions<Apply> options;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_application, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("My Application");

        recyclerView = view.findViewById(R.id.recycler_view3);
        MainActivity activity = (MainActivity) getActivity();
        currentUserID = activity.getMyData();
        setUpRecyclerView();


        return view;
    }

    private void setUpRecyclerView() {
//        Query query = jobRef.orderBy("time", Query.Direction.DESCENDING)
//                .orderBy("date", Query.Direction.DESCENDING);

        Query query = jobRef.whereEqualTo("user_id",currentUserID);


//        jobRef.whereEqualTo("user_id",currentUserID).get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        if(!queryDocumentSnapshots.isEmpty())
//                        {
//                            String string = queryDocumentSnapshots.getString
//                            Log.d("BULB","success");
//                        }
//
//                    }
//                });


//        Query query1 = fStore.collectionGroup("Apply").whereEqualTo("user_id",currentUserID).limit(2);
        Log.d("BULB",currentUserID);
        FirestoreRecyclerOptions<Apply> options = new FirestoreRecyclerOptions.Builder<Apply>()
                .setQuery(query, Apply.class)
                .build();

        adapter = new ApplyAdapter(options, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteItem(viewHolder.getAdapterPosition());
                Toast.makeText(getContext(), "Deleted !", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
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
    public void onApplyClick(int position) {

    }
}
