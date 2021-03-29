package com.winbee.successcentersikar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.winbee.successcentersikar.R;
import com.winbee.successcentersikar.Utils.LocalData;
import com.winbee.successcentersikar.Utils.SharedPrefManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;

public class DownloadVideo extends AppCompatActivity {

    private static final int ITAG_FOR_AUDIO = 140;

    private static String youtubeLink;

    private LinearLayout mainLayout;
    private ProgressBar mainProgressBar;
    private List<YtFragmentedVideo> formatsToShowList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_download_video);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        mainLayout = (LinearLayout) findViewById(R.id.main_layout);
        mainProgressBar = (ProgressBar) findViewById(R.id.prgrBar);


        getYoutubeDownloadUrl(YoutubeLibaray.youtubeLink);
    }

    private void getYoutubeDownloadUrl(String youtubeLink) {
        new YouTubeExtractor(this) {

            @Override
            public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
                mainProgressBar.setVisibility(View.GONE);
                if (ytFiles == null) {
                    TextView tv = new TextView(DownloadVideo.this);
                    tv.setText("Something went wrong.Please try again.");
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    mainLayout.addView(tv);
                    return;
                }
                formatsToShowList = new ArrayList<>();
                for (int i = 0, itag; i < ytFiles.size(); i++) {
                    itag = ytFiles.keyAt(i);
                    YtFile ytFile = ytFiles.get(itag);

                    if (ytFile.getFormat().getHeight() == -1 || ytFile.getFormat().getHeight() >= 144) {
                        addFormatToList(ytFile, ytFiles);
                    }
                }
                Collections.sort(formatsToShowList, new Comparator<YtFragmentedVideo>() {
                    @Override
                    public int compare(YtFragmentedVideo lhs, YtFragmentedVideo rhs) {
                        return lhs.height - rhs.height;
                    }
                });
                for (YtFragmentedVideo files : formatsToShowList) {
                    addButtonToMainLayout(vMeta.getTitle(), files);
                }
            }
        }.extract(youtubeLink, true, false);
    }

    private void addFormatToList(YtFile ytFile, SparseArray<YtFile> ytFiles) {
        int height = ytFile.getFormat().getHeight();
        if (height != -1) {
            for (YtFragmentedVideo frVideo : formatsToShowList) {
                if (frVideo.height == height && (frVideo.videoFile == null ||
                        frVideo.videoFile.getFormat().getFps() == ytFile.getFormat().getFps())) {
                    return;
                }
            }
        }
        YtFragmentedVideo frVideo = new YtFragmentedVideo();
        frVideo.height = height;
        if (ytFile.getFormat().isDashContainer()) {
            if (height > 0) {
                frVideo.videoFile = ytFile;
                frVideo.audioFile = ytFiles.get(ITAG_FOR_AUDIO);
            } else {
                frVideo.audioFile = ytFile;
            }
        } else {
            frVideo.videoFile = ytFile;
        }
        formatsToShowList.add(frVideo);
    }


    private void addButtonToMainLayout(final String videoTitle, final YtFragmentedVideo ytFrVideo) {
        // Display some buttons and let the user choose the format
        String btnText;
        if (ytFrVideo.height == -1)
            btnText = "Audio " + ytFrVideo.audioFile.getFormat().getAudioBitrate() + " kbit/s";
        else
            btnText = (ytFrVideo.videoFile.getFormat().getFps() == 60) ? ytFrVideo.height + "p60" :
                    ytFrVideo.height + "p";
        Button btn = new Button(this);
        btn.setText(btnText);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String filename;
                if (videoTitle.length() > 55) {
                    filename = videoTitle.substring(0, 55);
                } else {
                    filename = videoTitle;
                }
                filename = filename.replaceAll("[\\\\><\"|*?%:#/]", "");
                filename += (ytFrVideo.height == -1) ? "" : "-" + ytFrVideo.height + "p";
                String downloadIds = "";
                boolean hideAudioDownloadNotification = false;
                if (ytFrVideo.videoFile != null) {
                    downloadIds += downloadFromUrl(ytFrVideo.videoFile.getUrl(), videoTitle,
                            filename + ".abc" , false);
                    downloadIds += "-";
                    hideAudioDownloadNotification = true;
                }
                if (ytFrVideo.audioFile != null) {
                    downloadIds += downloadFromUrl(ytFrVideo.audioFile.getUrl(), videoTitle,
                            filename + ".cba", hideAudioDownloadNotification);
                }
                if (ytFrVideo.audioFile != null)
                    cacheDownloadIds(downloadIds);
                finish();
            }
        });
        mainLayout.addView(btn);
    }

    private long downloadFromUrl(String youtubeDlUrl, String downloadTitle, String fileName, boolean hide) {


        Log.d("TAG", "downloadFromUrl: " + Environment.DIRECTORY_DOWNLOADS);
        Uri uri = Uri.parse(youtubeDlUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(downloadTitle);
        if (hide) {
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
            request.setVisibleInDownloadsUi(false);
        } else
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI)
                .setAllowedOverRoaming(true)
                .setDestinationInExternalFilesDir(this,"Download/"+ SharedPrefManager.getInstance(this).refCode().getUsername()+"/"+ LocalData.CourseName +"/"+LocalData.SubjectName, fileName);



        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        return manager.enqueue(request);




    }

    private void cacheDownloadIds(String downloadIds) {
        File dlCacheFile = new File(this.getCacheDir().getAbsolutePath() + "/" + downloadIds);
        try {
            dlCacheFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goback(View view) {
        onBackPressed();
    }

    private class YtFragmentedVideo {
        int height;
        YtFile audioFile;
        YtFile videoFile;
    }
}

