package service;

import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;
import io.qameta.allure.okhttp3.AllureOkHttp3;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Coordinates {

    private ApiService service;
    public Map<String, Double> innerlon;
    public Map<String, Double> innerlat;

    public Coordinates() {
        final AllureOkHttp3 allureOkHttp3 = new AllureOkHttp3();
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(allureOkHttp3)
                .build();
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://geocode-maps.yandex.ru/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ApiService.class);
        this.innerlon = new HashMap<>();
        this.innerlat = new HashMap<>();
    }

    public void getCorner(String country) throws IOException {
        final Call<JsonObject> mapsCall = service.getCoordinates("ce9de1da-5b6b-496c-b8a9-89fcbd14b314",
                "json", country);
        JsonObject jsonObject = mapsCall.execute().body();
        putCoordinates(jsonObject);
    }

    public void putCoordinates(JsonObject object) {
        parseJson(JsonPath.read(object.toString(),
                "$.response.GeoObjectCollection.featureMember[*].GeoObject.boundedBy.Envelope.lowerCorner")
                .toString(), "lower");
        parseJson(JsonPath.read(object.toString(),
                "$.response.GeoObjectCollection.featureMember[*].GeoObject.boundedBy.Envelope.upperCorner")
                .toString(), "upper");
    }

    public void parseJson (String json, String bounder) {
        Pattern pattern = Pattern.compile("-?\\d*\\.\\d*\\s-?\\d*\\.\\d*");
        Matcher matcher = pattern.matcher(json);
        String[] arr = new String[2];
        while (matcher.find()) {
            arr = matcher.group().split(" ");
        }
        innerlon.put(bounder, Double.parseDouble(arr[0]));
        innerlat.put(bounder, Double.parseDouble(arr[1]));
    }
}
