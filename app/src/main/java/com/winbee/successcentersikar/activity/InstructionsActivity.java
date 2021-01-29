package com.winbee.successcentersikar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.winbee.successcentersikar.R;
import com.winbee.successcentersikar.RetrofitApiCall.OnlineTestApiClient;
import com.winbee.successcentersikar.Utils.OnlineTestData;
import com.winbee.successcentersikar.Utils.ProgressBarUtil;
import com.winbee.successcentersikar.Utils.SharedPrefManager;
import com.winbee.successcentersikar.WebApi.ClientApi;
import com.winbee.successcentersikar.model.InstructionsModel;
import com.winbee.successcentersikar.model.StartTestModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InstructionsActivity extends AppCompatActivity {
    private TextView tv_subject_name,text2;
    private RelativeLayout layout_start_test;
    String UserId;
    String Referal_code;
    private ProgressBarUtil progressBarUtil;

    ImageView WebsiteHome,img_share,companylogo,img_noti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_instructions);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        Referal_code=SharedPrefManager.getInstance(this).refCode().getREFERRAL_CODE();

        iniIDs();
         setData();

    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
    private void iniIDs() {
        tv_subject_name=findViewById(R.id.tv_subject_name);
        text2=findViewById(R.id.text2);
        WebsiteHome=findViewById(R.id.WebsiteHome);
        WebsiteHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructionsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        img_noti=findViewById(R.id.img_noti);
        img_noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructionsActivity.this, NotificationActivity.class);
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
        layout_start_test=findViewById(R.id.layout_start_test);
        UserId = SharedPrefManager.getInstance(this).refCode().getUserId();
        progressBarUtil   =  new ProgressBarUtil(this);
        layout_start_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callApiService();
            }
        });
        callInstructionService();

    }
    private void setData() {
        tv_subject_name.setText(OnlineTestData.paperName);
    }


    private void callInstructionService() {
        ClientApi apiCAll = OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<InstructionsModel> call = apiCAll.getInstruction(OnlineTestData.paperID);
        call.enqueue(new Callback<InstructionsModel>() {
            @Override
            public void onResponse(Call<InstructionsModel> call, Response<InstructionsModel> response) {
                int statusCode = response.code();
                if (statusCode == 200 ) {
                    System.out.println("Suree body: " + response.body().getUpdate_user_status());
                    // Picasso.get().load(response.body().getFile()).into(img_video_thumbails);
                    text2.setText(Html.fromHtml(response.body().getUpdate_user_status()));
                } else {
                    System.out.println("Suree: response code" + response.message());
                    Toast.makeText(getApplicationContext(), "NetWork Issue,Please Check Network Connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InstructionsModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error " + t.getMessage());
            }
        });
    }

    private void callApiService() {
        progressBarUtil.showProgress();
        ClientApi apiCAll = OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<StartTestModel> call = apiCAll.getStartTest("WB_010",UserId,OnlineTestData.paperID,"1","true");
        call.enqueue(new Callback<StartTestModel>() {
            @Override
            public void onResponse(Call<StartTestModel> call, Response<StartTestModel> response) {
                int statusCode = response.code();
                if(statusCode==200){
                    progressBarUtil.hideProgress();
                    Intent intent=new Intent(InstructionsActivity.this, OnlineQuestionActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(getApplicationContext(),"Success" , Toast.LENGTH_SHORT).show();

                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"something went wrong" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StartTestModel> call, Throwable t) {
                progressBarUtil.hideProgress();
                Toast.makeText(getApplicationContext(),"Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error "+t.getMessage());
            }
        });
    }

}
