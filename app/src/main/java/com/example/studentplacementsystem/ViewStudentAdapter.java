package com.example.studentplacementsystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

class ViewStudentAdapter extends FirestoreRecyclerAdapter<Apply,ViewStudentAdapter.StudentHolder> {

    private OnListener mOnListener;

    public ViewStudentAdapter(@NonNull FirestoreRecyclerOptions<Apply> options, ViewStudentAdapter.OnListener mOnListener) {
        super(options);
        this.mOnListener = mOnListener;
    }

    @NonNull
    @Override
    public StudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_item, parent, false);
        return new ViewStudentAdapter.StudentHolder(v,mOnListener);
    }

    @Override
    protected void onBindViewHolder(@NonNull StudentHolder holder, int position, @NonNull Apply model) {
        holder.textViewJobPost.setText(model.getStudentName());
        holder.textViewCompanyName.setText("Email: " + model.getStudentEmail());
        holder.textViewCompanyDescription.setText("Company Name: " + model.getCompanyName());
        holder.textViewWorkType.setText("Job Post: " + model.getJobPost());
    }

    class StudentHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textViewCompanyName;
        TextView textViewJobPost;
        TextView textViewCompanyDescription;
        TextView textViewWorkType;
        CardView cardViewItem;

        ViewStudentAdapter.OnListener onListener;

        public StudentHolder(@NonNull View itemView, OnListener onListener) {
            super(itemView);

            textViewCompanyName = itemView.findViewById(R.id.text_view_companyName);
            textViewJobPost = itemView.findViewById(R.id.text_view_jobPost);
            textViewCompanyDescription = itemView.findViewById(R.id.text_view_description);
            textViewWorkType = itemView.findViewById(R.id.text_view_workType);
            cardViewItem = itemView.findViewById(R.id.item_view);
            this.onListener = onListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onListener.ONClick(getAdapterPosition());
        }
    }

    public interface OnListener {
        void ONClick(int position);
    }

}
