package com.winbee.successcentersikar.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.winbee.successcentersikar.LocalData;
import com.winbee.successcentersikar.MyPurchaseTopicActivity;
import com.winbee.successcentersikar.NewModels.SubjectContentArray;
import com.winbee.successcentersikar.R;


import java.util.ArrayList;

public class AllPurchaseSubjectAdapter extends RecyclerView.Adapter<AllPurchaseSubjectAdapter.ViewHolder> {
    private Context context;
    private ArrayList<SubjectContentArray> list;

    public AllPurchaseSubjectAdapter(Context context, ArrayList<SubjectContentArray> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_subject_adapter,parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //setting data toAd apter List
        holder.branchname.setText(list.get(position).getBucket_Name());
        holder.total_video.setText("Videos : "+list.get(position).getTotal_Video());
        holder.total_document.setText("Notes : "+list.get(position).getTotal_Document());
        Picasso.get().load(list.get(position).getBucket_Image())
                .placeholder(R.drawable.dummyimage)
                .into(holder.live_image);
            holder.branch_sem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LocalData.SubjectChildLink=list.get(position).getChild_Link();
                    Intent intent = new Intent(context, MyPurchaseTopicActivity.class);
                    context.startActivity(intent);
                }
            });



    }

    @Override
    public int getItemCount() {
        return list==null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView branchname,total_video,total_document;
        private RelativeLayout branch_sem;
        private ImageView live_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            branchname = itemView.findViewById(R.id.gec_branchname);
            total_video = itemView.findViewById(R.id.total_video);
            total_document = itemView.findViewById(R.id.total_document);
            branch_sem = itemView.findViewById(R.id.branch_sem);
            live_image = itemView.findViewById(R.id.live_image);
        }
    }
}


