package com.winbee.successcentersikar.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.balsikandar.crashreporter.CrashReporter;
import com.squareup.picasso.Picasso;
import com.winbee.successcentersikar.Fragment.BottomSheetQuestionFragment;
import com.winbee.successcentersikar.Fragment.BottomSheetResultFragment;
import com.winbee.successcentersikar.R;
import com.winbee.successcentersikar.RetrofitApiCall.OnlineTestApiClient;
import com.winbee.successcentersikar.Utils.OnlineTestData;
import com.winbee.successcentersikar.Utils.SharedPrefManager;
import com.winbee.successcentersikar.WebApi.ClientApi;
import com.winbee.successcentersikar.model.ResultModel;
import com.winbee.successcentersikar.model.SIADDQuestionDataModel;
import com.winbee.successcentersikar.model.SIADDQuestionSectionModel;
import com.winbee.successcentersikar.model.SIADDataModel;
import com.winbee.successcentersikar.model.SIADMainModel;
import com.winbee.successcentersikar.model.StudentQAModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.supercharge.shimmerlayout.ShimmerLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnlineQuestionActivity extends AppCompatActivity implements View.OnClickListener {
    private ShimmerLayout shimmerLayout;
    String Referal_code;
    private ImageView pauseBtn,listBtn,img_title,img_option1,img_option2,img_option3,img_option4,
            img_option1_slected,img_option2_selected,img_option3_selected,img_option4_selected;
    private TextView tv_testName,tv_timer;
    private RelativeLayout layout_question;
    private TextView tv_question_num,text_view_marks,tv_review_question,textview_Question,textview_option1,
            textview_option2,textview_option3,textview_option4,buttonSubmit,tv_review_question_selected,
            textview_option1_slected,textview_option2_selected,textview_option3_selected,textview_option4_selected;
    private Button buttonSubmitAndReview,buttonReview,buttonNext,buttonSaveNext;
    private View view_option1,view_option1_selected,view_option2,view_option2_selected,view_option3,view_option3_selected,
            view_option4,view_option4_selected;
    private RelativeLayout layout_option1,layout_option2,layout_option3,layout_option4,
            layout_option1_selected,layout_option2_selected,layout_option3_selected,layout_option4_selected;
    private int currentQuestion=0,totalQuestion=0,ansSelected=0,questionReview=0;
    private String selectedAns="";
    int milliTimer,cntMillitimer,countTimer;
    int ReHrs,ReMin,ReSec;
    ImageView WebsiteHome,img_share;
    CountDownTimer countDownTimer;
    List<SIADDQuestionDataModel> siaddQuestionDataModelList;
    List<StudentQAModel> studentQAModelList=new ArrayList<>();
    BottomSheetQuestionFragment bottomSheetFragment = new BottomSheetQuestionFragment();
    BottomSheetResultFragment bottomSheetResultFragment=new BottomSheetResultFragment();
    private Toast toast_msg;
    String UserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_test_question);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        Referal_code=SharedPrefManager.getInstance(this).refCode().getREFERRAL_CODE();
        try{
            WebsiteHome=findViewById(R.id.WebsiteHome);
            WebsiteHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(OnlineQuestionActivity.this,MainActivity.class);
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
            countTimer= Integer.parseInt(OnlineTestData.time);
            countTimer=countTimer*60;
            milliTimer=(countTimer+1)*1000;
            cntMillitimer=milliTimer;
            iniIDs();
            getQuestionData();
            buttonNext.setOnClickListener(this);
            layout_option1.setOnClickListener(this);
            layout_option2.setOnClickListener(this);
            layout_option3.setOnClickListener(this);
            layout_option4.setOnClickListener(this);
            layout_option1_selected.setOnClickListener(this);
            layout_option2_selected.setOnClickListener(this);
            layout_option3_selected.setOnClickListener(this);
            layout_option4_selected.setOnClickListener(this);
            listBtn.setOnClickListener(this);
            tv_review_question_selected.setOnClickListener(this);
            tv_review_question.setOnClickListener(this);


            buttonSaveNext.setOnClickListener(this);
            buttonNext.setOnClickListener(this);
            buttonSubmitAndReview.setOnClickListener(this);
            buttonReview.setOnClickListener(this);
            buttonSubmit.setOnClickListener(this);
            UserId= SharedPrefManager.getInstance(this).refCode().getUserId();

        }catch (Exception e){
            CrashReporter.logException(e);
        }


    }
    @Override
    public void onBackPressed(){
       // super.onBackPressed();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                OnlineQuestionActivity.this);
        alertDialogBuilder.setTitle("Exit");
        alertDialogBuilder
                .setMessage("Do you really want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                    //    OnlineQuestionActivity.this.finish();
                        Intent intent =new Intent(OnlineQuestionActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.layout_option1:
                ansSelected=1;
                selectedAns="1";
               // layout_option1.setBackground(getDrawable(R.drawable.q_non_selected));
                layout_option2.setBackground(getDrawable(R.drawable.q_non_selected));
                layout_option3.setBackground(getDrawable(R.drawable.q_non_selected));
                layout_option4.setBackground(getDrawable(R.drawable.q_non_selected));
                layout_option1.setVisibility(View.GONE);
                layout_option1_selected.setVisibility(View.VISIBLE);
                layout_option2.setVisibility(View.VISIBLE);
                layout_option2_selected.setVisibility(View.GONE);
                layout_option3.setVisibility(View.VISIBLE);
                layout_option3_selected.setVisibility(View.GONE);
                layout_option4.setVisibility(View.VISIBLE);
                layout_option4_selected.setVisibility(View.GONE);
                break;
            case R.id.layout_option1_selected:
                ansSelected=0;
                selectedAns="";
 //               layout_option1.setBackground(getDrawable(R.drawable.q_selected));
                layout_option2.setBackground(getDrawable(R.drawable.q_non_selected));
                layout_option3.setBackground(getDrawable(R.drawable.q_non_selected));
                layout_option4.setBackground(getDrawable(R.drawable.q_non_selected));
                layout_option1.setVisibility(View.VISIBLE);
                layout_option1_selected.setVisibility(View.GONE);
                break;
            case R.id.layout_option2:
                ansSelected=1;
                selectedAns="2";
               // layout_option2.setBackground(getDrawable(R.drawable.q_selected));
                layout_option1.setBackground(getDrawable(R.drawable.q_non_selected));
                layout_option3.setBackground(getDrawable(R.drawable.q_non_selected));
                layout_option4.setBackground(getDrawable(R.drawable.q_non_selected));
                layout_option2.setVisibility(View.GONE);
                layout_option2_selected.setVisibility(View.VISIBLE);
                layout_option1.setVisibility(View.VISIBLE);
                layout_option1_selected.setVisibility(View.GONE);
                layout_option3.setVisibility(View.VISIBLE);
                layout_option3_selected.setVisibility(View.GONE);
                layout_option4.setVisibility(View.VISIBLE);
                layout_option4_selected.setVisibility(View.GONE);
                break;

            case R.id.layout_option2_selected:
                ansSelected=0;
                selectedAns="";
             //   layout_option2.setBackground(getDrawable(R.drawable.q_non_selected));
                layout_option1.setBackground(getDrawable(R.drawable.q_non_selected));
                layout_option3.setBackground(getDrawable(R.drawable.q_non_selected));
                layout_option4.setBackground(getDrawable(R.drawable.q_non_selected));
                layout_option2.setVisibility(View.VISIBLE);
                layout_option2_selected.setVisibility(View.GONE);
                break;
            case R.id.layout_option3:
                ansSelected=1;
                selectedAns="3";
               // layout_option3.setBackground(getDrawable(R.drawable.q_selected));
                layout_option1.setBackground(getDrawable(R.drawable.q_non_selected));
                layout_option2.setBackground(getDrawable(R.drawable.q_non_selected));
                layout_option4.setBackground(getDrawable(R.drawable.q_non_selected));
                layout_option3.setVisibility(View.GONE);
                layout_option3_selected.setVisibility(View.VISIBLE);
                layout_option1.setVisibility(View.VISIBLE);
                layout_option1_selected.setVisibility(View.GONE);
                layout_option2.setVisibility(View.VISIBLE);
                layout_option2_selected.setVisibility(View.GONE);
                layout_option4.setVisibility(View.VISIBLE);
                layout_option4_selected.setVisibility(View.GONE);
                break;
            case R.id.layout_option3_selected:
                ansSelected=0;
                selectedAns="";
               // layout_option3.setBackground(getDrawable(R.drawable.q_non_selected));
                layout_option1.setBackground(getDrawable(R.drawable.q_non_selected));
                layout_option2.setBackground(getDrawable(R.drawable.q_non_selected));
                layout_option4.setBackground(getDrawable(R.drawable.q_non_selected));
                layout_option3.setVisibility(View.VISIBLE);
                layout_option3_selected.setVisibility(View.GONE);
                break;
            case R.id.layout_option4:
                ansSelected=1;
                selectedAns="4";
                //layout_option4.setBackground(getDrawable(R.drawable.q_selected));
                layout_option1.setBackground(getDrawable(R.drawable.q_non_selected));
                layout_option2.setBackground(getDrawable(R.drawable.q_non_selected));
                layout_option3.setBackground(getDrawable(R.drawable.q_non_selected));
                layout_option4.setVisibility(View.GONE);
                layout_option4_selected.setVisibility(View.VISIBLE);
                layout_option1.setVisibility(View.VISIBLE);
                layout_option1_selected.setVisibility(View.GONE);
                layout_option3.setVisibility(View.VISIBLE);
                layout_option3_selected.setVisibility(View.GONE);
                layout_option2.setVisibility(View.VISIBLE);
                layout_option2_selected.setVisibility(View.GONE);
                break;
            case R.id.layout_option4_selected:
                ansSelected=0;
                selectedAns="";
               // layout_option4.setBackground(getDrawable(R.drawable.q_non_selected));
                layout_option1.setBackground(getDrawable(R.drawable.q_non_selected));
                layout_option2.setBackground(getDrawable(R.drawable.q_non_selected));
                layout_option3.setBackground(getDrawable(R.drawable.q_non_selected));
                layout_option4.setVisibility(View.VISIBLE);
                layout_option4_selected.setVisibility(View.GONE);
                break;
            case R.id.listBtn:
                showBottomSheetDialogFragment();
                break;
            case R.id.buttonSaveNext:
                if(ansSelected==1)
                {
                    String ansStatus="answered";
                    String ansStatusCode="1";
                    if(questionReview==1){
                        ansStatus="review_and_answered";
                        ansStatusCode="4";
                    }
                    studentQAModelList.get(currentQuestion).setAnsStatus(ansStatus);
                    studentQAModelList.get(currentQuestion).setSelectedAns(selectedAns);
                    studentQAModelList.get(currentQuestion).setAnsStatusCode(ansStatusCode);

                    ansSelected=0;
                    questionReview=0;
                    tv_review_question.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,getDrawable(R.drawable.ic_star_border_black_24dp),null);
                    if(currentQuestion<totalQuestion-1){
                        currentQuestion++;
                        setQuestion(currentQuestion);
                    }
                    else{
                        AlertDialog alertDialog = new AlertDialog.Builder(OnlineQuestionActivity.this)
                                .setTitle("Quiz Completed")
                                .setMessage("You have attempted all Questions..!!!\n\nShow Score")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        showResult();
                                    }
                                })
                                .show();
                    }
                }
                else{
                    Toast.makeText(OnlineQuestionActivity.this, "Please answer the current question", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.buttonNext:
                if(ansSelected==1)
                {
                    String ansStatus="answered";
                    String ansStatusCode="1";
                    if(questionReview==1){
                        ansStatus="review_and_answered";
                        ansStatusCode="4";
                    }
                    studentQAModelList.get(currentQuestion).setAnsStatus(ansStatus);
                    studentQAModelList.get(currentQuestion).setSelectedAns(selectedAns);
                    studentQAModelList.get(currentQuestion).setAnsStatusCode(ansStatusCode);
                    ansSelected=0;
                    questionReview=0;
                    tv_review_question.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,getDrawable(R.drawable.ic_star_border_black_24dp),null);
                    if(currentQuestion<totalQuestion-1) {
                        currentQuestion++;
                        setQuestion(currentQuestion);
                    }
                    else {
                        AlertDialog alertDialog = new AlertDialog.Builder(OnlineQuestionActivity.this)
                                .setTitle("Quiz Completed")
                                .setMessage("You have attempted all Questions..!!!\n\nShow Score")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        showResult();
                                    }
                                })
                                .show();
                    }
                }
                else{
                    String ansStatus="not_answered";
                    String ansStatusCode="2";
                    if(questionReview==1){
                        ansStatus="review";
                        ansStatusCode="3";
                    }
                    studentQAModelList.get(currentQuestion).setAnsStatus(ansStatus);
                    studentQAModelList.get(currentQuestion).setSelectedAns(selectedAns);
                    studentQAModelList.get(currentQuestion).setAnsStatusCode(ansStatusCode);
                    ansSelected=0;
                    questionReview=0;
                    tv_review_question.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,getDrawable(R.drawable.ic_star_border_black_24dp),null);
                    setQuestion(currentQuestion);
                    if(currentQuestion<totalQuestion-1) {
                        currentQuestion++;
                        setQuestion(currentQuestion);
                    }
                    else {
                        AlertDialog alertDialog = new AlertDialog.Builder(OnlineQuestionActivity.this)
                                .setTitle("Quiz Completed")
                                .setMessage("You have attempted all Questions..!!!\n\nShow Score")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        showResult();
                                    }
                                })
                                .show();
                    }
                }
                break;
            case R.id.tv_review_question:
                tv_review_question.setVisibility(View.GONE);
                tv_review_question_selected.setVisibility(View.VISIBLE);
                if(questionReview==0){
                    questionReview=1;
                    tv_review_question.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,getDrawable(R.drawable.ic_star_black_24dp),null);
                    studentQAModelList.get(currentQuestion).setAnsStatus("review");
                    studentQAModelList.get(currentQuestion).setAnsStatusCode("3");
                }
                else{
                    questionReview=0;
                    tv_review_question.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,getDrawable(R.drawable.ic_star_border_black_24dp),null);
                    studentQAModelList.get(currentQuestion).setAnsStatus("not_answered");
                    studentQAModelList.get(currentQuestion).setAnsStatusCode("2");
                }
                break;
            case R.id.tv_review_question_selected:
                tv_review_question.setVisibility(View.VISIBLE);
                tv_review_question_selected.setVisibility(View.GONE);
                if(questionReview==0){
                    questionReview=0;
                    tv_review_question.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,getDrawable(R.drawable.ic_star_border_black_24dp),null);
                    studentQAModelList.get(currentQuestion).setAnsStatus("not_answered");
                    studentQAModelList.get(currentQuestion).setAnsStatusCode("2");
                }
                else{
                    questionReview=1;
                    tv_review_question.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,getDrawable(R.drawable.ic_star_black_24dp),null);
                    studentQAModelList.get(currentQuestion).setAnsStatus("review");
                    studentQAModelList.get(currentQuestion).setAnsStatusCode("3");
                }
                break;
//            case R.id.buttonReview:
//                if(questionReview==0){
//                    questionReview=1;
//                    tv_review_question.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,getDrawable(R.drawable.ic_star_black_24dp),null);
//                    studentQAModelList.get(currentQuestion).setAnsStatus("review");
//                    studentQAModelList.get(currentQuestion).setAnsStatusCode("3");
//                }
//                else{
//                    questionReview=0;
//                    tv_review_question.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,getDrawable(R.drawable.ic_star_border_black_24dp),null);
//                    studentQAModelList.get(currentQuestion).setAnsStatus("not_answered");
//                    studentQAModelList.get(currentQuestion).setAnsStatusCode("2");
//                }
//                break;
            case R.id.buttonSubmitAndReview:
                if(ansSelected==1)
                {
                    String ansStatus="review_and_answered";
                    String ansStatusCode="4";
                    studentQAModelList.get(currentQuestion).setAnsStatus(ansStatus);
                    studentQAModelList.get(currentQuestion).setSelectedAns(selectedAns);
                    studentQAModelList.get(currentQuestion).setAnsStatusCode(ansStatusCode);
                    ansSelected=0;
                    questionReview=0;
                    tv_review_question.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,getDrawable(R.drawable.ic_star_border_black_24dp),null);
                    if(currentQuestion<totalQuestion-1) {
                        currentQuestion++;
                        setQuestion(currentQuestion);
                    }
                    else {
                        AlertDialog alertDialog = new AlertDialog.Builder(OnlineQuestionActivity.this)
                                .setTitle("Quiz Completed")
                                .setMessage("You have attempted all Questions..!!!\n\nShow Score")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        showResult();
                                    }
                                })
                                .show();
                    }
                }
                else{
                    Toast.makeText(OnlineQuestionActivity.this, "Please answer the current question", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.buttonSubmit:
                showResult();
                break;
        }
    }
    private void iniIDs(){
        shimmerLayout=findViewById(R.id.shimmerLayout);
        //pauseBtn=findViewById(R.id.pauseBtn);
        listBtn=findViewById(R.id.listBtn);
        tv_testName=findViewById(R.id.tv_testName);
        tv_timer=findViewById(R.id.tv_timer);
        layout_question=findViewById(R.id.layout_question);
        tv_question_num=findViewById(R.id.tv_question_num);
        text_view_marks=findViewById(R.id.text_view_marks);
        tv_review_question=findViewById(R.id.tv_review_question);
        textview_Question=findViewById(R.id.textview_Question);
        textview_option1=findViewById(R.id.textview_option1);
        textview_option2=findViewById(R.id.textview_option2);
        textview_option3=findViewById(R.id.textview_option3);
        textview_option4=findViewById(R.id.textview_option4);
        textview_option1_slected=findViewById(R.id.textview_option1_slected);
        textview_option2_selected=findViewById(R.id.textview_option2_selected);
        textview_option3_selected=findViewById(R.id.textview_option3_selected);
        textview_option4_selected=findViewById(R.id.textview_option4_selected);
        layout_option1=findViewById(R.id.layout_option1);
        layout_option2=findViewById(R.id.layout_option2);
        layout_option3=findViewById(R.id.layout_option3);
        layout_option4=findViewById(R.id.layout_option4);
        layout_option1_selected=findViewById(R.id.layout_option1_selected);
        layout_option2_selected=findViewById(R.id.layout_option2_selected);
        layout_option3_selected=findViewById(R.id.layout_option3_selected);
        layout_option4_selected=findViewById(R.id.layout_option4_selected);
        img_title=findViewById(R.id.img_title);
        img_option1=findViewById(R.id.img_option1);
        img_option2=findViewById(R.id.img_option2);
        img_option3=findViewById(R.id.img_option3);
        img_option4=findViewById(R.id.img_option4);
        img_option1_slected=findViewById(R.id.img_option1_slected);
        img_option2_selected=findViewById(R.id.img_option2_selected);
        img_option3_selected=findViewById(R.id.img_option3_selected);
        img_option4_selected=findViewById(R.id.img_option4_selected);
        tv_review_question=findViewById(R.id.tv_review_question);
        tv_review_question_selected=findViewById(R.id.tv_review_question_selected);
        view_option1=findViewById(R.id.view_option1);
        view_option2=findViewById(R.id.view_option2);
        view_option3=findViewById(R.id.view_option3);
        view_option4=findViewById(R.id.view_option4);

        buttonSubmit=findViewById(R.id.buttonSubmit);
        buttonSubmitAndReview=findViewById(R.id.buttonSubmitAndReview);
        buttonNext=findViewById(R.id.buttonNext);
        buttonReview=findViewById(R.id.buttonReview);
        buttonSaveNext=findViewById(R.id.buttonSaveNext);
    }
    private void getQuestionData() {
        apiCall();
        ClientApi apiClient= OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<SIADMainModel> call=apiClient.fetchSIADDATA(OnlineTestData.org_code,OnlineTestData.auth_code,OnlineTestData.bucketID,OnlineTestData.paperID);
        call.enqueue(new Callback<SIADMainModel>() {
            @Override
            public void onResponse(Call<SIADMainModel> call, Response<SIADMainModel> response) {
                apiCalled();
                SIADMainModel siadMainModel=response.body();
                if(siadMainModel!=null){
                    if (siadMainModel.getMessage().equalsIgnoreCase("true")){
                        setTimer();
                        tv_testName.setText(OnlineTestData.paperName);
                        SIADDataModel siadDataModel=siadMainModel.getData();
                        siaddQuestionDataModelList=new ArrayList<>(Arrays.asList(siadDataModel.getQuestionData()));
                        List<SIADDQuestionSectionModel> siaddQuestionSectionModelList=new ArrayList<>(Arrays.asList(siadDataModel.getQuestionSection()));
                        totalQuestion=siaddQuestionDataModelList.size();
                        setStudentQA();
                        setQuestion(currentQuestion);
                        layout_question.setVisibility(View.VISIBLE);
                    }
                    else
                        doToast(siadMainModel.getMessage());
                }
                else
                    doToast("data null");
            }
            @Override
            public void onFailure(Call<SIADMainModel> call, Throwable t) {
                doToast(getString(R.string.went_wrong)+t.getMessage());
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
        toast_msg = Toast.makeText(OnlineQuestionActivity.this, msg, Toast.LENGTH_SHORT);
        toast_msg.show();
    }
    private void setQuestion(int currentQuestion) {
        studentQAModelList.get(currentQuestion).setAnsStatus("not_answered");
        studentQAModelList.get(currentQuestion).setAnsStatusCode("2");
        layout_option1.setBackgroundResource(R.drawable.q_non_selected);
        layout_option2.setBackgroundResource(R.drawable.q_non_selected);
        layout_option3.setBackgroundResource(R.drawable.q_non_selected);
        layout_option4.setBackgroundResource(R.drawable.q_non_selected);
        layout_option1.setVisibility(View.VISIBLE);
        layout_option2.setVisibility(View.VISIBLE);
        layout_option3.setVisibility(View.VISIBLE);
        layout_option4.setVisibility(View.VISIBLE);
        layout_option1_selected.setVisibility(View.GONE);
        layout_option2_selected.setVisibility(View.GONE);
        layout_option3_selected.setVisibility(View.GONE);
        layout_option4_selected.setVisibility(View.GONE);
        int q=currentQuestion+1;
        tv_question_num.setText(""+q);
        //image implement
        if (siaddQuestionDataModelList.get(currentQuestion).getQuestionTitle_img().endsWith("jpg")){
            img_title.setVisibility(View.VISIBLE);
            Picasso.get().load(siaddQuestionDataModelList.get(currentQuestion).getQuestionTitle_img())
                    .placeholder(R.drawable.dummyimage)
                    .into(img_title);
        }else{
            img_title.setVisibility(View.GONE);
        }
        textview_Question.setText(Html.fromHtml(siaddQuestionDataModelList.get(currentQuestion).getQuestionTitle()));

        if (siaddQuestionDataModelList.get(currentQuestion).getOption1_img().endsWith("jpg"))
        {
            img_option1.setVisibility(View.VISIBLE);
            Picasso.get().load(siaddQuestionDataModelList.get(currentQuestion).getOption1_img())
                    .placeholder(R.drawable.dummyimage)
                    .into(img_option1);
        }else{
            img_option1.setVisibility(View.GONE);
        }
        textview_option1.setText(Html.fromHtml(siaddQuestionDataModelList.get(currentQuestion).getOption1()));

        if (siaddQuestionDataModelList.get(currentQuestion).getOption1_img().endsWith("jpg"))
        {
            img_option1_slected.setVisibility(View.VISIBLE);
            Picasso.get().load(siaddQuestionDataModelList.get(currentQuestion).getOption1_img())
                    .placeholder(R.drawable.dummyimage)
                    .into(img_option1);
        }else{
            img_option1_slected.setVisibility(View.GONE);
        }
        textview_option1_slected.setText(Html.fromHtml(siaddQuestionDataModelList.get(currentQuestion).getOption1()));

        if (siaddQuestionDataModelList.get(currentQuestion).getOption2_img().endsWith("jpg"))
        {
            img_option2.setVisibility(View.VISIBLE);
            Picasso.get().load(siaddQuestionDataModelList.get(currentQuestion).getOption2_img())
                    .placeholder(R.drawable.dummyimage)
                    .into(img_option2);
        }else{
            img_option2.setVisibility(View.GONE);
        }
        textview_option2.setText(Html.fromHtml(siaddQuestionDataModelList.get(currentQuestion).getOption2()));

        if (siaddQuestionDataModelList.get(currentQuestion).getOption2_img().endsWith("jpg"))
        {
            img_option2_selected.setVisibility(View.VISIBLE);
            Picasso.get().load(siaddQuestionDataModelList.get(currentQuestion).getOption2_img())
                    .placeholder(R.drawable.dummyimage)
                    .into(img_option2);
        }else{
            img_option2_selected.setVisibility(View.GONE);
        }
        textview_option2_selected.setText(Html.fromHtml(siaddQuestionDataModelList.get(currentQuestion).getOption2()));


        if (siaddQuestionDataModelList.get(currentQuestion).getOption3_img().endsWith("jpg"))
        {
            img_option3.setVisibility(View.VISIBLE);
            Picasso.get().load(siaddQuestionDataModelList.get(currentQuestion).getOption3_img())
                    .placeholder(R.drawable.dummyimage)
                    .into(img_option3);
        }else{
            img_option3.setVisibility(View.GONE);
        }
        textview_option3.setText(Html.fromHtml(siaddQuestionDataModelList.get(currentQuestion).getOption3()));

        if (siaddQuestionDataModelList.get(currentQuestion).getOption3_img().endsWith("jpg"))
        {
            img_option3_selected.setVisibility(View.VISIBLE);
            Picasso.get().load(siaddQuestionDataModelList.get(currentQuestion).getOption3_img())
                    .placeholder(R.drawable.dummyimage)
                    .into(img_option3);
        }else{
            img_option3_selected.setVisibility(View.GONE);
        }
        textview_option3_selected.setText(Html.fromHtml(siaddQuestionDataModelList.get(currentQuestion).getOption3()));


        if (siaddQuestionDataModelList.get(currentQuestion).getOption4_img().endsWith("jpg"))
        {
            img_option4.setVisibility(View.VISIBLE);
            Picasso.get().load(siaddQuestionDataModelList.get(currentQuestion).getOption4_img())
                    .placeholder(R.drawable.dummyimage)
                    .into(img_option4);
        }else{
            img_option4.setVisibility(View.GONE);
        }
        textview_option4.setText(Html.fromHtml(siaddQuestionDataModelList.get(currentQuestion).getOption4()));

        if (siaddQuestionDataModelList.get(currentQuestion).getOption4_img().endsWith("jpg"))
        {
            img_option4_selected.setVisibility(View.VISIBLE);
            Picasso.get().load(siaddQuestionDataModelList.get(currentQuestion).getOption4_img())
                    .placeholder(R.drawable.dummyimage)
                    .into(img_option4);
        }else{
            img_option4_selected.setVisibility(View.GONE);
        }
        textview_option4_selected.setText(Html.fromHtml(siaddQuestionDataModelList.get(currentQuestion).getOption4()));
    }

    private void setTimer() {
        countDownTimer=new CountDownTimer(milliTimer,1000) {
            @Override
            public void onTick(long l) {
                int hrs= (int) (l/(60*60*1000));
                long remainingMs= l%(60*60*1000);
                int min= (int) (remainingMs/(60*1000));
                int sec= (int) ((remainingMs%(60*1000))/1000);
                getTime(hrs);
                tv_timer.setText(""+getTime(hrs)+":"+getTime(min)+":"+getTime(sec));

                long fTime=cntMillitimer-l;

                ReHrs= (int) (fTime/(60*60*1000));
                long RmSEC= fTime%(60*60*1000);
                ReMin= (int) (RmSEC/(60*1000));
                ReSec= (int) ((RmSEC%(60*1000))/1000);
            }

            @Override
            public void onFinish() {
                doToast("Time Up, Submitting Response");
               //submitData();
                showResult();
            }
        }.start();
    }
    private String getTime(int timeData) {
        String time= String.valueOf(timeData);
        if (time.length()==1)
            return "0"+time;
        return time;
    }
    private void showResult(){
        countDownTimer.cancel();
        bottomSheetResultFragment.show(getSupportFragmentManager(), bottomSheetResultFragment.getTag());
    }
    private void setStudentQA() {
        for(int i=0;i<siaddQuestionDataModelList.size();i++){
            StudentQAModel studentQAModel=new StudentQAModel();
            studentQAModel.SectionCode=siaddQuestionDataModelList.get(i).getSectionCode();
            studentQAModel.QuestionID=siaddQuestionDataModelList.get(i).getQuestionID();
            studentQAModel.selectedAns="";
            studentQAModel.QuestionGUID=siaddQuestionDataModelList.get(i).getQuestionGUID();
            studentQAModel.ansStatusCode="0";
            studentQAModel.ansStatus="not_visited";
            studentQAModelList.add(studentQAModel);
        }
        OnlineTestData.studentQAModels=studentQAModelList;
    }
    public void showBottomSheetDialogFragment() {
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    public void questionSelected(int position) {
        bottomSheetFragment.dismiss();
        ansSelected=0;
        questionReview=0;
        currentQuestion=position;
        setQuestion(currentQuestion);
    }
    public void submitData(){
        bottomSheetResultFragment.dismiss();
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("Submitting Answers.....");
        pd.show();
        List<StudentQAModel> studentQAModelList=OnlineTestData.studentQAModels;
        JSONArray Response = new JSONArray();
        for(int i=0;i<studentQAModelList.size();i++){
            StudentQAModel studentQAModel=studentQAModelList.get(i);
            JSONObject questionData=new JSONObject();
            try {
                questionData.put("K",studentQAModel.getQuestionGUID());
                questionData.put("V",studentQAModel.getSelectedAns());
                questionData.put("T",studentQAModel.getAnsStatusCode());
                questionData.put("DB",studentQAModel.getQuestionID());
                questionData.put("G","1");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Response.put(questionData);
        }

        ClientApi apiClient= OnlineTestApiClient.getClient().create(ClientApi.class);
        Call<ResultModel> call=apiClient.submitResponse("WB_010",OnlineTestData.paperID,UserId,Response,null,true);
        call.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                ResultModel resultModel=response.body();
                pd.cancel();
                if(resultModel!=null) {
                    doToast("Response Submitted");
                    if (OnlineTestData.test_publish) {
                    Intent intent = new Intent(OnlineQuestionActivity.this, ViewResultActivity.class);
                    startActivity(intent);
                    finish();
                    } else {
                        final Dialog dialog = new Dialog(OnlineQuestionActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.custom_test_alert);
                        RelativeLayout layout_home = dialog.findViewById(R.id.layout_home);
                        TextView txt_course = dialog.findViewById(R.id.txt_course);
                        txt_course.setText(OnlineTestData.test_closed);
                        layout_home.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(OnlineQuestionActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                        dialog.show();
                        dialog.setCancelable(false);

                    }
                }
                else
                    doToast("data null");
            }
            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
                pd.cancel();
                doToast(getString(R.string.went_wrong));
                System.out.println("call fail "+t);
            }
        });
    }
    public void backToTest(){
        bottomSheetResultFragment.dismiss();
    }
}
