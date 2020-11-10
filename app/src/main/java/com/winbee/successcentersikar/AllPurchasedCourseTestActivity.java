package com.winbee.successcentersikar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class AllPurchasedCourseTestActivity extends AppCompatActivity {

    TextView txtViewDailyQuiz,txtViewDiscussion;
    private boolean onTestFragment=false;
    private boolean onCourseFragment=true;
    private ImageView WebsiteHome,img_share;
    private View view_quiz,view_discussion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_purchased_course_test);
     //   getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        txtViewDailyQuiz= findViewById(R.id.txtViewDailyQuiz);
        txtViewDiscussion= findViewById(R.id.txtViewDiscussion);
        WebsiteHome=findViewById(R.id.WebsiteHome);
        view_quiz=findViewById(R.id.view_quiz);
        view_discussion=findViewById(R.id.view_discussion);
        img_share=findViewById(R.id.img_share);
        WebsiteHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AllPurchasedCourseTestActivity.this,MainActivity.class);
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


        defalutDailyQuizTab(false);

        txtViewDailyQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!onCourseFragment){
                    defalutDailyQuizTab(true);
                    view_quiz.setVisibility(View.VISIBLE);
                    view_discussion.setVisibility(View.GONE);
                    onCourseFragment=true;
                    onTestFragment=false;
                }
            }
        });

        txtViewDiscussion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!onTestFragment) {
                    DiscussionFragment(true);
                    view_quiz.setVisibility(View.GONE);
                    view_discussion.setVisibility(View.VISIBLE);
                    onTestFragment=true;
                    onCourseFragment=false;
                }
            }
        });

    }

    private void defalutDailyQuizTab(boolean addToBackStack) {


        CourseFragment quizFragment = new CourseFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction().replace(R.id.containerCourse,quizFragment,"CourseFragment");

        if (addToBackStack){
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();

        onCourseFragment=true;
    }
    private void DiscussionFragment(boolean addToBackStack) {


        TestFragment discussionFragment = new TestFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction().replace(R.id.containerCourse,discussionFragment,"TestFragment");

        if (addToBackStack){
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();

        onTestFragment=true;
    }

}
