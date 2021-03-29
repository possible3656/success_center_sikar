package com.winbee.successcentersikar.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.successcentersikar.R;
import com.winbee.successcentersikar.Utils.OnlineTestData;
import com.winbee.successcentersikar.activity.InstructionsActivity;
import com.winbee.successcentersikar.activity.TestRankActivity;
import com.winbee.successcentersikar.activity.TestSolutionActivity;
import com.winbee.successcentersikar.model.SIACDetailsDataModel;

import java.util.List;

public class SubscriptionTestListAdapter extends RecyclerView.Adapter<SubscriptionTestListAdapter.CustomViewHolder> {
    private Context context;
    private List<SIACDetailsDataModel> siacDetailsDataModelList;
    private View view;

    public SubscriptionTestListAdapter(Context context, List<SIACDetailsDataModel> siacDetailsDataModelList) {
        this.context=context;
        this.siacDetailsDataModelList = siacDetailsDataModelList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_test_list,parent,false);
        return new CustomViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewHolder, int position) {
        final SIACDetailsDataModel siacDetailsDataModel = siacDetailsDataModelList.get(position);
//        if (siacDetailsDataModel.getIsPremium_decode().equalsIgnoreCase("Free")) {
//          viewHolder.img_lock.setVisibility(View.GONE);
//          viewHolder.img_Unlock.setVisibility(View.VISIBLE);
//        }else{
//            viewHolder.img_lock.setVisibility(View.VISIBLE);
//            viewHolder.img_Unlock.setVisibility(View.GONE);
//        }
        viewHolder.online_testname.setText(siacDetailsDataModel.getPaperName());

        if (siacDetailsDataModel.getIsPremium_decode().equalsIgnoreCase("Free")) {
            viewHolder.img_lock.setVisibility(View.GONE);
            viewHolder.img_Unlock.setVisibility(View.VISIBLE);
            viewHolder.layout_test_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OnlineTestData.bucketID = siacDetailsDataModel.getBucketID();
                    OnlineTestData.paperID = siacDetailsDataModel.getPaperID();
                    OnlineTestData.paperName = siacDetailsDataModel.getPaperName();
                    OnlineTestData.paperSection_Encode = siacDetailsDataModel.getPaperSection_Encode();
                    OnlineTestData.isNegativeMarking_encode = siacDetailsDataModel.getIsNegativeMarking_encode();
                    OnlineTestData.time = siacDetailsDataModel.getTime();
                    OnlineTestData.isOpen = siacDetailsDataModel.getIsOpen();
                    OnlineTestData.openDate = siacDetailsDataModel.getOpenDate();
                    OnlineTestData.isNegativeMarking_decode = siacDetailsDataModel.getIsNegativeMarking_decode();
                    OnlineTestData.isPremium_encode = siacDetailsDataModel.getIsPremium_encode();
                    OnlineTestData.isPremium_decode = siacDetailsDataModel.getIsPremium_decode();
                    OnlineTestData.description = siacDetailsDataModel.getDescription();
                    Intent intent = new Intent(context, InstructionsActivity.class);
                    context.startActivity(intent);

                }
            });

        }else{
            viewHolder.img_lock.setVisibility(View.VISIBLE);
            viewHolder.img_Unlock.setVisibility(View.GONE);
        }
    }
    @Override
    public int getItemCount() {
        return siacDetailsDataModelList.size();
    }
    static class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView live_image,img_lock,img_Unlock;
        TextView online_testname;
        RelativeLayout layout_test_list;
        CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            live_image=itemView.findViewById(R.id.live_image);
            online_testname=itemView.findViewById(R.id.online_testname);
            img_lock=itemView.findViewById(R.id.img_lock);
            img_Unlock=itemView.findViewById(R.id.img_Unlock);
            layout_test_list=itemView.findViewById(R.id.layout_test_list);
        }
    }
}
