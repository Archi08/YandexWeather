import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import generators.CountryGenerator;
import io.qameta.allure.okhttp3.AllureOkHttp3;
import model.Weather;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import service.ApiService;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Retrofit Tests")
class RetrofitTests {
    private ApiService service;
    private CountryGenerator countryGenerator = new CountryGenerator();

    @BeforeAll
    public void setUpApiClient() {
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
    }

// ДЛЯ ЗАПУСКА РЕТРОФИТ ТЕСТОВ ОТКЛЮЧИТЬ ТАСКУ В build.gradle
// test {
//    useJUnit()
//}
    @Test
    @DisplayName("Получение из ответа код 200")
    void apiResponse() throws IOException {
        System.out.println(countryGenerator.getRandomCountry());
        final Call<Weather> ownerCall = service.forecastDefault();
        Assertions.assertEquals(200, ownerCall.execute().code());
    }

    @Test
    @DisplayName("Запрос без параметров")
    void defaultRequest() throws IOException {
        final Call<Weather> ownerCall = service.forecastDefault();
        final Weather weather = ownerCall.execute().body();
        Assertions.assertNotNull(weather);
        Assertions.assertEquals("https://yandex.ru/pogoda/moscow", weather.info.url);
        // Количество дней
        Assertions.assertEquals(7, weather.forecasts.size());
        // Количество прогнозов по времени суток
        Assertions.assertEquals(6, weather.forecasts.get(0).parts.size());
        // Количество прогнозов по каждому часу
        Assertions.assertEquals(24, weather.forecasts.get(0).hours.size());
    }

    @Test
    @DisplayName("Запрос с параметрами")
    void paramsRequest() throws IOException {
        Map<String, String> data = new HashMap<>();
        data.put("lat", "66.3422");
        data.put("lon", "55.3422");
        data.put("hours", "false");
        data.put("extra", "true");
        data.put("limit", "2");
        data.put("lang", "en_US");
        final Call<Weather> ownerCall = service.forecastWithParams(data);
        final Weather weather = ownerCall.execute().body();
        Assertions.assertNotNull(weather);
        Assertions.assertEquals("https://yandex.com/pogoda/?lat=66.3422&lon=55.3422", weather.info.url);
        // Прогнозы по часам отключены
        Assertions.assertNull(weather.forecasts.get(0).hours);
        // Появились доп поля после включения флага "extra"
        Assertions.assertNotEquals(0.0, weather.forecasts.get(0).parts.get("night").prec_type);
        Assertions.assertNotEquals(0.0, weather.forecasts.get(0).parts.get("night").prec_strength);
        // Количество дней
        Assertions.assertEquals(2, weather.forecasts.size());
    }

    @Test
    @DisplayName("Запрос с некорректными параметрами")
    void badRequest() throws IOException {
        Map<String, String> data = new HashMap<>();
        data.put("lat",   "test");
        data.put("lon",   "test");
        data.put("hours", "test");
        data.put("lang",  "test");
        data.put("extra", "test");
        data.put("limit", "test");
        final Call<Weather> ownerCall = service.forecastWithParams(data);
        final Weather weather = ownerCall.execute().body();
        Assertions.assertEquals("https://yandex.com/pogoda/moscow", weather.info.url);
        // Количество дней
        Assertions.assertEquals(7, weather.forecasts.size());
        // Количество прогнозов по времени суток
        Assertions.assertEquals(6, weather.forecasts.get(0).parts.size());
        // Отсутствие доп полей по флагу extra
        Assertions.assertEquals(0.0, weather.forecasts.get(0).parts.get("night").prec_type);
        Assertions.assertEquals(0.0, weather.forecasts.get(0).parts.get("night").prec_strength);
        // Количество прогнозов по каждому часу
        Assertions.assertEquals(24, weather.forecasts.get(0).hours.size());
    }

    @ParameterizedTest
    @DisplayName("Проверка структуры времени суток")
    @ValueSource(strings = {"night", "day_short", "evening", "day", "night_short", "morning"})
    void partsStructure(String value) throws IOException {
        final Call<Weather> ownerCall = service.forecastDefault();
        final Weather weather = ownerCall.execute().body();
        Assertions.assertTrue(weather.forecasts.get(0).parts.containsKey(value));
    }

    @ParameterizedTest
    @DisplayName("Проверка граничных значений для lon")
    @ValueSource(strings = {"-181.998", "181.5674"})
    void valueLonCoordinates(String value) throws IOException {
        Map<String, String> data = new HashMap<>();
        data.put("lat", "55.987");
        data.put("lon", value);
        final Call<Weather> ownerCall = service.forecastWithParams(data);
        final Weather weather = ownerCall.execute().body();
        Assertions.assertEquals("https://yandex.ru/pogoda/moscow", weather.info.url);
    }

    @ParameterizedTest
    @DisplayName("Проверка граничных значений для lat")
    @ValueSource(strings = {"-91.998", "91.5674"})
    void valueLatCoordinates(String value) throws IOException {
        Map<String, String> data = new HashMap<>();
        data.put("lat", value);
        data.put("lon", "55.231");
        final Call<Weather> ownerCall = service.forecastWithParams(data);
        final Weather weather = ownerCall.execute().body();
        Assertions.assertEquals("https://yandex.ru/pogoda/moscow", weather.info.url);
    }

    @ParameterizedTest
    @DisplayName("Проверка граничных значений для limit")
    @ValueSource(strings = {"0", "8"})
    void valueDayLimit(String value) throws IOException {
        Map<String, String> data = new HashMap<>();
        data.put("limit", value);
        final Call<Weather> ownerCall = service.forecastWithParams(data);
        final Weather weather = ownerCall.execute().body();
        Assertions.assertEquals(7, weather.forecasts.size());
    }

    @ParameterizedTest
    @DisplayName("Проверка значений параметра lang")
    @ValueSource(strings = {"ru_RU", "ru_UA", "uk_UA", "be_BY", "kk_KZ"})
    void domenZone(String value) throws IOException {
        Map<String, String> data = new HashMap<>();
        data.put("lang", value);
        final Call<Weather> ownerCall = service.forecastWithParams(data);
        final Weather weather = ownerCall.execute().body();
        Assertions.assertEquals(String.format("https://yandex.%s/pogoda/moscow", value.substring(3).toLowerCase()), weather.info.url);
    }

    @Test
    @DisplayName("Проверка Турецкой доменной зоны")
    void TRDomenZone() throws IOException {
        Map<String, String> data = new HashMap<>();
        data.put("lang", "tr_TR");
        final Call<Weather> ownerCall = service.forecastWithParams(data);
        final Weather weather = ownerCall.execute().body();
        Assertions.assertEquals("https://yandex.com.tr/hava/moscow", weather.info.url);
    }

    @Test
    @DisplayName("Запрос с параметрами hours и extra")
    void paramRequest() throws IOException {
        Map<String, String> data = new HashMap<>();
        data.put("hours", "true");
        data.put("extra", "false");
        final Call<Weather> ownerCall = service.forecastWithParams(data);
        final Weather weather = ownerCall.execute().body();
        // Прогнозы по часам влкючен
        Assertions.assertEquals(24, weather.forecasts.get(0).hours.size());
        // Выключены доп поля флага "extra"
        Assertions.assertEquals(0.0, weather.forecasts.get(0).parts.get("night").prec_type);
        Assertions.assertEquals(0.0, weather.forecasts.get(0).parts.get("night").prec_strength);
    }

}
