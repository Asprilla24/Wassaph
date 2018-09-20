package id.co.wassaph.aladhine.wassaph.helper;

import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import id.co.wassaph.aladhine.wassaph.manager.AppData;
import id.co.wassaph.aladhine.wassaph.model.MessageModel;
import id.co.wassaph.aladhine.wassaph.rest.ApiUtils;
import id.co.wassaph.aladhine.wassaph.rest.service.SpotifyService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by aladhine on 02/11/17.
 */

public class SpotifyHelper {
    private String track;

//    public String searchSong(String title){
//        track = null;
//
//        SpotifyService spotifyService = ApiUtils.getSpotifyService();
//        String auth = "Bearer " + AppData.tokenSpotify;
//        Map<String, String> query = new HashMap<>();
//        query.put("q", title);
//        query.put("type", "track");
//
//        Call<ResponseBody> call = spotifyService.getTrack(auth, query);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    if (response.isSuccessful()) {
//                        String responseBody = response.body().string();
//                        Log.d("getSong", responseBody);
//
//                        JSONObject mResult = new JSONObject(responseBody);
//                        JSONObject mTrack = mResult.getJSONObject("tracks").getJSONArray("items").getJSONObject(0);
//
//                        track = mTrack.getString("uri");
//                    } else {
//                        String responseBody = response.errorBody().string();
//                        Log.d("getSong", responseBody);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
//
//        return track;
//    }
}
