package id.co.wassaph.aladhine.wassaph;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import ai.api.AIDataService;
import ai.api.AIListener;
import ai.api.AIServiceException;
import ai.api.android.AIService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.wassaph.aladhine.wassaph.adapter.MessageAdapter;
import id.co.wassaph.aladhine.wassaph.database.MessageRealmHelper;
import id.co.wassaph.aladhine.wassaph.helper.AI;
import id.co.wassaph.aladhine.wassaph.helper.AlarmHelper;
import id.co.wassaph.aladhine.wassaph.helper.SpotifyHelper;
import id.co.wassaph.aladhine.wassaph.manager.AppData;
import id.co.wassaph.aladhine.wassaph.model.MessageModel;
import id.co.wassaph.aladhine.wassaph.rest.ApiUtils;
import id.co.wassaph.aladhine.wassaph.rest.service.QuotesService;
import io.realm.Realm;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AIListener {

    @BindView(R.id.recyclerMessage)
    RecyclerView recyclerMessage;
    @BindView(R.id.editMessage)
    EditText editMessage;
    @BindView(R.id.btnSend)
    ImageView btnSend;

    private final int REQ_CODE_SPEECH_INPUT = 100;
    private int REQ_CODE_VN = 0;

    private MessageAdapter mAdapter;
    private List<MessageModel> messageList;
    private MessageRealmHelper helper;
    private SpotifyHelper spotifyHelper;

    private Realm realm;

    private TextToSpeech t1;
    private AIService aiService;
    private AIDataService aiDataService;
    private AIRequest aiRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        helper = new MessageRealmHelper(realm);
        spotifyHelper = new SpotifyHelper();

        //get message from local database
        messageList = helper.getListMessage();

        //set vn
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != TextToSpeech.ERROR)
                    t1.setLanguage(Locale.getDefault());
            }
        });

        //init recyclerview
        initRecyclerView();

        //check message textbox
        textChangedListener();

        //set AI Service
        initAIService();
    }

    private void initRecyclerView(){
        mAdapter = new MessageAdapter(MainActivity.this, messageList, AppData.name);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerMessage.setLayoutManager(mLayoutManager);
        recyclerMessage.setItemAnimator(new DefaultItemAnimator());
        recyclerMessage.scrollToPosition(messageList.size()-1);
        recyclerMessage.setNestedScrollingEnabled(true);
        recyclerMessage.setVerticalScrollBarEnabled(false);
        recyclerMessage.setAdapter(mAdapter);
    }

    private void textChangedListener(){
        editMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!TextUtils.isEmpty(editMessage.getText().toString())){
                    btnSend.setBackground(getResources().getDrawable(R.drawable.button_send));
                }else{
                    btnSend.setBackground(getResources().getDrawable(R.drawable.button_mic));
                }
            }
        });
    }

    private void initAIService(){
        aiService = AIService.getService(this, AppData.aiConfig);
        aiService.setListener(this);
        aiDataService = new AIDataService(AppData.aiConfig);
        aiRequest = new AIRequest();
    }

    @OnClick(R.id.btnSend)
    public void onViewClicked() {
        String message = editMessage.getText().toString().trim();

        editMessage.setText(null);

        if(message.equals(""))
            promptSpeechInput();
        else
            sendMessage(message);

        //editMessage.setText(spotifyHelper.searchSong(message));
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say Something");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext()
                    , "Sorry! Your device doesn\'t support speech input"
                    ,  Toast.LENGTH_SHORT).show();
        }
    }

    private void playSong(String url){
        String uri = url;
        Intent launcher = new Intent( Intent.ACTION_VIEW, Uri.parse(uri) );
        startActivity(launcher);
    }

    private void showMotivation() {
        QuotesService quotesService = ApiUtils.getQuotesService();
        Call<ResponseBody> call = quotesService.getQOD();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        String responseBody = response.body().string();
                        Log.d("getQOD", responseBody);

                        JSONObject mQuotes = new JSONObject(responseBody);
                        String quotes = mQuotes.getString("quote");
                        String author = mQuotes.getString("author");

                        String result = quotes + " - " + author;

                        MessageModel dhea = new MessageModel(
                                result
                                , "Dhea"
                                , AppData.textMessage);
                        helper.addMessage(dhea);
                        refreshView();
                    } else {
                        String responseBody = response.errorBody().string();
                        Log.d("getQuotes", responseBody);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private void sendMessage(String message){
        MessageModel alde = new MessageModel(message, AppData.name, AppData.textMessage);
        helper.addMessage(alde);

        refreshView();

        AI aI = new AI(getApplicationContext());

        if(aI.isAlarm(message)){
            MessageModel dhea = new MessageModel(
                    "Siap yang, Jam " + AppData.timeAlarm + " yaa"
                    , "Dhea"
                    , AppData.textMessage);
            helper.addMessage(dhea);
            refreshView();
        } else {
            getResultAI(message);
        }
    }

    public void getResultAI(String message){
        aiRequest.setQuery(message);
        new AsyncTask<AIRequest,Void,AIResponse>(){

            @Override
            protected AIResponse doInBackground(AIRequest... aiRequests) {
                final AIRequest request = aiRequests[0];
                try {
                    final AIResponse response = aiDataService.request(aiRequest);
                    return response;
                } catch (AIServiceException e) {
                }
                return null;
            }
            @Override
            protected void onPostExecute(AIResponse response) {
                if (response != null) {

                    Result result = response.getResult();
                    String reply = result.getFulfillment().getSpeech();
                    final String action = response.getResult().getAction();
                    MessageModel dhea = new MessageModel(
                            reply
                            , "Dhea"
                            , action.equals("show.image") ? AppData.imageMessage : AppData.textMessage);
                    helper.addMessage(dhea);

                    if(REQ_CODE_VN == 1){
                        REQ_CODE_VN = 0;
                        speech(reply);
                    }

                    refreshView();

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 5s = 5000ms
                            if(action.equals("play.song")){
                                playSong("spotify:track:0CcIhzC8gq1GNzYgvPsBvQ");
                            }else if(action.equals("show.motivation")){
                                showMotivation();
                            }
                        }
                    }, 2000);
                }
            }
        }.execute(aiRequest);
    }

    public void refreshView(){
        messageList.clear();
        messageList.addAll(helper.getListMessage());
        mAdapter.notifyDataSetChanged();
        recyclerMessage.smoothScrollToPosition(messageList.size());
    }

    public void speech(String message){
        t1.speak(message, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onResult(ai.api.model.AIResponse response) {

        Result result = response.getResult();

        String message = result.getResolvedQuery();
        MessageModel alde = new MessageModel(message, AppData.name, AppData.textMessage);
        //ref.child("chat").push().setValue(chatMessage0);
        helper.addMessage(alde);


        String reply = result.getFulfillment().getSpeech();
        MessageModel dhea = new MessageModel(reply, "Dhea", AppData.textMessage);
        //ref.child("chat").push().setValue(chatMessage);
        helper.addMessage(dhea);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    REQ_CODE_VN = 1;

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    sendMessage(result.get(0));
                }
                break;
            }

        }
    }

    @Override
    public void onError(ai.api.model.AIError error) {

    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }
}
