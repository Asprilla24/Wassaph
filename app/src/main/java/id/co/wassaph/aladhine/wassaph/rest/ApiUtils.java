package id.co.wassaph.aladhine.wassaph.rest;

import id.co.wassaph.aladhine.wassaph.rest.service.QuotesService;
import id.co.wassaph.aladhine.wassaph.rest.service.SpotifyService;

/**
 * Created by aladhine on 02/11/17.
 */

public class ApiUtils {
    public static final String BASE_URL_QUOTES = "https://talaikis.com/api/";
    public static final String BASE_URL_SPOTIFY = "https://api.spotify.com/v1/";

    public static QuotesService getQuotesService() {
        return ApiClient.getClient(BASE_URL_QUOTES).create(QuotesService.class);
    }

    public static SpotifyService getSpotifyService(){
        return ApiClient.getClient(BASE_URL_SPOTIFY).create(SpotifyService.class);
    }
}
