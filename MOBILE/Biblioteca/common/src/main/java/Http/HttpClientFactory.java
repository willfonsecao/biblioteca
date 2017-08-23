package Http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class HttpClientFactory {

    private static Retrofit retrofit;

    public static Retrofit getHttpClient() {

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.readTimeout(4, TimeUnit.MINUTES);
        okHttpClient.connectTimeout(4, TimeUnit.MINUTES);

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.17.81.28:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient.build())
                    .build();
        }
        return retrofit;
    }
}
