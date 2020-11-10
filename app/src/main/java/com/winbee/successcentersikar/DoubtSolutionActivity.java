package com.winbee.successcentersikar;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;
import com.winbee.successcentersikar.RetrofitApiCall.ApiClient;
import com.winbee.successcentersikar.WebApi.ClientApi;
import com.winbee.successcentersikar.adapter.SolutionAdapter;
import com.winbee.successcentersikar.model.SolutionDoubtQuestion;
import com.winbee.successcentersikar.model.SolutionQuestion;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoubtSolutionActivity extends AppCompatActivity {
    private ProgressBarUtil progressBarUtil;
   private SolutionAdapter adapter;
    private ArrayList<SolutionQuestion> list;
    private RecyclerView askedSolution;
    EditText editTextGiveSolution;
    ImageView image_show;
    ImageView submit_solution,WebsiteHome,img_share,select_image,select_url;
    private TextView txt_user,txt_time,txt_ask_title,txt_ask_question,txt_commments;
    LinearLayout layout_user,layout_test_series,layout_home,layout_doubt,layout_notification;
    private  static final int IMG_REQUEST=777;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doubt_solution);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        askedSolution = findViewById(R.id.gec_asked_solution_recycle);
        editTextGiveSolution=findViewById(R.id.editTextGiveSolution);
        select_image=findViewById(R.id.select_image);
        select_url=findViewById(R.id.select_url);
        submit_solution=findViewById(R.id.submit_solution);
        txt_user=findViewById(R.id.txt_user);
        txt_user.setText(LocalData.User);
        txt_time=findViewById(R.id.txt_time);
        txt_time.setText(LocalData.Duration);
        txt_ask_title=findViewById(R.id.txt_ask_title);
        txt_ask_title.setText(LocalData.Title);
        txt_ask_question=findViewById(R.id.txt_ask_question);
        txt_ask_question.setText(LocalData.Discription);
        txt_commments=findViewById(R.id.txt_commments);
        txt_commments.setText(LocalData.Commnts);
        WebsiteHome=findViewById(R.id.WebsiteHome);
        img_share=findViewById(R.id.img_share);
        image_show=findViewById(R.id.image_show);
        Picasso.get().load(LocalData.Discription).placeholder(R.drawable.dummyimage).fit().into(image_show);
        if (LocalData.QuestionType.equalsIgnoreCase("1")){
            image_show.setVisibility(View.GONE);
            txt_ask_question.setVisibility(View.VISIBLE);
        }else if (LocalData.QuestionType.equalsIgnoreCase("2")){
            image_show.setVisibility(View.VISIBLE);
            txt_ask_question.setVisibility(View.GONE);
        }
        layout_user=findViewById(R.id.layout_user);
        layout_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(DoubtSolutionActivity.this,DashboardCourseActivity.class);
                startActivity(profile);
            }
        });
        layout_test_series=findViewById(R.id.layout_test_series);
        layout_test_series.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent test = new Intent(DoubtSolutionActivity.this,SubjectActivity.class);
                startActivity(test);
                //Toast.makeText(DoubtSolutionActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        layout_home=findViewById(R.id.layout_home);
        layout_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(DoubtSolutionActivity.this,MainActivity.class);
                startActivity(profile);
            }
        });
        layout_doubt=findViewById(R.id.layout_doubt);
        layout_doubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(DoubtSolutionActivity.this,DoubtActivity.class);
                startActivity(profile);
            }
        });
        layout_notification=findViewById(R.id.layout_notification);
        layout_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(DoubtSolutionActivity.this,NotificationActivity.class);
                startActivity(profile);
            }
        });
        WebsiteHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoubtSolutionActivity.this,DiscussionActivity.class);
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
                    shareMessage = shareMessage + "\nhttps://play.google.com/store/apps/details?id="+getPackageName() ;
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                }
            }
        });
        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });



        submit_solution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solutionValidation();
            }
        });
        progressBarUtil   =  new ProgressBarUtil(this);
        callAskedSolutionApiService();
    }
    private void callAskedSolutionApiService(){
        progressBarUtil.showProgress();
        ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
        Call<ArrayList<SolutionQuestion>> call = apiCall.getSolution(LocalData.FileName);
        // Call<ArrayList<UrlQuestion>> call = mService.getQuestion(urlName.getDocumentId());

        call.enqueue(new Callback<ArrayList<SolutionQuestion>>() {
            @Override
            public void onResponse(Call<ArrayList<SolutionQuestion>> call, Response<ArrayList<SolutionQuestion>> response) {

                int statusCode = response.code();
                list = new ArrayList();
                if(statusCode==200){
                    System.out.println("Suree body: "+response.body());
                    list = response.body();
                    adapter = new SolutionAdapter(DoubtSolutionActivity.this,list);
                    askedSolution.setAdapter(adapter);
                    progressBarUtil.hideProgress();
                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<SolutionQuestion>> call, Throwable t) {
                System.out.println("Suree: "+t.getMessage());
                progressBarUtil.hideProgress();
                Toast.makeText(getApplicationContext(),"Failed" ,Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void solutionValidation() {
        final String solution = editTextGiveSolution.getText().toString();
        final String question = LocalData.FileName;
        final String userid = SharedPrefManager.getInstance(this).refCode().getUserId();
        Log.d("tag", "solutionValidation: "+solution+question+userid);
        if (TextUtils.isEmpty(solution)) {
            editTextGiveSolution.setError("Please enter solution");
            editTextGiveSolution.requestFocus();
            return;
        }
        SolutionDoubtQuestion solutionDoubtQuestion = new SolutionDoubtQuestion();
        solutionDoubtQuestion.setFilename(question);
        solutionDoubtQuestion.setAnswer(solution);
        solutionDoubtQuestion.setUserid(userid);



        callSolutionApiService(solutionDoubtQuestion);

    }
    private void callSolutionApiService(SolutionDoubtQuestion solutionDoubtQuestion) {
        progressBarUtil.showProgress();
        // final UrlQuestionSolution urlQuestionSolution = new UrlQuestionSolution(solution, question,userid, documentid);
        ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
        Call<SolutionDoubtQuestion> call = apiCall.getNewSolutionText(solutionDoubtQuestion.getUserid(),solutionDoubtQuestion.getFilename(),solutionDoubtQuestion.getAnswer(),"1");
        Log.d("tag", "callSolutionApiService: "+solutionDoubtQuestion.getFilename()+solutionDoubtQuestion.getAnswer()+solutionDoubtQuestion.getUserid());
        call.enqueue(new Callback<SolutionDoubtQuestion>() {
            @Override
            public void onResponse(Call<SolutionDoubtQuestion> call, Response<SolutionDoubtQuestion> response) {
                int statusCode = response.code();
                if (statusCode == 200 && response.body() != null) {
                    progressBarUtil.hideProgress();
                    startActivity(new Intent(DoubtSolutionActivity.this, DoubtSolutionActivity.class));
                    finish();
                } else {
                    System.out.println("Sur: response code" + response.message());
                    Toast.makeText(getApplicationContext(), "Ërror due to" + response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SolutionDoubtQuestion> call, Throwable t) {
                System.out.println("Suree: " + t.getMessage());
                progressBarUtil.hideProgress();
                Toast.makeText(getApplicationContext(), "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                final Dialog dialog = new Dialog(DoubtSolutionActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.select_image_alert);
                TextView txt_cancel=dialog.findViewById(R.id.txt_cancel);
                TextView txt_re_select=dialog.findViewById(R.id.txt_re_select);
                TextView txt_upload=dialog.findViewById(R.id.txt_upload);
                ImageView selected_image= dialog.findViewById(R.id.selected_image);
                selected_image.setImageBitmap(bitmap);
                txt_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                txt_re_select.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectImage();
                        dialog.dismiss();
                    }
                });
                txt_upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //apicall
                        solutionImageValidation();
                        dialog.dismiss();
                    }
                });

                dialog.show();
                dialog.setCancelable(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void solutionImageValidation() {
        final String solution = imageToString();
        final String question = LocalData.FileName;
        final String userid = SharedPrefManager.getInstance(this).refCode().getUserId();
        Log.d("tag", "solutionValidation: "+solution+question+userid);
        SolutionDoubtQuestion solutionDoubtQuestion = new SolutionDoubtQuestion();
        solutionDoubtQuestion.setFilename(question);
        solutionDoubtQuestion.setAnswer(solution);
        solutionDoubtQuestion.setUserid(userid);



        callSolutionImageApiService(solutionDoubtQuestion);

    }
    private void callSolutionImageApiService(SolutionDoubtQuestion solutionDoubtQuestion) {
        progressBarUtil.showProgress();
        // final UrlQuestionSolution urlQuestionSolution = new UrlQuestionSolution(solution, question,userid, documentid);
        ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
        Call<SolutionDoubtQuestion> call = apiCall.getNewSolutionImage(solutionDoubtQuestion.getUserid(),solutionDoubtQuestion.getFilename(),solutionDoubtQuestion.getAnswer(),"2");
        Log.d("tag", "callSolutionApiService: "+solutionDoubtQuestion.getFilename()+solutionDoubtQuestion.getAnswer()+solutionDoubtQuestion.getUserid());
        call.enqueue(new Callback<SolutionDoubtQuestion>() {
            @Override
            public void onResponse(Call<SolutionDoubtQuestion> call, Response<SolutionDoubtQuestion> response) {
                int statusCode = response.code();
                if (statusCode == 200 && response.body() != null) {
                    progressBarUtil.hideProgress();
                    Toast.makeText(DoubtSolutionActivity.this, "image uploaded", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DoubtSolutionActivity.this, DoubtSolutionActivity.class));
                    finish();
                } else {
                    System.out.println("Sur: response code" + response.message());
                    Toast.makeText(getApplicationContext(), "Ërror due to" + response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SolutionDoubtQuestion> call, Throwable t) {
                System.out.println("Suree: " + t.getMessage());
                progressBarUtil.hideProgress();
                Toast.makeText(getApplicationContext(), "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private String imageToString()
        {

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            // In case you want to compress your image, here it's at 40%
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();



            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        }
}
