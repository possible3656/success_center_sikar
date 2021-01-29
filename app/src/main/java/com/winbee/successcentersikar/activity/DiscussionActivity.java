package com.winbee.successcentersikar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.winbee.successcentersikar.Fragment.AskFragment;
import com.winbee.successcentersikar.Fragment.McqFragment;
import com.winbee.successcentersikar.R;
import com.winbee.successcentersikar.Utils.SharedPrefManager;

public class DiscussionActivity extends AppCompatActivity {

    TextView txtAsk,txtMcq;
    private boolean onMcqFragment=false;
    private boolean onAskFragment=true;
    private ImageView WebsiteHome,img_share,img_noti;
    String Referal_code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        Referal_code= SharedPrefManager.getInstance(this).refCode().getREFERRAL_CODE();
        txtAsk= findViewById(R.id.txtViewAsk);
        txtMcq= findViewById(R.id.txtViewMcq);
        WebsiteHome=findViewById(R.id.WebsiteHome);
        img_noti=findViewById(R.id.img_noti);
        img_share=findViewById(R.id.img_share);
        WebsiteHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiscussionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        img_noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiscussionActivity.this,NotificationActivity.class);
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


        defalutAskTab(false);

        txtAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!onAskFragment){
               defalutAskTab(true);
                onAskFragment=true;
                onMcqFragment=false;
                }
            }
        });

        txtMcq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!onMcqFragment) {
                    McqTab(true);
                    onMcqFragment=true;
                    onAskFragment=false;
                }
            }
        });

    }

    private void defalutAskTab(boolean addToBackStack) {


        AskFragment askFragment = new AskFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction().replace(R.id.containerDisscussion,askFragment,"AskFragment");

        if (addToBackStack){
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();

    onAskFragment=true;
    }
    private void McqTab(boolean addToBackStack) {


        McqFragment askFragment = new McqFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction().replace(R.id.containerDisscussion,askFragment,"McqFragment");

        if (addToBackStack){
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();

        onMcqFragment=true;
    }

}
