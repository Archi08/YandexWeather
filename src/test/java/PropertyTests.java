import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import generators.CountryGenerator;
import generators.TurkGenerator;
import io.qameta.allure.okhttp3.AllureOkHttp3;
import model.Weather;
import okhttp3.OkHttpClient;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import service.ApiService;
import service.Coordinates;
import service.LogForecast;
import java.io.IOException;
import java.util.HashMap;

@RunWith(JUnitQuickcheck.class)
public class PropertyTests {
    private ApiService service;
    public static Coordinates coordinates;
    public LogForecast log;

    @Before public void init() {
        final AllureOkHttp3 allureOkHttp3 = new AllureOkHttp3();
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(allureOkHttp3)
                .build();
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weather.yandex.ru/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ApiService.class);
        log = new LogForecast();
    }



    @Property(trials = 1)
    public void testOneCountry(@From(TurkGenerator.class) HashMap data) throws IOException {
        final Call<Weather> ownerCall = service.forecastWithCoord(data);
        final Weather weather = ownerCall.execute().body();
        log.outputWeather(weather, "Турция");
        Assertions.assertEquals(String.format("https://yandex.ru/pogoda/?lat=%s&lon=%s", data.get("lat").toString(),
                data.get("lon").toString()), weather.info.url);
    }


    @Property
    public void testAllCountry(@From(CountryGenerator.class) HashMap data)  throws IOException {
        final Call<Weather> ownerCall = service.forecastWithCoord(data);
        final Weather weather = ownerCall.execute().body();
        log.outputWeather(weather, CountryGenerator.land);
        Assertions.assertEquals(String.format("https://yandex.ru/pogoda/?lat=%s&lon=%s", data.get("lat").toString(),
                data.get("lon").toString()), weather.info.url);
    }

}
