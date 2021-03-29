package com.winbee.successcentersikar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.balsikandar.crashreporter.CrashReporter;
import com.winbee.successcentersikar.R;
import com.winbee.successcentersikar.RetrofitApiCall.OnlineTestApiClient;
import com.winbee.successcentersikar.Utils.LocalData;
import com.winbee.successcentersikar.Utils.OnlineTestData;
import com.winbee.successcentersikar.Utils.SharedPrefManager;
import com.winbee.successcentersikar.WebApi.ClientApi;
import com.winbee.successcentersikar.adapter.DemoTestListAdapter;
import com.winbee.successcentersikar.model.SIACDetailsDataModel;
import com.winbee.successcentersikar.model.SIACDetailsMainModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.supercharge.shimmerlayout.ShimmerLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DemoOnlineTestActivity extends AppCompatActivity {

    private ShimmerLayout shimmerLayout;
    private RecyclerView recycle_test;
    private Toast toast_msg;
    ImageView WebsiteHome,img_share,img_noti;
    String UserId;
    String Referal_code;
    LinearLayout layout_user,layout_test_series,layout_home,layout_doubt,layout_notification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_online_test);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        Referal_code=SharedPrefManager.getInstance(this).refCode().getREFERRAL_CODE();
        try{
            WebsiteHome=findViewById(R.id.WebsiteHome);
            img_noti=findViewById(R.id.img_noti);
            UserId= SharedPrefManager.getInstance(this).refCode().getUserId();
            WebsiteHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DemoOnlineTestActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
            img_noti.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DemoOnlineTestActivity.this,NotificationActivity.class);
                    startActivity(intent);
                }
            });
            img_share=findViewById(R.id.img_share);
            img_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Success Center Sikar");
                        String shareMessage= "\nSuccess Center Sikar download the application.\n ";
                        String referalMessage= "\nMy Referal Code is .\n "+Referal_code;
                        shareMessage = shareMessage + "\nhttps://play.google.com/store/apps/details?id="+getPackageName() ;
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage+referalMessage);
                        startActivity(Intent.createChooser(shareIntent, "choose one"));
                    } catch(Exception e) {
                    }
                }
            });
            layout_user=findViewById(R.id.layout_user);
            layout_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent profile = new Intent(DemoOnlineTestActivity.this,DashboardCourseActivity.class);
                    startActivity(profile);
                }
            });
            layout_test_series=findViewById(R.id.layout_test_series);
            layout_test_series.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent test = new Intent(DemoOnlineTestActivity.this, SubjectActivity.class);
                    startActivity(test);
                    // Toast.makeText(MainActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
                }
            });
            layout_home=findViewById(R.id.layout_home);
            layout_home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent profile = new Intent(DemoOnlineTestActivity.this,MainActivity.class);
                    startActivity(profile);
                }
            });
            layout_doubt=findViewById(R.id.layout_doubt);
            layout_doubt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent profile = new Intent(DemoOnlineTestActivity.this,DiscussionActivity.class);
                    startActivity(profile);
                }
            });
            layout_notification=findViewById(R.id.layout_notification);
            layout_notification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent profile = new Intent(DemoOnlineTestActivity.this, ReferalActivity.class);
                    startActivity(profile);
                }
            });
            iniIDs();
            getTestList();

        }catch (Exception e){
            //CrashReporter.logException(e);
        }

    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
    private void iniIDs(){
        shimmerLayout=findViewById(R.id.shimmerLayout);
        recycle_test=findViewById(R.id.recycle_test);
    }
    private void getTestList() {
        apiCall();
        ClientApi apiClient= OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<SIACDetailsMainModel> call=apiClient.fetchSIACDetails(OnlineTestData.org_code,OnlineTestData.auth_code, LocalData.TestBuckedId,UserId);
        call.enqueue(new Callback<SIACDetailsMainModel>() {
            @Override
            public void onResponse(Call<SIACDetailsMainModel> call, Response<SIACDetailsMainModel> response) {
                apiCalled();
                SIACDetailsMainModel siacDetailsMainModel=response.body();
                if(siacDetailsMainModel!=null){
                    if (siacDetailsMainModel.getMessage().equalsIgnoreCase("true")){
                        List<SIACDetailsDataModel> siacDetailsDataModelList=new ArrayList<>(Arrays.asList(siacDetailsMainModel.getData()));
                        DemoTestListAdapter testListAdapter=new DemoTestListAdapter(DemoOnlineTestActivity.this,siacDetailsDataModelList);
                        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(DemoOnlineTestActivity.this, LinearLayoutManager.VERTICAL, false);
                        recycle_test.setLayoutManager(layoutManager);
                        recycle_test.setItemAnimator(new DefaultItemAnimator());
                        recycle_test.setAdapter(testListAdapter);
                    }
                    else
                        doToast(siacDetailsMainModel.getMessage());
                }
                else
                    doToast("data null");
            }
            @Override
            public void onFailure(Call<SIACDetailsMainModel> call, Throwable t) {
                doToast(getString(R.string.went_wrong));
                System.out.println("call fail "+t);
                apiCalled();
            }
        });
    }
    private void apiCall() {
        shimmerLayout.setVisibility(View.VISIBLE);
        shimmerLayout.startShimmerAnimation();
    }
    private void apiCalled() {
        shimmerLayout.setVisibility(View.GONE);
        shimmerLayout.stopShimmerAnimation();
    }
    private void doToast(String msg){
        if(toast_msg !=null){
            toast_msg.cancel();
        }
        toast_msg = Toast.makeText(DemoOnlineTestActivity.this, msg, Toast.LENGTH_SHORT);
        toast_msg.show();
    }
}
