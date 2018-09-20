package id.co.wassaph.aladhine.wassaph.rest.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by aladhine on 02/11/17.
 */

public interface QuotesService {
    @GET("quotes/random")
    Call<ResponseBody> getQOD();
}
