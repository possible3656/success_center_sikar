package com.winbee.successcentersikar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity {

    TextView txtViewDailyQuiz,txtViewDiscussion;
    private boolean onDiscussionFragment=false;
    private boolean onQuizFragment=true;
    private ImageView WebsiteHome,img_share;
    private View view_quiz,view_discussion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        txtViewDailyQuiz= findViewById(R.id.txtViewDailyQuiz);
        txtViewDiscussion= findViewById(R.id.txtViewDiscussion);
        WebsiteHome=findViewById(R.id.WebsiteHome);
        view_quiz=findViewById(R.id.view_quiz);
        view_discussion=findViewById(R.id.view_discussion);
        img_share=findViewById(R.id.img_share);
        WebsiteHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizActivity.this,MainActivity.class);
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
                if (!onQuizFragment){
                    defalutDailyQuizTab(true);
                    view_quiz.setVisibility(View.VISIBLE);
                    view_discussion.setVisibility(View.GONE);
                    onQuizFragment=true;
                    onDiscussionFragment=false;
                }
            }
        });

        txtViewDiscussion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!onDiscussionFragment) {
                    DiscussionFragment(true);
                    view_quiz.setVisibility(View.GONE);
                    view_discussion.setVisibility(View.VISIBLE);
                    onDiscussionFragment=true;
                    onQuizFragment=false;
                }
            }
        });

    }

    private void defalutDailyQuizTab(boolean addToBackStack) {


        QuizFragment quizFragment = new QuizFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction().replace(R.id.containerQuiz,quizFragment,"QuizFragment");

        if (addToBackStack){
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();

        onQuizFragment=true;
    }
    private void DiscussionFragment(boolean addToBackStack) {


        DiscussionFragment discussionFragment = new DiscussionFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction().replace(R.id.containerQuiz,discussionFragment,"DiscussionFragment");

        if (addToBackStack){
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();

        onDiscussionFragment=true;
    }

}
