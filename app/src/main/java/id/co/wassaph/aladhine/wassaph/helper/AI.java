package id.co.wassaph.aladhine.wassaph.helper;

import android.content.Context;

import java.util.Calendar;

import id.co.wassaph.aladhine.wassaph.manager.AppData;

/**
 * Created by aladhine on 01/11/17.
 */

public class AI {
    private Context mContext;

    public AI(Context context){
        this.mContext = context;
    }

    public boolean isAlarm(String message){
        String[] msg = message.split(" ");
        String mMessage = "";
        String mTime = "";
        Boolean isAlarm = false;

        for(int i = 0; i < msg.length; i++){
            if(msg[i].contains("bangunin") || msg[i].contains("ingetin")
                    || msg[i].contains("tangikne") || msg[i].contains("gugahen")){
                mMessage = msg[i];
            } else if (msg[i].contains("jam")){
                mTime = msg[i+1];

                if(msg[i+2].contains("malem") || msg[i+2].contains("siang") || msg[i+2].contains("sore")){
                    int hour = Integer.parseInt(mTime.substring(0,2)) + 12;
                    int minute = Integer.parseInt(mTime.substring(2,4));
                    mTime = hour + ":" + minute;
                }
            }
        }

        mMessage = "Yang, udah jam " + mTime + " .Katanya suruh " + mMessage;

        AppData.messageAlarm = mMessage;
        AppData.timeAlarm = mTime;
        isAlarm = (!mMessage.equals("") && !mTime.equals(""));

        if(isAlarm)
            setAlarm(mMessage, mTime);

        return isAlarm;
    }

    public void setAlarm(String msg, String time) {
        int hour = Integer.parseInt(time.substring(0, 2));
        int minute = Integer.parseInt(time.substring(3, 5));

        //Set waktu sholat ke calendar
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        //Set Alarm waktu sholat sekarang
        AlarmHelper alarmHelper = new AlarmHelper(mContext);
        alarmHelper.setAlarm(calendar, msg);
    }
}
