package ru.suleymanovtat.moneytracker;

import android.app.Application;
import android.text.TextUtils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.suleymanovtat.moneytracker.api.MoneyTrackerApi;

public class MoneyTrackerApp extends Application {

    private MoneyTrackerApi api;
    private final String PREFERENCES_SESSION = "preferences_session";
    private final String KEY_AUTH_TOKEN = "auth_token";

    public MoneyTrackerApi getApi() {
        return api;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.HEADERS : HttpLoggingInterceptor.Level.NONE))
                .addInterceptor(new AuthInterceptor())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://android.loftschool.com/basic/v1/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        api = retrofit.create(MoneyTrackerApi.class);
    }

    public void setAuthToken(String token) {
        getSharedPreferences(PREFERENCES_SESSION, MODE_PRIVATE)
                .edit()
                .putString(KEY_AUTH_TOKEN, token)
                .apply();
    }

    public String getAuthToken() {
        return getSharedPreferences(PREFERENCES_SESSION,
                MODE_PRIVATE)
                .getString(KEY_AUTH_TOKEN, "");
    }

    public boolean isLoggedIn() {
        return !TextUtils.isEmpty(getAuthToken());
    }

    private class AuthInterceptor implements Interceptor {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            HttpUrl url = originalRequest.url().newBuilder().addQueryParameter("auth-token", getAuthToken()).build();
            return chain.proceed(originalRequest.newBuilder().url(url).build());
        }
    }
}
