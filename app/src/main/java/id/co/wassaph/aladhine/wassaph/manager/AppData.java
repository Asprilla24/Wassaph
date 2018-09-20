package id.co.wassaph.aladhine.wassaph.manager;

import ai.api.android.AIConfiguration;

/**
 * Created by aladhine on 01/11/17.
 */

public class AppData {
    //MyName
    final public static String name = "Alde";

    //Spotify token
    private static final String CLIENT_ID = "4daac072e74e4065a4485303189ec9b9";
    private static final String REDIRECT_URI = "wassaph://spotify/callback";
    private static final int REQUEST_CODE = 1337;

    //AI Configuration
    final public static AIConfiguration aiConfig = new AIConfiguration(
            "5566e65c18b54add97e0f76df1b1c983",
            AIConfiguration.SupportedLanguages.English,
            AIConfiguration.RecognitionEngine.System);

    //Message Type
    final public static int textMessage = 0;
    final public static int imageMessage = 1;

    //Alarm
    public static String messageAlarm = "";
    public static String timeAlarm = "";

}
