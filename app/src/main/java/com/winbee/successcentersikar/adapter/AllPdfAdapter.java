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

import com.winbee.successcentersikar.Utils.LocalData;
import com.winbee.successcentersikar.NewModels.PdfContentArray;
import com.winbee.successcentersikar.activity.PdfWebActivity;
import com.winbee.successcentersikar.R;

import java.util.List;

public class AllPdfAdapter extends RecyclerView.Adapter<AllPdfAdapter.ViewHolder> {
    private Context context;
    private List<PdfContentArray> list;

    public AllPdfAdapter(Context context, List<PdfContentArray> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AllPdfAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pdf_adapter,parent, false);
        return  new AllPdfAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllPdfAdapter.ViewHolder holder, final int position) {
        //setting data toAd apter List
        holder.gec_subject.setText(list.get(position).getTopic());
        holder.teacher.setText(list.get(position).getFaculty());
      //  Picasso.get().load(list.get(position).getBucket_Image()).into(holder.live_image);
        holder.branch_sem_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalData.PdfUrl=list.get(position).getURL();
                Intent intent = new Intent(context, PdfWebActivity.class);
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return list==null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView gec_subject,teacher,total_document;
        private RelativeLayout branch_sem_topic;
        private ImageView live_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gec_subject = itemView.findViewById(R.id.gec_subject);
            teacher = itemView.findViewById(R.id.teacher);
            total_document = itemView.findViewById(R.id.total_document);
            branch_sem_topic = itemView.findViewById(R.id.branch_sem_topic);
            live_image = itemView.findViewById(R.id.live_image);
        }
    }
}