package id.co.wassaph.aladhine.wassaph.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import id.co.wassaph.aladhine.wassaph.MainActivity;
import id.co.wassaph.aladhine.wassaph.R;
import id.co.wassaph.aladhine.wassaph.database.MessageRealmHelper;
import id.co.wassaph.aladhine.wassaph.database.MessageRealmObject;
import id.co.wassaph.aladhine.wassaph.manager.AppData;
import id.co.wassaph.aladhine.wassaph.model.MessageModel;
import io.realm.Realm;

/**
 * Created by aladhine on 01/11/17.
 */

public class AlarmService extends IntentService {

    private Realm realm;
    private MessageRealmHelper helper;
    private NotificationManager alarmNotificationManager;

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        realm = Realm.getDefaultInstance();
        helper = new MessageRealmHelper(realm);
        String message = intent.getExtras().getString("message");

        sendMessage(message);

        sendNotification(message);

        AlarmReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(String msg) {
        Log.d("AlarmService", "Preparing to send notification...: " + msg);
        alarmNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Notification alamNotificationBuilder = new Notification.Builder(this)
                .setContentTitle("Dhea")
                .setContentText(msg)
                .setContentIntent(contentIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new Notification.BigTextStyle().bigText(msg))
                .setContentText(msg)
                .setSound(defaultSoundUri)
                .build();

        alarmNotificationManager.notify(1, alamNotificationBuilder);
        Log.d("AlarmService", "Notification sent.");
    }

    private void sendMessage(String msg){
        MessageModel dhea = new MessageModel(
                msg
                , "Dhea"
                , AppData.textMessage);
        helper.addMessage(dhea);
    }
}
