package com.winbee.successcentersikar.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.winbee.successcentersikar.NewModels.PdfContent;
import com.winbee.successcentersikar.NewModels.PdfContentArray;
import com.winbee.successcentersikar.R;
import com.winbee.successcentersikar.RetrofitApiCall.ApiClient;
import com.winbee.successcentersikar.Utils.LocalData;
import com.winbee.successcentersikar.Utils.ProgressBarUtil;
import com.winbee.successcentersikar.Utils.SharedPrefManager;
import com.winbee.successcentersikar.Utils.Variable;
import com.winbee.successcentersikar.WebApi.ClientApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.balsikandar.crashreporter.CrashReporter.getContext;


public class YoutubeLibaray extends AppCompatActivity {
    private static final String TAG = "VimeoActivityforlog";
    private static long LAST_CLICK_TIME = 0;
    private final int mDoubleClickInterval = 400; // Milliseconds
    private float currenttime=0;
    private int forwardtimes=1;
    RecyclerView messageRecyclerView;
    RelativeLayout relativeLayoutLive,title_bar_home,details_layout,relativeBack,relativeForward;
    private ProgressBarUtil progressBarUtil;
    private Button btn_pdf;
    private LinearLayout footer;
    String Referal_code;
    private TextView txt_subject,txt_topic,txt_teacher;
    private ArrayList<PdfContentArray> list;
    YouTubePlayerView youTubePlayerView;
    String UserId,Username,android_id;
    ImageView WebsiteHome,img_share,backimage,forwardImage,img_noti;
    LinearLayout layout_user,layout_test_series,layout_home,layout_doubt,layout_notification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_libaray);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        Referal_code=SharedPrefManager.getInstance(this).refCode().getREFERRAL_CODE();
        title_bar_home = findViewById(R.id.title_bar_home);
        relativeBack = findViewById(R.id.relativeBack);
        relativeForward = findViewById(R.id.relativeForward);
        backimage = findViewById(R.id.backimage);
        forwardImage = findViewById(R.id.forwardImage);
        UserId = SharedPrefManager.getInstance(this).refCode().getUserId();
        Username=SharedPrefManager.getInstance(this).refCode().getName();
        android_id = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        details_layout = findViewById(R.id.details_layout);
        progressBarUtil = new ProgressBarUtil(this);
        relativeLayoutLive = findViewById(R.id.relative_layout_live);
        txt_subject = findViewById(R.id.txt_subject);
        txt_subject.setText("Subject - "+ LocalData.SubjectName);
        txt_topic = findViewById(R.id.txt_topic);
        txt_topic.setText("Topic - "+LocalData.TopicName);
        txt_teacher = findViewById(R.id.txt_teacher);
        txt_teacher.setText("Teacher - "+LocalData.TeacherName);
        footer = findViewById(R.id.footer);
        this.youTubePlayerView = findViewById(R.id.youtube_player);
        getLifecycle().addObserver(youTubePlayerView);
        //backwordForwardGesture();
        layout_user=findViewById(R.id.layout_user);
        layout_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(YoutubeLibaray.this, DashboardCourseActivity.class);
                startActivity(profile);
            }
        });
        img_noti=findViewById(R.id.img_noti);
        img_noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(YoutubeLibaray.this, NotificationActivity.class);
                startActivity(profile);
            }
        });
        layout_test_series=findViewById(R.id.layout_test_series);
        layout_test_series.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent test = new Intent(YoutubeLibaray.this,SubjectActivity.class);
                startActivity(test);
                // Toast.makeText(MainActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        layout_home=findViewById(R.id.layout_home);
        layout_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(YoutubeLibaray.this, MainActivity.class);
                startActivity(profile);
            }
        });
        layout_doubt=findViewById(R.id.layout_doubt);
        layout_doubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(YoutubeLibaray.this, DiscussionActivity.class);
                startActivity(profile);
            }
        });
        layout_notification=findViewById(R.id.layout_notification);
        layout_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(YoutubeLibaray.this, ReferalActivity.class);
                startActivity(profile);
            }
        });
        WebsiteHome=findViewById(R.id.WebsiteHome);
        WebsiteHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YoutubeLibaray.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btn_pdf=findViewById(R.id.btn_pdf);
        btn_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo
                Intent intent = new Intent(YoutubeLibaray.this,StudyMaterial.class);
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
                details_layout.setVisibility(View.GONE);
                // hide softkeys if it is already open

            }

            @Override
            public void onYouTubePlayerExitFullScreen() {
               // Variable.time=youTubePlayerView.getCurrentTimeSeconds();
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
                title_bar_home.setVisibility(View.VISIBLE);
                btn_pdf.setVisibility(View.VISIBLE);
                footer.setVisibility(View.VISIBLE);
                details_layout.setVisibility(View.VISIBLE);
            }
        });
        youTubePlayerView.
                addYouTubePlayerListener(new AbstractYouTubePlayerListener() {


                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                       // youTubePlayerView.seekTo(Variable.time);
                        YouTubePlayerUtils.loadOrCueVideo(
                                youTubePlayer, getLifecycle(),
                                LocalData.VideoUrl, 0f
                        );

//                        CustomPlayerUiController customPlayerUiController = new CustomPlayerUiController(CustomUiActivity.this, customPlayerUi, youTubePlayer, youTubePlayerView);
//                        youTubePlayer.addListener(customPlayerUiController);
                    }


                });
//        YouTubePlayerTracker tracker = new YouTubePlayerTracker();
//        youTubePlayer.addListener(tracker);
//
//        tracker.getState();
//        tracker.getCurrentSecond();
//        tracker.getVideoDuration();
//        tracker.getVideoId();

        // Called every time the quality of the playback changes.
       // void onPlaybackQualityChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlaybackQuality playbackQuality)
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
                list = new ArrayList();
                if(statusCode==200){
                    if (response.body().getError()==false) {
                        System.out.println("Suree body: " + response.body());
                        list = new ArrayList<>(Arrays.asList(Objects.requireNonNull(pdfContent).getData()));
                        //LocalData.PdfUrl=list.get(0).getURL();
                        Log.i("pdf", "onResponse: "+ LocalData.PdfUrl);
                        progressBarUtil.hideProgress();
                    }
                    else{
                        android.app.AlertDialog.Builder alertDialogBuilder =
                                new android.app.AlertDialog.Builder(YoutubeLibaray.this);
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



//    private void backwordForwardGesture() {
//
//        relativeBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Log.d(TAG, "onClick: back");
//                relativeBack.setEnabled(false);
//
//                long doubleClickCurrentTime = System.currentTimeMillis();
//                long currentClickTime = System.currentTimeMillis();
//                if (currentClickTime - LAST_CLICK_TIME <= mDoubleClickInterval)
//                {
//                    backimage.setVisibility(View.VISIBLE);
//                    if (youTubePlayerView.getDrawingTime() >= 10) {
//                        youTubePlayerView.(youTubePlayerView.getCurrentSecond()-10);
//                    }else {
//                        youTubePlayerView.seekTo(0);
//                    }
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            backimage.setVisibility(View.INVISIBLE);
//                            relativeBack.setEnabled(true);
//                        }
//                    },500);
//
//                    currenttime=youTubePlayerView.getDrawingTime()-10;
//                   // currenttime=youTubePlayerView.getCurrentTimeSeconds()-10;
//                  //  Log.d(TAG, "onClick: double tapped  "+vimeoPlayer.getCurrentTimeSeconds()+"   "+currenttime);
//                }
//                else
//                {
//                    LAST_CLICK_TIME = System.currentTimeMillis();
//                    // !Warning, Single click action problem
//                    Log.d(TAG, "onClick: single tap");
//                }
//            }
//        });
//
//
//        relativeForward.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Log.d(TAG, "onClick: for");
//                relativeForward.setEnabled(false);
//
//
//                long doubleClickCurrentTime = System.currentTimeMillis();
//                long currentClickTime = System.currentTimeMillis();
//                if (currentClickTime - LAST_CLICK_TIME <= mDoubleClickInterval)
//                {
//                    //  Toast.makeText(this, "Double click detected", Toast.LENGTH_SHORT).show();
//                    // Toast.makeText(VimeoActivity.this, "double clicked", Toast.LENGTH_SHORT).show();
//
//
//
//                    forwardImage.setVisibility(View.VISIBLE);
//
//
//
//
//                    youTubePlayerView.seekTo(youTubePlayerView.getDrawingTime()+10);
//
//
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            forwardImage.setVisibility(View.INVISIBLE);
//                            relativeForward.setEnabled(true);
//                        }
//                    },500);
//                    currenttime=youTubePlayerView.getDrawingTime()+10;
//                  //  Log.d(TAG, "onClick: double tapped  "+vimeoPlayer.getCurrentTimeSeconds()+"   "+currenttime);
//                }
//
//                else
//
//                {
//                    LAST_CLICK_TIME = System.currentTimeMillis();
//                    Log.d(TAG, "onClick: single tap");
//
//
//                }
//            }
//        });
//
//
//    }





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