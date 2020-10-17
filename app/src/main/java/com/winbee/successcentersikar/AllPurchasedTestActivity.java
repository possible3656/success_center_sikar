package com.winbee.successcentersikar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.balsikandar.crashreporter.CrashReporter;
import com.winbee.successcentersikar.RetrofitApiCall.OnlineTestApiClient;
import com.winbee.successcentersikar.Utils.OnlineTestData;
import com.winbee.successcentersikar.WebApi.ClientApi;
import com.winbee.successcentersikar.adapter.AllPurchasedTest;
import com.winbee.successcentersikar.model.SectionDetailsDataModel;
import com.winbee.successcentersikar.model.SectionDetailsMainModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import io.supercharge.shimmerlayout.ShimmerLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllPurchasedTestActivity extends AppCompatActivity {

    private ShimmerLayout shimmerLayout;
    private RecyclerView recycle_subject;
    private List<SectionDetailsDataModel> list;
    private Toast toast_msg;
    private AllPurchasedTest allPurchasedTest;
    String UserId;
    ImageView WebsiteHome,img_share;
    LinearLayout layout_user,layout_test_series,layout_home,layout_doubt,layout_notification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_purchased_test);
         getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        try{
            WebsiteHome=findViewById(R.id.WebsiteHome);
            WebsiteHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AllPurchasedTestActivity.this,MainActivity.class);
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
                        shareMessage = shareMessage + "\nhttps://play.google.com/store/apps/details?id="+getPackageName() ;
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                        startActivity(Intent.createChooser(shareIntent, "choose one"));
                    } catch(Exception e) {
                    }
                }
            });
            layout_user=findViewById(R.id.layout_user);
            layout_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent profile = new Intent(AllPurchasedTestActivity.this,DashboardCourseActivity.class);
                    startActivity(profile);
                }
            });
            layout_test_series=findViewById(R.id.layout_test_series);
            layout_home=findViewById(R.id.layout_home);
            layout_home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent profile = new Intent(AllPurchasedTestActivity.this,MainActivity.class);
                    startActivity(profile);
                }
            });
            layout_doubt=findViewById(R.id.layout_doubt);
            layout_doubt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent profile = new Intent(AllPurchasedTestActivity.this,DiscussionActivity.class);
                    startActivity(profile);
                }
            });
            layout_notification=findViewById(R.id.layout_notification);
            layout_notification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent profile = new Intent(AllPurchasedTestActivity.this,NotificationActivity.class);
                    startActivity(profile);
                }
            });
            iniIDs();
            getSubjectName();
        }catch (Exception e){
            CrashReporter.logException(e);
        }

    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
    private void iniIDs(){
        shimmerLayout=findViewById(R.id.shimmerLayout);
        recycle_subject=findViewById(R.id.recycle_subject);
        UserId= SharedPrefManager.getInstance(this).refCode().getUserId();
    }
    private void getSubjectName() {
        apiCall();
        ClientApi apiClient= OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<SectionDetailsMainModel> call=apiClient.fetchSectionDetails(OnlineTestData.org_code,OnlineTestData.auth_code,UserId);
        call.enqueue(new Callback<SectionDetailsMainModel>() {
            @Override
            public void onResponse(Call<SectionDetailsMainModel> call, Response<SectionDetailsMainModel> response) {
                apiCalled();
                SectionDetailsMainModel sectionDetailsMainModel=response.body();
                if(sectionDetailsMainModel!=null){
                    if (sectionDetailsMainModel.getMessage().equalsIgnoreCase("TRUE")){
                        list=new ArrayList<>(Arrays.asList(Objects.requireNonNull(sectionDetailsMainModel).getData()));
                        allPurchasedTest=new AllPurchasedTest(AllPurchasedTestActivity.this,list);
//                        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(SubjectActivity.this, LinearLayoutManager.VERTICAL, false);
//                        recycle_subject.setLayoutManager(layoutManager);
//                        recycle_subject.setItemAnimator(new DefaultItemAnimator());
                        recycle_subject.setAdapter(allPurchasedTest);
                    }
                    else
                        doToast(sectionDetailsMainModel.getMessage());
                }
                else
                    doToast("No Test Available");
            }
            @Override
            public void onFailure(Call<SectionDetailsMainModel> call, Throwable t) {
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
        toast_msg = Toast.makeText(AllPurchasedTestActivity.this, msg, Toast.LENGTH_SHORT);
        toast_msg.show();
    }
}
