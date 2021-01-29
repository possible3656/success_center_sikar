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
import com.winbee.successcentersikar.Utils.LocalData;
import com.winbee.successcentersikar.NewModels.TopicContentArray;
import com.winbee.successcentersikar.activity.PdfWebActivity;
import com.winbee.successcentersikar.R;
import com.winbee.successcentersikar.activity.YoutubeLibaray;

import java.util.List;


public class AllTopicAdapter extends RecyclerView.Adapter<AllTopicAdapter.ViewHolder> {
    private Context context;
    private List<TopicContentArray> list;

    public AllTopicAdapter(Context context, List<TopicContentArray> list){
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
        if (list.get(position).getAccessType().equalsIgnoreCase("2")) {
            holder.img_lock.setVisibility(View.VISIBLE);
            holder.img_Unlock.setVisibility(View.GONE);
        } else if (list.get(position).getAccessType().equalsIgnoreCase("1")) {
            holder.img_lock.setVisibility(View.GONE);
            holder.img_Unlock.setVisibility(View.GONE);
        }


        holder.subject.setText(list.get(position).getSubject());
        holder.teacher.setText(list.get(position).getFaculty());
        holder.txt_date.setText(list.get(position).getPublished());
        Picasso.get().load(list.get(position).getThumbnail()).placeholder(R.drawable.dummyimage).fit().into(holder.document_type);


        if (list.get(position).getType().equalsIgnoreCase("YouTube")){
            holder.document_type_image.setImageResource(R.drawable.ic_clapperboard);
            holder.branch_sem_topic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LocalData.VideoUrl=list.get(position).getURL();
                    LocalData.VideoUrl=list.get(position).getURL();
                    LocalData.SubjectName=list.get(position).getSubject();
                    LocalData.TopicName=list.get(position).getTopic();
                    LocalData.TeacherName=list.get(position).getFaculty();
                    LocalData.TopicDocumentId=list.get(position).getDocumentId();
                    Intent intent = new Intent(context, YoutubeLibaray.class);
                    context.startActivity(intent);
                }
            });

        }else if (list.get(position).getType().equalsIgnoreCase("Vimeo"))
        {
            holder.document_type_image.setImageResource(R.drawable.ic_clapperboard);
            holder.branch_sem_topic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LocalData.VideoUrl=list.get(position).getURL();
                    LocalData.SubjectName=list.get(position).getSubject();
                    LocalData.TopicName=list.get(position).getTopic();
                    LocalData.TeacherName=list.get(position).getFaculty();
                    LocalData.TopicDocumentId=list.get(position).getDocumentId();
                    Intent intent = new Intent(context, YoutubeLibaray.class);
                    context.startActivity(intent);
                }
            });
        }
        else if (list.get(position).getType().equalsIgnoreCase("Pdf")){
            holder.document_type_image.setImageResource(R.drawable.ic_pdf);
            holder.branch_sem_topic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LocalData.PdfUrl=list.get(position).getURL();
                    LocalData.TopicDocumentId=list.get(position).getDocumentId();
                    Intent intent = new Intent(context, PdfWebActivity.class);
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
        private TextView subject, teacher, txt_date, document_type_text;
        ImageView img_lock, img_Unlock,txt_video,txt_pdf,document_type_image,document_type;
        private RelativeLayout branch_sem_topic;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.gec_subject);
            img_lock = itemView.findViewById(R.id.img_lock);
            img_Unlock = itemView.findViewById(R.id.img_Unlock);
            teacher = itemView.findViewById(R.id.teacher);
            txt_video = itemView.findViewById(R.id.txt_video);
            branch_sem_topic = itemView.findViewById(R.id.branch_sem_topic);
            txt_pdf = itemView.findViewById(R.id.txt_pdf);
            txt_date = itemView.findViewById(R.id.txt_date);
            document_type_image = itemView.findViewById(R.id.document_type_image);
            document_type = itemView.findViewById(R.id.document_type);
        }

    }
}



