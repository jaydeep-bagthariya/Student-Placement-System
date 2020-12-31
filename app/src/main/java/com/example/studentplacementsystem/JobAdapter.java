package com.example.studentplacementsystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class JobAdapter extends FirestoreRecyclerAdapter<Job,JobAdapter.JobHolder> {

    private OnJobListener mOnJobListener;


    public JobAdapter(@NonNull FirestoreRecyclerOptions<Job> options, OnJobListener mOnJobListener) {
        super(options);
        this.mOnJobListener = mOnJobListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull JobHolder holder, int position, @NonNull Job model) {
        holder.textViewJobPost.setText(model.getJobPost());
        holder.textViewCompanyName.setText("Company Name: " + model.getCompanyName());
        holder.textViewCompanyDescription.setText("Description: " + model.getCompanyDescription());
        holder.textViewWorkType.setText("Working Type: " + model.getWorkType());

    }

    @NonNull
    @Override
    public JobHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_item, parent, false);
        return new JobHolder(v,mOnJobListener);
    }

    class JobHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewCompanyName;
        TextView textViewJobPost;
        TextView textViewCompanyDescription;
        TextView textViewWorkType;
        CardView cardViewItem;

        OnJobListener onJobListener;

        public JobHolder(@NonNull View itemView, OnJobListener onJobListener) {
            super(itemView);
            textViewCompanyName = itemView.findViewById(R.id.text_view_companyName);
            textViewJobPost = itemView.findViewById(R.id.text_view_jobPost);
            textViewCompanyDescription = itemView.findViewById(R.id.text_view_description);
            textViewWorkType = itemView.findViewById(R.id.text_view_workType);
            cardViewItem = itemView.findViewById(R.id.item_view);
            this.onJobListener = onJobListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onJobListener.onJobClick(getAdapterPosition());
        }
    }

    public interface OnJobListener {
        void onJobClick(int position);
    }
}
