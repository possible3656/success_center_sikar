package com.winbee.successcentersikar.Utils;

import android.app.PendingIntent;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.JsonIOException;
import com.winbee.successcentersikar.MainActivity;
import com.winbee.successcentersikar.R;

import org.json.JSONException;
import org.json.JSONObject;

public class FcmMessagingService extends FirebaseMessagingService {
    String type="";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size()>0){
            type="json";
            sendNotification(remoteMessage.getData().toString());
        }
        if (remoteMessage.getNotification()!=null){
            type="message";
            sendNotification(remoteMessage.getNotification().getBody());
        }
    }

    private void sendNotification(String messageBdy) {
        String id="",message="",title="";
        if (type.equals("json")){
            try{
                JSONObject jsonObject = new JSONObject(messageBdy);
                id=jsonObject.getString("id");
                message=jsonObject.getString("message");
                title=jsonObject.getString("title");
            }catch (JSONException e){

            }

        }else if (type.equals("message")){
            message=messageBdy;
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notificationBulider = new NotificationCompat.Builder(this);
        notificationBulider.setContentTitle(getString(R.string.app_name));

    }
}
