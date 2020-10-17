package com.winbee.successcentersikar.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.successcentersikar.NewModels.ContentSyllabu;
import com.winbee.successcentersikar.R;

import java.util.ArrayList;
import java.util.List;

public class CourseContentAdapter extends RecyclerView.Adapter<CourseContentAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ContentSyllabu> list1;

    public CourseContentAdapter(Context context, ArrayList<ContentSyllabu> horizontalList) {
        this.context = context;
        this.list1 = horizontalList;
    }

    @NonNull
    @Override
    public CourseContentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_content_adapter, parent, false);
        return new CourseContentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseContentAdapter.ViewHolder holder, final int position) {
        holder.serial_number.setText((position+1)+".");
        holder.gec_subject.setText(list1.get(position).getCourse_Item_Name());

        Log.i("adapter", "onBindViewHolder: "+list1.get(position).getCourse_Item_Name());

    }

    @Override
    public int getItemCount() {
        return list1 == null ? 0 : list1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView serial_number, gec_subject;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serial_number = itemView.findViewById(R.id.serial_number);
            gec_subject = itemView.findViewById(R.id.gec_subject);
        }
    }
}