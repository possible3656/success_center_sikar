package com.winbee.successcentersikar.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.successcentersikar.Utils.LocalData;
import com.winbee.successcentersikar.activity.PdfWebActivity;
import com.winbee.successcentersikar.R;
import com.winbee.successcentersikar.model.NotesModel;

import java.util.ArrayList;

public class AllNotesAdapter extends RecyclerView.Adapter<AllNotesAdapter.ViewHolder> {
    private Context context;
    private ArrayList<NotesModel> list;

    public AllNotesAdapter(Context context, ArrayList<NotesModel> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.study_material_adapter,parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.gec_branchname.setText(list.get(position).getNotes_Topic());
        holder.branch_sem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalData.NotesData=list.get(position).getNotes_data();
                Bundle bundle = new Bundle();
                Intent intent = new Intent(context, PdfWebActivity.class);
                bundle.putSerializable("Notes_data",list.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list==null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView gec_branchname;
        private RelativeLayout branch_sem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gec_branchname = itemView.findViewById(R.id.gec_branchname);
            branch_sem = itemView.findViewById(R.id.branch_sem);
        }
    }
}

