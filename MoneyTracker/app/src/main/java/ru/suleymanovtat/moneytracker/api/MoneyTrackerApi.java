package ru.suleymanovtat.moneytracker.api;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.suleymanovtat.moneytracker.models.AddResult;
import ru.suleymanovtat.moneytracker.models.Item;

public interface MoneyTrackerApi {

    @Headers("Content-Type: application/json")
    @GET("items")
    Call<ArrayList<Item>> items(@Query("type") String type);

    @Headers("Content-Type: application/json")
    @POST("items/add")
    Call<AddResult> add(@Query("name") String name, @Query("price") int price, @Query("type") String type);
}
