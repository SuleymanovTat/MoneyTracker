package ru.suleymanovtat.moneytracker.api;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.suleymanovtat.moneytracker.models.AddResult;
import ru.suleymanovtat.moneytracker.models.AuthResult;
import ru.suleymanovtat.moneytracker.models.BalanceResult;
import ru.suleymanovtat.moneytracker.models.Item;
import ru.suleymanovtat.moneytracker.models.Result;

public interface MoneyTrackerApi {

    @GET("items")
    Call<ArrayList<Item>> items(@Query("type") String type);

    @POST("items/add")
    Call<AddResult> addItem(@Query("name") String name, @Query("price") int price, @Query("type") String type);

    @GET("auth")
    Call<AuthResult> auth(@Query("social_user_id") String socialUserId);

    @POST("items/remove")
    Call<Result> remove(@Query("id") int id);

    @GET("balance")
    Call<BalanceResult> balance();
}
