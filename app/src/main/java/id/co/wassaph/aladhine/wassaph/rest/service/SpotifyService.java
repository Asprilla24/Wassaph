package id.co.wassaph.aladhine.wassaph.rest.service;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.QueryMap;

/**
 * Created by aladhine on 02/11/17.
 */

public interface SpotifyService {
    @GET("search")
    Call<ResponseBody> getTrack(@Header("Authorization") String auth, @QueryMap Map<String, String> query);
}
