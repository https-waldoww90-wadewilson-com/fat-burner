package edu.stts.fatburner.data.network;

import java.util.List;

import edu.stts.fatburner.data.model.Article;
import edu.stts.fatburner.data.model.Food;
import edu.stts.fatburner.data.model.LogFood;
import edu.stts.fatburner.data.model.User;
import edu.stts.fatburner.data.network.body.LogFoodBody;
import edu.stts.fatburner.data.network.body.LoginBody;
import edu.stts.fatburner.data.network.response.InsertResponse;
import edu.stts.fatburner.data.network.response.LoginResponse;
import edu.stts.fatburner.data.network.response.RegisterResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API {
    @POST("login")
    Call<LoginResponse> login(@Body LoginBody body);

    @POST("register")
    Call<RegisterResponse> register(@Body User body);

    @POST("food/log/insert")
    Call<InsertResponse> saveLogFood(@Body LogFoodBody body);

    @GET("foods")
    Call<List<Food>> getFoods();

    @GET("articles")
    Call<List<Article>> getArticles();

    @GET("food/log/{userid}")
    Call<List<LogFood>> getLogFood(@Path("userid") int userID);
}
