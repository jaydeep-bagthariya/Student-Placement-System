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

public class ApplyAdapter extends FirestoreRecyclerAdapter<Apply,ApplyAdapter.ApplyHolder> {

    private OnApplyListener mOnApplyListener;

    public ApplyAdapter(@NonNull FirestoreRecyclerOptions<Apply> options, OnApplyListener mOnApplyListener) {
        super(options);
        this.mOnApplyListener = mOnApplyListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull ApplyHolder holder, int position, @NonNull Apply model) {
        holder.textViewJobPost.setText(model.getJobPost());
        holder.textViewCompanyName.setText("Company Name: " + model.getCompanyName());
        holder.textViewCompanyDescription.setText("Description: " + model.getCompanyDescription());
        holder.textViewWorkType.setText("Working Type: " + model.getWorkType());

    }

    @NonNull
    @Override
    public ApplyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_item, parent, false);
        return new ApplyAdapter.ApplyHolder(v,mOnApplyListener);
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
