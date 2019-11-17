package service;

import model.Weather;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;

import java.util.Map;


public interface ApiService {
    @Headers("X-Yandex-API-Key: b4e803b7-75b1-4c74-be66-a578582d1290")
    @GET("v1/forecast")
    Call<Weather> forecastWithParams(
            @QueryMap Map<String, String> options
    );

    @Headers("X-Yandex-API-Key: b4e803b7-75b1-4c74-be66-a578582d1290")
    @GET("v1/forecast")
    Call<Weather> forecastDefault(
    );
}
