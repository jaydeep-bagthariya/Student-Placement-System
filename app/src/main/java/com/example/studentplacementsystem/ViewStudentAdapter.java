package com.example.studentplacementsystem;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.security.AccessController.getContext;

class ViewStudentAdapter extends FirestoreRecyclerAdapter<Apply,ViewStudentAdapter.StudentHolder> {

    private OnListener mOnListener;

    public ViewStudentAdapter(@NonNull FirestoreRecyclerOptions<Apply> options, ViewStudentAdapter.OnListener mOnListener) {
        super(options);
        this.mOnListener = mOnListener;
    }

    @NonNull
    @Override
    public StudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.aaaaa, parent, false);
        return new ViewStudentAdapter.StudentHolder(v,mOnListener);
    }

    @Override
    protected void onBindViewHolder(@NonNull final StudentHolder holder, int position, @NonNull final Apply model) {

        String ID = model.getUser_id();
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        DocumentReference documentReference = fStore.collection("user").document(ID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                String name = value.getString("StudentName");

                String email = value.getString("StudentEmail");
                String er = value.getString("StudentEr");
                holder.textViewJobPost.setText(name);
//                holder.textViewCompanyName.setText("Email : " + email + "\nCGPA: " + value.get("Cgpa").toString());
                holder.textViewCompanyName.setText(er);
                if(value.getString("profileImage")!=null) {
                    String url = value.getString("profileImage");
                    Picasso.get().load(url).into(holder.ImageView);
                }
            }
        });

//        String cID = model.getCompany_id();
//        DocumentReference documentReference2 = fStore.collection("Companies").document(cID);
//        documentReference2.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                holder.textViewCompanyDescription.setText("Company Name : " + value.getString("companyName")
//                 + "\nCompany Description : " + value.getString("companyDescription"));
//                holder.textViewWorkType.setText("Job Post : " + value.getString("jobPost"));
//            }
//        });

//        holder.textViewJobPost.setText(model.getStudentName());
//        holder.textViewCompanyName.setText("Email: " + model.getStudentEmail());
//        holder.textViewCompanyDescription.setText("Company Name: " + model.getCompanyName() +
//                "\nCompany Description: " + model.getCompanyDescription());
//        holder.textViewWorkType.setText("Job Post: " + model.getJobPost());
    }

    class StudentHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textViewCompanyName;
        TextView textViewJobPost;
        TextView textViewCompanyDescription;
        TextView textViewWorkType;
        CardView cardViewItem;
        CircleImageView ImageView;

            ViewStudentAdapter.OnListener onListener;

        public StudentHolder(@NonNull View itemView, OnListener onListener) {
            super(itemView);
            ImageView = itemView.findViewById(R.id.profile_image);
//            textViewCompanyName = itemView.findViewById(R.id.text_view_companyName);
            textViewCompanyName = itemView.findViewById(R.id.profileEr);
//            textViewJobPost = itemView.findViewById(R.id.text_view_jobPost);
            textViewJobPost = itemView.findViewById(R.id.profileName);
//            textViewCompanyDescription = itemView.findViewById(R.id.text_view_description);
//            textViewWorkType = itemView.findViewById(R.id.text_view_workType);
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
