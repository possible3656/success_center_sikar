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


import com.winbee.successcentersikar.LocalData;
import com.winbee.successcentersikar.NewModels.PdfContentArray;
import com.winbee.successcentersikar.NewModels.TopicContentArray;
import com.winbee.successcentersikar.ProgressBarUtil;
import com.winbee.successcentersikar.R;
import com.winbee.successcentersikar.StudyMaterial;
import com.winbee.successcentersikar.VimeoActivity;
import com.winbee.successcentersikar.YoutubeLibaray;

import java.util.ArrayList;
import java.util.List;


public class AllPurchaseTopicAdapter extends RecyclerView.Adapter<AllPurchaseTopicAdapter.ViewHolder> {
    private Context context;
    private List<TopicContentArray> list;

    public AllPurchaseTopicAdapter(Context context, List<TopicContentArray> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_adapter,parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
            holder.img_lock.setVisibility(View.GONE);
            holder.img_Unlock.setVisibility(View.GONE);
            holder.subject.setText(list.get(position).getSubject());
            holder.teacher.setText(list.get(position).getFaculty());
            if (list.get(position).getType().equalsIgnoreCase("YouTube")){
                holder.txt_video.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LocalData.VideoUrl=list.get(position).getURL();
                        LocalData.TopicDocumentId=list.get(position).getDocumentId();
                        LocalData.SubjectName=list.get(position).getSubject();
                        LocalData.TopicName=list.get(position).getTopic();
                        LocalData.TeacherName=list.get(position).getFaculty();
                        Intent intent = new Intent(context, YoutubeLibaray.class);
                        context.startActivity(intent);

                    }
                });
                holder.txt_pdf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LocalData.TopicDocumentId=list.get(position).getDocumentId();
                        Intent intent = new Intent(context, StudyMaterial.class);
                        context.startActivity(intent);
                    }
                });
            }else if (list.get(position).getType().equalsIgnoreCase("Vimeo")){
                holder.txt_video.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LocalData.VideoUrl=list.get(position).getURL();
                        LocalData.TopicDocumentId=list.get(position).getDocumentId();
                        LocalData.SubjectName=list.get(position).getSubject();
                        LocalData.TopicName=list.get(position).getTopic();
                        LocalData.TeacherName=list.get(position).getFaculty();
                        Intent intent = new Intent(context, VimeoActivity.class);
                        context.startActivity(intent);
                    }
                });


                holder.branch_sem_topic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LocalData.VideoUrl=list.get(position).getURL();
                        LocalData.TopicDocumentId=list.get(position).getDocumentId();
                        LocalData.SubjectName=list.get(position).getSubject();
                        LocalData.TopicName=list.get(position).getTopic();
                        LocalData.TeacherName=list.get(position).getFaculty();
                        Intent intent = new Intent(context, VimeoActivity.class);
                        context.startActivity(intent);
                    }
                });

                holder.txt_pdf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LocalData.TopicDocumentId=list.get(position).getDocumentId();
                        Intent intent = new Intent(context, StudyMaterial.class);
                        context.startActivity(intent);
                    }
                });
        }
    }



    @Override
    public int getItemCount() {
        return list==null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView subject, teacher, released_date, document_type_text;
        ImageView img_lock, img_Unlock,txt_video, txt_pdf;
        private RelativeLayout branch_sem_topic, layout_onclick;
        private ProgressBarUtil progressBarUtil;
        private ArrayList<PdfContentArray> list;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.gec_subject);
            img_lock = itemView.findViewById(R.id.img_lock);
            img_Unlock = itemView.findViewById(R.id.img_Unlock);
            teacher = itemView.findViewById(R.id.teacher);
            txt_video = itemView.findViewById(R.id.txt_video);
            branch_sem_topic = itemView.findViewById(R.id.branch_sem_topic);
            txt_pdf = itemView.findViewById(R.id.txt_pdf);
        }

    }
}



