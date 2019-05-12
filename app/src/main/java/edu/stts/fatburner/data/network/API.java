package edu.stts.fatburner.data.network;

import java.util.List;

import edu.stts.fatburner.data.model.Article;
import edu.stts.fatburner.data.model.Food;
import edu.stts.fatburner.data.model.FoodCategory;
import edu.stts.fatburner.data.model.LogFood;
import edu.stts.fatburner.data.model.LogWorkout;
import edu.stts.fatburner.data.model.User;
import edu.stts.fatburner.data.model.Workout;
import edu.stts.fatburner.data.network.body.LogFoodBody;
import edu.stts.fatburner.data.network.body.LogWorkoutBody;
import edu.stts.fatburner.data.network.body.LoginBody;
import edu.stts.fatburner.data.network.body.UpdateLogFoodBody;
import edu.stts.fatburner.data.network.body.UpdateLogWorkoutBody;
import edu.stts.fatburner.data.network.response.InsertResponse;
import edu.stts.fatburner.data.network.response.LoginResponse;
import edu.stts.fatburner.data.network.response.RegisterResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    @POST("food/log/update/{id}")
    Call<InsertResponse> updateLogFood(@Path("id") int logId,@Body UpdateLogFoodBody body);

    @POST("workout/log/insert")
    Call<InsertResponse> saveLogWorkout(@Body LogWorkoutBody body);

    @POST("workout/log/update/{id}")
    Call<InsertResponse> updateLogWorkout(@Path("id") int logId,@Body UpdateLogWorkoutBody body);

    @GET("food/{category}")
    Call<List<Food>> getFoods(@Path("category") String category);

    @GET("food")
    Call<List<FoodCategory>> getFoodCategory();

    @GET("articles")
    Call<List<Article>> getArticles();

    @GET("workouts")
    Call<List<Workout>> getWorkouts();

    @GET("food/log/{userid}/{filter}")
    Call<List<LogFood>> getLogFood(@Path("userid") int userID,@Path("filter") String filter);

    @GET("workout/log/{userid}/{filter}")
    Call<List<LogWorkout>> getLogWorkout(@Path("userid") int userID, @Path("filter") String filter);

    @DELETE("food/log/delete/{id}")
    Call<InsertResponse> deleteLogFood(@Path("id") int logId);

    @DELETE("workout/log/delete/{id}")
    Call<InsertResponse> deleteLogWorkout(@Path("id") int logId);
}
