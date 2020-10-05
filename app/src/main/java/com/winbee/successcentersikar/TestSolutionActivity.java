package com.winbee.successcentersikar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.winbee.successcentersikar.RetrofitApiCall.OnlineTestApiClient;
import com.winbee.successcentersikar.Utils.OnlineTestData;
import com.winbee.successcentersikar.WebApi.ClientApi;
import com.winbee.successcentersikar.adapter.TestSolutionAdapter;
import com.winbee.successcentersikar.model.SIADDSolutionDataModel;
import com.winbee.successcentersikar.model.SIADSolutionDataModel;
import com.winbee.successcentersikar.model.SIADSolutionMainModel;
import com.winbee.successcentersikar.model.ViewResult;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestSolutionActivity extends AppCompatActivity {
    private RecyclerView test_recycler;
    private ArrayList<SIADDSolutionDataModel> list;
    private TestSolutionAdapter adapter;
    String UserID;
    private ProgressBarUtil progressBarUtil;
    private ImageView WebsiteHome,img_share;
    private TextView tv_paper_name,tv_section_name,tv_total_question,tv_total_attempt,tv_total_correct,tv_total_review,tv_total_wrong,tv_total_marks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_solution);

        UserID = SharedPrefManager.getInstance(this).refCode().getUserId();
        progressBarUtil = new ProgressBarUtil(this);
        test_recycler = findViewById(R.id.test_solution_recycle);
        WebsiteHome=findViewById(R.id.WebsiteHome);
        img_share=findViewById(R.id.img_share);
        tv_paper_name=findViewById(R.id.tv_paper_name);
        tv_section_name=findViewById(R.id.tv_section_name);
        tv_total_question=findViewById(R.id.tv_total_question);
        tv_total_attempt=findViewById(R.id.tv_total_attempt);
        tv_total_correct=findViewById(R.id.tv_total_correct);
        tv_total_review=findViewById(R.id.tv_total_review);
        tv_total_wrong=findViewById(R.id.tv_total_wrong);
        tv_total_marks=findViewById(R.id.tv_total_marks);
        WebsiteHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestSolutionActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Sekhawati Defence Academy");
                    String shareMessage= "\nSekhawati Defence Academy download the application.\n ";
                    shareMessage = shareMessage + "\nhttps://play.google.com/store/apps/details?id="+getPackageName() ;
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                }
            }
        });
        callApiService();
        callResultService();
    }


    private void callResultService() {
        ClientApi apiCAll = OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<ViewResult> call = apiCAll.viewResult("WB_010", OnlineTestData.paperID,UserID);
        call.enqueue(new Callback<ViewResult>() {
            @Override
            public void onResponse(Call<ViewResult> call, Response<ViewResult> response) {
                list = new ArrayList();
                int statusCode = response.code();
                if(statusCode==200 && response.body()!=null){
                    System.out.println("Suree body: "+response.body());
                    OnlineTestData.PaperID=response.body().getPaperID();
                    String Question= String.valueOf(response.body().getTotalQuestion());
                    tv_total_question.setText(Question);
                    String Attempt= String.valueOf(response.body().getAttempt());
                    tv_total_attempt.setText(Attempt);
                    String Correct= String.valueOf(response.body().getCorrect());
                    tv_total_correct.setText(Correct);
                    String Review= String.valueOf(response.body().getReview());
                    tv_total_review.setText(Review);
                    String Wrong= String.valueOf(response.body().getWrong());
                    tv_total_wrong.setText(Wrong);
                    String Total=response.body().getTotalMarks();
                    tv_total_marks.setText(Total);

                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ViewResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error "+t.getMessage());
            }
        });
        tv_paper_name.setText(OnlineTestData.paperName);
        tv_section_name.setText(OnlineTestData.paperName);
    }


    private void callApiService() {
        progressBarUtil.showProgress();
        ClientApi apiCAll = OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<SIADSolutionMainModel> call = apiCAll.getTestSolution(OnlineTestData.paperID, UserID);
        call.enqueue(new Callback<SIADSolutionMainModel>() {
            @Override
            public void onResponse(Call<SIADSolutionMainModel> call, Response<SIADSolutionMainModel> response) {
                SIADSolutionMainModel purchasedMainModel = response.body();
                int statusCode = response.code();
                // list = new ArrayList();
                if (statusCode == 200) {
                    if (response.body().getMessage().equalsIgnoreCase("true")) {
                        //courses.setVisibility(View.VISIBLE);
                        SIADSolutionDataModel siadSolutionDataModel = purchasedMainModel.getData();
                        list = new ArrayList<>(Arrays.asList(siadSolutionDataModel.getQuestionData()));
                        System.out.println("Suree body: " + response.body());
                        adapter = new TestSolutionAdapter(TestSolutionActivity.this, list);
                        test_recycler.setAdapter(adapter);
                        progressBarUtil.hideProgress();
                    } else {
                        //nocourse.setVisibility(View.VISIBLE);
                    }
                } else {
                    System.out.println("Suree: response code" + response.message());
                    Toast.makeText(getApplicationContext(), "NetWork Issue,Please Check Network Connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SIADSolutionMainModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

                System.out.println("Suree: Error " + t.getMessage());
            }
        });
    }
}