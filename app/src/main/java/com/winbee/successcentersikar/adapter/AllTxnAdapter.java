package com.winbee.successcentersikar.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.successcentersikar.NewModels.TxnModel;
import com.winbee.successcentersikar.R;

import java.util.ArrayList;

public class AllTxnAdapter extends RecyclerView.Adapter<AllTxnAdapter.ViewHolder> {
    private Context context;
    private ArrayList<TxnModel> list1;

    public AllTxnAdapter(Context context, ArrayList<TxnModel> horizontalList) {
        this.context = context;
        this.list1 = horizontalList;
    }

    @NonNull
    @Override
    public AllTxnAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.txn_adapter, parent, false);
        return new AllTxnAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllTxnAdapter.ViewHolder holder, final int position) {
        holder.text_course_name.setText(list1.get(position).getBucketName());
        holder.text_type.setText(list1.get(position).getPurchased());
        holder.text_amount.setText(Html.fromHtml(list1.get(position).getAmount_order_org()));
        holder.text_txnid.setText("Txn_id - "+Html.fromHtml(list1.get(position).getOrder_id()));
        if (list1.get(position).getPaid().equalsIgnoreCase("1")) {
            holder.text_status.setText("Success");
            holder.text_date.setText(list1.get(position).getSuccessDate());
        } else if (list1.get(position).getPaid().equalsIgnoreCase("Null")) {
            holder.text_status.setText("Pending");
            holder.text_date.setText(list1.get(position).getInitaitedDate());
        }


    }

    @Override
    public int getItemCount() {
        return list1 == null ? 0 : list1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text_course_name, text_amount, text_txnid, text_status,text_date,text_type;
        private ImageView course_image;
        RelativeLayout cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_course_name = itemView.findViewById(R.id.text_course_name);
            cardView = itemView.findViewById(R.id.branch_sem);
            course_image = itemView.findViewById(R.id.course_image);
            text_amount = itemView.findViewById(R.id.text_amount);
            text_txnid = itemView.findViewById(R.id.text_txnid);
            text_status = itemView.findViewById(R.id.text_status);
            text_date = itemView.findViewById(R.id.text_date);
            text_type = itemView.findViewById(R.id.text_type);
        }
    }
}