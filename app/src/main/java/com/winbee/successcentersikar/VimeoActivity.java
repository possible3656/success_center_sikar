package com.winbee.successcentersikar;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ct7ct7ct7.androidvimeoplayer.listeners.VimeoPlayerReadyListener;
import com.ct7ct7ct7.androidvimeoplayer.model.TextTrack;
import com.ct7ct7ct7.androidvimeoplayer.view.VimeoPlayerView;

public class VimeoActivity extends AppCompatActivity {
    private static final String TAG = "VimeoActivityforlog";
    VimeoPlayerView vimeoPlayer;
    private RelativeLayout title_bar_home;
    LinearLayout footer;
    ImageView WebsiteHome, img_share, exitscreen_button;
    ImageView fullscreen_button;
    Button btn_pdf;
    TextView txt_subject,txt_topic,txt_teacher;
    private static long LAST_CLICK_TIME = 0;
    private final int mDoubleClickInterval = 400; // Milliseconds



    LinearLayout layout_user, layout_test_series, layout_home, layout_doubt, layout_notification;
    private RelativeLayout relativeBack,relativeForward,details_layout;
    private ImageView backimage,forwardImage;
    private float currenttime=0;
    private int forwardtimes=1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_vimeo);



        vimeoPlayer = findViewById(R.id.vimeoPlayer);
        title_bar_home = findViewById(R.id.title_bar_home);
        details_layout = findViewById(R.id.details_layout);
        footer = findViewById(R.id.footer);
        WebsiteHome = findViewById(R.id.WebsiteHome);
        btn_pdf = findViewById(R.id.btn_pdf);
        btn_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(VimeoActivity.this,StudyMaterial.class);
                startActivity(intent);
                finish();
            }
        });
        fullscreen_button = findViewById(R.id.fullscreen_button);
        exitscreen_button = findViewById(R.id.exitscreen_button);
        txt_subject = findViewById(R.id.txt_subject);
        txt_subject.setText("Subject - "+LocalData.SubjectName);
        txt_topic = findViewById(R.id.txt_topic);
        txt_topic.setText("Topic - "+LocalData.TopicName);
        txt_teacher = findViewById(R.id.txt_teacher);
        txt_teacher.setText("Teacher - "+LocalData.TeacherName);

        Log.d(TAG, "onCreate: not in full screen" + Variable.fullscreen);


        vimeoPlayer.addReadyListener(new VimeoPlayerReadyListener() {
            @Override
            public void onReady(String title, float duration, TextTrack[] textTrackArray) {
                vimeoPlayer.seekTo(Variable.time);
            }

            @Override
            public void onInitFailed() {

            }
        });


        layoutSettings();
        //   backwordForwardGesture();
        gesture();
        // ek bar run rte he
        //ok




        fullscreen_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Variable.fullscreen = true;
                Variable.time=vimeoPlayer.getCurrentTimeSeconds();
                Log.d(TAG, "onClick: now o full screen" + Variable.fullscreen +" "+Variable.time);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) vimeoPlayer.getLayoutParams();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                vimeoPlayer.setLayoutParams(params);
                title_bar_home.setVisibility(View.GONE);
                footer.setVisibility(View.GONE);
                fullscreen_button.setVisibility(View.GONE);
                exitscreen_button.setVisibility(View.VISIBLE);
                vimeoPlayer.seekTo(Variable.time);


            }
        });


        exitscreen_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Variable.fullscreen = false;
                Variable.time=vimeoPlayer.getCurrentTimeSeconds();
                Log.d(TAG, "onClick: back to normal screen" + Variable.fullscreen+"  "+Variable.time);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) vimeoPlayer.getLayoutParams();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                vimeoPlayer.setLayoutParams(params);
                title_bar_home.setVisibility(View.VISIBLE);
                fullscreen_button.setVisibility(View.VISIBLE);
                exitscreen_button.setVisibility(View.GONE);
                vimeoPlayer.seekTo(Variable.time);

            }
        });
        WebsiteHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VimeoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        img_share = findViewById(R.id.img_share);
        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Success Center Sikar");
                    String shareMessage = "\nSuccess Center Sikar download the application.\n ";
                    shareMessage = shareMessage + "\nhttps://play.google.com/store/apps/details?id=" + getPackageName();

                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                }
            }
        });
        setupView();


    }

    private void gesture() {




    }

    private void backwordForwardGesture() {

        relativeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "onClick: back");
                relativeBack.setEnabled(false);

                long doubleClickCurrentTime = System.currentTimeMillis();
                long currentClickTime = System.currentTimeMillis();
                if (currentClickTime - LAST_CLICK_TIME <= mDoubleClickInterval)
                {
                    backimage.setVisibility(View.VISIBLE);
                    if (vimeoPlayer.getCurrentTimeSeconds() >= 10) {
                        vimeoPlayer.seekTo(vimeoPlayer.getCurrentTimeSeconds()-10);
                    }else {
                        vimeoPlayer.seekTo(0);
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            backimage.setVisibility(View.INVISIBLE);
                            relativeBack.setEnabled(true);
                        }
                    },500);

                    currenttime=vimeoPlayer.getCurrentTimeSeconds()-10;
                    Log.d(TAG, "onClick: double tapped  "+vimeoPlayer.getCurrentTimeSeconds()+"   "+currenttime);
                }
                else
                {
                    LAST_CLICK_TIME = System.currentTimeMillis();
                    // !Warning, Single click action problem
                    Log.d(TAG, "onClick: single tap");
                }
            }
        });


        relativeForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "onClick: for");
                relativeForward.setEnabled(false);


                long doubleClickCurrentTime = System.currentTimeMillis();
                long currentClickTime = System.currentTimeMillis();
                if (currentClickTime - LAST_CLICK_TIME <= mDoubleClickInterval)
                {
                    //  Toast.makeText(this, "Double click detected", Toast.LENGTH_SHORT).show();
                    // Toast.makeText(VimeoActivity.this, "double clicked", Toast.LENGTH_SHORT).show();



                    forwardImage.setVisibility(View.VISIBLE);




                    vimeoPlayer.seekTo(vimeoPlayer.getCurrentTimeSeconds()+10);


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            forwardImage.setVisibility(View.INVISIBLE);
                            relativeForward.setEnabled(true);
                        }
                    },500);
                    currenttime=vimeoPlayer.getCurrentTimeSeconds()+10;
                    Log.d(TAG, "onClick: double tapped  "+vimeoPlayer.getCurrentTimeSeconds()+"   "+currenttime);
                }

                else

                {
                    LAST_CLICK_TIME = System.currentTimeMillis();
                    Log.d(TAG, "onClick: single tap");


                }
            }
        });


    }


    private void layoutSettings() {

        if (Variable.fullscreen) {
            title_bar_home.setVisibility(View.GONE);
            footer.setVisibility(View.GONE);
            details_layout.setVisibility(View.GONE);
            fullscreen_button.setVisibility(View.GONE);
            exitscreen_button.setVisibility(View.VISIBLE);
            vimeoPlayer.seekTo(Variable.time);
            Log.d(TAG, "layoutSettings: "+Variable.time);
        }else {
            title_bar_home.setVisibility(View.VISIBLE);
            fullscreen_button.setVisibility(View.VISIBLE);
            footer.setVisibility(View.VISIBLE);
            details_layout.setVisibility(View.VISIBLE);
            exitscreen_button.setVisibility(View.GONE);
            vimeoPlayer.seekTo(Variable.time);
            Log.d(TAG, "layoutSettings: "+Variable.time);
        }
    }


    private void setupView() {
        getLifecycle().addObserver(vimeoPlayer);
        // vimeoPlayer.initialize(Integer.parseInt(LocalData.VideoUrl));
        vimeoPlayer.initialize(441740538);//441740538
        vimeoPlayer.setFullscreenVisibility(true);
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: " + Variable.fullscreen);
        if (Variable.fullscreen) {

            Log.d(TAG, "onBackPressed: swithcing to noraml");
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) vimeoPlayer.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            fullscreen_button.setVisibility(View.VISIBLE);
            exitscreen_button.setVisibility(View.GONE);
            params.height = (int) (200 * getApplicationContext().getResources().getDisplayMetrics().density);
            vimeoPlayer.setLayoutParams(params);
            Variable.fullscreen = false;
        } else {
            Log.d(TAG, "onBackPressed: on full screen");

            if (!Variable.fullscreen) {
                Log.d(TAG, "onBackPressed: goinfg to prevoucs activity");
                super.onBackPressed();
                return;
            }



        }

    }
}

