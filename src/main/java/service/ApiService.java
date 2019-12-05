package service;

import com.google.gson.JsonObject;
import model.Weather;
import retrofit2.Call;
import retrofit2.http.*;
import java.util.Map;


public interface ApiService {
    @Headers("X-Yandex-API-Key: b4e803b7-75b1-4c74-be66-a578582d1290")
    @GET("v1/forecast")
    Call<Weather> forecastWithParams(
            @QueryMap Map<String, String> options
    );

    @Headers("X-Yandex-API-Key: b4e803b7-75b1-4c74-be66-a578582d1290")
    @GET("v1/forecast")
    Call<Weather> forecastWithCoord(
            @QueryMap Map<String, Double> options
    );

    @Headers("X-Yandex-API-Key: b4e803b7-75b1-4c74-be66-a578582d1290")
    @GET("v1/forecast")
    Call<Weather> forecastDefault(
    );


    @GET("1.x")
    Call<JsonObject> getCoordinates(
           @Query("apikey") String apikey,
           @Query("format") String format,
           @Query("geocode") String geocode
    );
}
