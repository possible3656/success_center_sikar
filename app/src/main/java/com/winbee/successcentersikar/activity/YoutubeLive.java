package com.winbee.successcentersikar.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.winbee.successcentersikar.NewModels.LiveChatMessage;
import com.winbee.successcentersikar.NewModels.LiveMessage;
import com.winbee.successcentersikar.NewModels.LiveMessageArray;
import com.winbee.successcentersikar.NewModels.PdfContent;
import com.winbee.successcentersikar.NewModels.PdfContentArray;
import com.winbee.successcentersikar.R;
import com.winbee.successcentersikar.RetrofitApiCall.ApiClient;
import com.winbee.successcentersikar.Utils.LocalData;
import com.winbee.successcentersikar.Utils.ProgressBarUtil;
import com.winbee.successcentersikar.Utils.SharedPrefManager;
import com.winbee.successcentersikar.WebApi.ClientApi;
import com.winbee.successcentersikar.adapter.ServerMessageAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.balsikandar.crashreporter.CrashReporter.getContext;


public class YoutubeLive extends AppCompatActivity {

    RecyclerView messageRecyclerView;
    RelativeLayout relativeLayoutLive,title_bar_home,details_layout;
    private ProgressBarUtil progressBarUtil;
    private Button btn_pdf;
    private LinearLayout footer;
    private TextView txt_subject,txt_topic,txt_teacher;
    private ArrayList<PdfContentArray> list1;
    private YouTubePlayerView youTubePlayerView;
    String UserId,Username,android_id;
    ImageView WebsiteHome,img_share,img_noti;
    String Referal_code;

    LinearLayout layout_user,layout_test_series,layout_home,layout_doubt,layout_notification;
    EditText editTextMessageLive;
    ImageView sendMessageLive;
    private TextView textNoMessageLive;
    ProgressBar progressBar;
    private ArrayList<LiveMessageArray> list;
    private ServerMessageAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_live);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        title_bar_home = findViewById(R.id.title_bar_home);
        editTextMessageLive=findViewById(R.id.editTextMessageLive);
        sendMessageLive=findViewById(R.id.sendMessageLive);
        sendMessageLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageSend();
            }
        });
        messageRecyclerView=findViewById(R.id.liveMessageRecyclerViewLive);
        textNoMessageLive=findViewById(R.id.textNoMessageLive);
        progressBar=findViewById(R.id.progressBarLive);
        relativeLayoutLive=findViewById(R.id.relative_layout_live);
        UserId = SharedPrefManager.getInstance(this).refCode().getUserId();
        Username=SharedPrefManager.getInstance(this).refCode().getName();
        Referal_code=SharedPrefManager.getInstance(this).refCode().getREFERRAL_CODE();

        android_id = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
       // details_layout = findViewById(R.id.details_layout);
        progressBarUtil = new ProgressBarUtil(this);
        relativeLayoutLive = findViewById(R.id.relative_layout_live);

        footer = findViewById(R.id.footer);
        this.youTubePlayerView = findViewById(R.id.youtube_player);
        getLifecycle().addObserver(youTubePlayerView);
        layout_user=findViewById(R.id.layout_user);
        layout_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(YoutubeLive.this, DashboardCourseActivity.class);
                startActivity(profile);
            }
        });
        img_noti=findViewById(R.id.img_noti);
        img_noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(YoutubeLive.this, NotificationActivity.class);
                startActivity(profile);
            }
        });
        layout_test_series=findViewById(R.id.layout_test_series);
        layout_test_series.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent test = new Intent(YoutubeLive.this,SubjectActivity.class);
                startActivity(test);
                // Toast.makeText(MainActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        layout_home=findViewById(R.id.layout_home);
        layout_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(YoutubeLive.this, MainActivity.class);
                startActivity(profile);
            }
        });
        layout_doubt=findViewById(R.id.layout_doubt);
        layout_doubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(YoutubeLive.this, DiscussionActivity.class);
                startActivity(profile);
            }
        });
        layout_notification=findViewById(R.id.layout_notification);
        layout_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(YoutubeLive.this, ReferalActivity.class);
                startActivity(profile);
            }
        });
        WebsiteHome=findViewById(R.id.WebsiteHome);
        WebsiteHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YoutubeLive.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btn_pdf=findViewById(R.id.btn_pdf);
        btn_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo
                Intent intent = new Intent(YoutubeLive.this,StudyMaterial.class);
                startActivity(intent);
                finish();
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
        youTubePlayerView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
            @Override
            public void onYouTubePlayerEnterFullScreen() {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);
                title_bar_home.setVisibility(View.GONE);
                btn_pdf.setVisibility(View.GONE);
                footer.setVisibility(View.GONE);
               // details_layout.setVisibility(View.GONE);
                // hide softkeys if it is already open

            }


            @Override
            public void onYouTubePlayerExitFullScreen() {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
                title_bar_home.setVisibility(View.VISIBLE);
                btn_pdf.setVisibility(View.VISIBLE);
                footer.setVisibility(View.GONE);
               // details_layout.setVisibility(View.VISIBLE);
            }
        });

        youTubePlayerView.
                addYouTubePlayerListener(new AbstractYouTubePlayerListener() {


                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                        YouTubePlayerUtils.loadOrCueVideo(
                                youTubePlayer, getLifecycle(),
                                LocalData.VideoUrl, 0f
                        );


                    }

                });
        callTopicApiService();

    }

    private void callTopicApiService(){
        progressBarUtil.showProgress();
        ClientApi mService = ApiClient.getClient().create(ClientApi.class);
        Call<PdfContent> call = mService.getCoursePdf(5,UserId,"WB_010",LocalData.TopicDocumentId,android_id);
        call.enqueue(new Callback<PdfContent>() {
            @Override
            public void onResponse(Call<PdfContent> call, Response<PdfContent> response) {
                PdfContent pdfContent =response.body();

                int statusCode = response.code();
                list1 = new ArrayList();
                if(statusCode==200){
                    if (response.body().getError()==false) {
                        System.out.println("Suree body: " + response.body());
                        list1 = new ArrayList<>(Arrays.asList(Objects.requireNonNull(pdfContent).getData()));
                        //LocalData.PdfUrl=list.get(0).getURL();
                        Log.i("pdf", "onResponse: "+ LocalData.PdfUrl);
                        progressBarUtil.hideProgress();
                    }
                    else{
                        android.app.AlertDialog.Builder alertDialogBuilder =
                                new android.app.AlertDialog.Builder(YoutubeLive.this);
                        alertDialogBuilder.setTitle("Alert");
                        alertDialogBuilder
                                .setMessage(response.body().getError_Message())
                                .setCancelable(false)
                                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        forceLogout();
                                    }
                                });

                        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(), response.body().getError_Message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<PdfContent> call, Throwable t) {
                System.out.println("Suree: "+t.getMessage());
            }
        });
    }

    private void messageSend() {
        final String message = editTextMessageLive.getText().toString();
        LocalData.Message=message;
        if (TextUtils.isEmpty(message)) {
            editTextMessageLive.setError("enter some message");
            editTextMessageLive.requestFocus();
            return;
        }


        callMessageSendApi();

    }
    private void callMessageSendApi() {
        ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
        Call<LiveChatMessage> call = apiCall.getLiveMessage(1,UserId,Username,LocalData.DocumentId,LocalData.Message,android_id);
        Log.i("tag", "callMessageSendApi: " +UserId+Username+LocalData.LiveId+LocalData.Message);
        call.enqueue(new Callback<LiveChatMessage>() {
            @Override
            public void onResponse(Call<LiveChatMessage> call, Response<LiveChatMessage> response) {
                int statusCode = response.code();
                if (statusCode == 200 && response.body().getResponse()) {
                    if (!response.body().getError()){
                        editTextMessageLive.getText().clear();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
                        callAllMessageFetch();
                    }else{
                        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(
                                YoutubeLive.this);
                        alertDialogBuilder.setTitle("Alert");
                        alertDialogBuilder
                                .setMessage(response.body().getError_Message())
                                .setCancelable(false)
                                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        forceLogout();
                                    }
                                });

                        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }

                } else {
                    System.out.println("Sur: response code" + response.message());
                    Toast.makeText(getApplicationContext(), "Ërror due to" + response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LiveChatMessage> call, Throwable t) {
                System.out.println("Suree: " + t.getMessage());
                progressBarUtil.hideProgress();
                Toast.makeText(getApplicationContext(), "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }



    private void callAllMessageFetch(){
        ClientApi apiCall = ApiClient.getClient().create(ClientApi.class);
        Call<LiveMessage> call = apiCall.getLiveMessageFetch(2,UserId,android_id,LocalData.DocumentId);
        call.enqueue(new Callback<LiveMessage>() {
            @Override
            public void onResponse(Call<LiveMessage> call, Response<LiveMessage> response) {
                LiveMessage liveMessage =response.body();
                int statusCode = response.code();
                list = new ArrayList();
                if(statusCode==200){
                    if (response.body().getResponse()==true) {
                        if (response.body().getError()==false){
                            textNoMessageLive.setVisibility(View.GONE);
                            list =new ArrayList<>(Arrays.asList(Objects.requireNonNull(liveMessage).getData()));
                            System.out.println("Suree body: " + list);
                            adapter = new ServerMessageAdapter(YoutubeLive.this, list);
                            messageRecyclerView.setAdapter(adapter);
                        }else{
                            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(
                                    YoutubeLive.this);
                            alertDialogBuilder.setTitle("Alert");
                            alertDialogBuilder
                                    .setMessage(response.body().getError_Message())
                                    .setCancelable(false)
                                    .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            forceLogout();
                                        }
                                    });

                            android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }
                    }else{
                        textNoMessageLive.setVisibility(View.VISIBLE);
                    }


                }
                else{
                    System.out.println("Suree: response code"+response.message());
                    Toast.makeText(getApplicationContext(),"Ërror due to" + response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LiveMessage> call, Throwable t) {
                System.out.println("Suree: "+t.getMessage());
                progressBarUtil.hideProgress();
                Toast.makeText(getApplicationContext(),"Failed" ,Toast.LENGTH_SHORT).show();

            }
        });
    }








    @Override
    public void onBackPressed() {
        if (youTubePlayerView.isFullScreen()) {
            youTubePlayerView.exitFullScreen();
        } else
            super.onBackPressed();
    }
    private void forceLogout() {
        SharedPrefManager.getInstance(this).logout();
        startActivity(new Intent(this, LoginActivity.class));
        Objects.requireNonNull(this).finish();
    }
}