package com.winbee.successcentersikar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.successcentersikar.R;
import com.winbee.successcentersikar.RetrofitApiCall.ApiClient;
import com.winbee.successcentersikar.Utils.LocalData;
import com.winbee.successcentersikar.Utils.ProgressBarUtil;
import com.winbee.successcentersikar.Utils.SharedPrefManager;
import com.winbee.successcentersikar.WebApi.ClientApi;
import com.winbee.successcentersikar.adapter.McqSolutionAdapter;
import com.winbee.successcentersikar.model.McqSolutionModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class McqSolutionActivity extends AppCompatActivity {
    private ProgressBarUtil progressBarUtil;
    private McqSolutionAdapter adapter;
    private ArrayList<McqSolutionModel> list;
    private RecyclerView askedSolution;
    String UserId,UserName;
    String Referal_code;
    ImageView WebsiteHome,img_share,img_noti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcq_solution);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        Referal_code=SharedPrefManager.getInstance(this).refCode().getREFERRAL_CODE();

        progressBarUtil   =  new ProgressBarUtil(this);
        askedSolution = findViewById(R.id.gec_asked_solution_recycle);
        WebsiteHome=findViewById(R.id.WebsiteHome);
        img_noti=findViewById(R.id.img_noti);
        img_share=findViewById(R.id.img_share);
        WebsiteHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(McqSolutionActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        img_noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(McqSolutionActivity.this,NotificationActivity.class);
                startActivity(intent);
            }
        });
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
        UserId= SharedPrefManager.getInstance(this).refCode().getUserId();
        UserName=SharedPrefManager.getInstance(this).refCode().getName();
        callAskedSolutionApiService();
    }
    private void callAskedSolutionApiService(){
        progressBarUtil.showProgress();
        ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
        Call<ArrayList<McqSolutionModel>> call = apiCall.getMcqQuestionSolution(LocalData.QuestionID,UserId,UserName);
        // Call<ArrayList<UrlQuestion>> call = mService.getQuestion(urlName.getDocumentId());

        call.enqueue(new Callback<ArrayList<McqSolutionModel>>() {
            @Override
            public void onResponse(Call<ArrayList<McqSolutionModel>> call, Response<ArrayList<McqSolutionModel>> response) {

                int statusCode = response.code();
                list = new ArrayList();
                if(statusCode==200){
                    System.out.println("Suree body: "+response.body());
                    list = response.body();
                    adapter = new McqSolutionAdapter(McqSolutionActivity.this,list);
                    askedSolution.setAdapter(adapter);
                    progressBarUtil.hideProgress();
                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<McqSolutionModel>> call, Throwable t) {
                System.out.println("Suree: "+t.getMessage());
                progressBarUtil.hideProgress();
                Toast.makeText(getApplicationContext(),"Failed" ,Toast.LENGTH_SHORT).show();

            }
        });
    }


}
