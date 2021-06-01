package com.example.studentplacementsystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ApplyAdapter extends FirestoreRecyclerAdapter<Apply,ApplyAdapter.ApplyHolder> {

    private OnApplyListener mOnApplyListener;

    public ApplyAdapter(@NonNull FirestoreRecyclerOptions<Apply> options, OnApplyListener mOnApplyListener) {
        super(options);
        this.mOnApplyListener = mOnApplyListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull final ApplyHolder holder, int position, @NonNull final Apply model) {
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        String cID = model.getCompany_id();
        DocumentReference documentReference2 = fStore.collection("Companies").document(cID);
        documentReference2.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                holder.textViewJobPost.setText(value.getString("jobPost"));
                holder.textViewCompanyName.setText(value.getString("companyName"));
                holder.textViewCompanyDescription.setText(value.getString("companyDescription"));
                holder.textViewWorkType.setText(value.getString("workType"));
            }
        });

//        holder.textViewJobPost.setText(model.getJobPost());
//        holder.textViewCompanyName.setText(model.getCompanyName());
//        holder.textViewCompanyDescription.setText(model.getCompanyDescription());
//        holder.textViewWorkType.setText(model.getWorkType());

    }

    @NonNull
    @Override
    public ApplyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_item, parent, false);
        return new ApplyAdapter.ApplyHolder(v,mOnApplyListener);
    }

    public void deleteItem(int position)
    {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class ApplyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textViewCompanyName;
        TextView textViewJobPost;
        TextView textViewCompanyDescription;
        TextView textViewWorkType;
        CardView cardViewItem;

        OnApplyListener onApplyListener;

        public ApplyHolder(@NonNull View itemView, OnApplyListener onApplyListener) {
            super(itemView);

            textViewCompanyName = itemView.findViewById(R.id.text_view_companyName);
            textViewJobPost = itemView.findViewById(R.id.text_view_jobPost);
            textViewCompanyDescription = itemView.findViewById(R.id.text_view_description);
            textViewWorkType = itemView.findViewById(R.id.text_view_workType);
            cardViewItem = itemView.findViewById(R.id.item_view);
            this.onApplyListener = onApplyListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onApplyListener.onApplyClick(getAdapterPosition());
        }
    }

    public interface OnApplyListener {
        void onApplyClick(int position);
    }
}
