package id.co.wassaph.aladhine.wassaph.helper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

import id.co.wassaph.aladhine.wassaph.service.AlarmReceiver;

/**
 * Created by aladhine on 01/11/17.
 */

public class AlarmHelper {
    private AlarmManager alarmManager;
    private Context mContext;

    public AlarmHelper(Context context){
        this.alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        this.mContext = context;
    }

    public void setAlarm(Calendar calendar, String msg){
        Intent i = new Intent(mContext, AlarmReceiver.class);
        i.putExtra("message", msg);
        PendingIntent pi = PendingIntent.getBroadcast(mContext, 1, i, PendingIntent.FLAG_UPDATE_CURRENT | Intent.FILL_IN_DATA);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi); // Millisec * Second * Minute
    }

    public void cancelAlarm() {
        Intent i = new Intent(mContext, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(mContext, 1, i, 0);
        alarmManager.cancel(sender);
    }

    private void showLog(String msg){
        Log.i("AlarmHelper", msg);
    }
}
