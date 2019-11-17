import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;


public interface ApiService {
    @Headers("X-Yandex-API-Key: b4e803b7-75b1-4c74-be66-a578582d1290")
    @GET("v1/forecast")
    Call<Weather> forecast();
}
