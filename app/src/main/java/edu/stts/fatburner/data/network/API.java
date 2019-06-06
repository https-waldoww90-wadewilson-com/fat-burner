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
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API {
    @POST("login")
    Call<LoginResponse> login(@Body LoginBody body);

    @POST("register")
    Call<RegisterResponse> register(@Body User body);

    @POST("api/food/log/insert")
    Call<InsertResponse> saveLogFood(@Header("Authorization") String token,@Body LogFoodBody body);

    @POST("api/food/log/update/{id}")
    Call<InsertResponse> updateLogFood(@Header("Authorization") String token,@Path("id") int logId,@Body UpdateLogFoodBody body);

    @POST("api/workout/log/insert")
    Call<InsertResponse> saveLogWorkout(@Header("Authorization") String token,@Body LogWorkoutBody body);

    @POST("api/workout/log/update/{id}")
    Call<InsertResponse> updateLogWorkout(@Header("Authorization") String token,@Path("id") int logId,@Body UpdateLogWorkoutBody body);

    @GET("api/food/{category}")
    Call<List<Food>> getFoods(@Header("Authorization") String token,@Path("category") String category);

    @GET("api/food")
    Call<List<FoodCategory>> getFoodCategory(@Header("Authorization") String token);

    @GET("api/articles/")
    Call<List<Article>> getArticles(@Header("Authorization") String token);

    @GET("api/workouts")
    Call<List<Workout>> getWorkouts(@Header("Authorization") String token);

    @GET("api/food/log/{userid}/{filter}")
    Call<List<LogFood>> getLogFood(@Header("Authorization") String token,@Path("userid") int userID,@Path("filter") String filter);

    @GET("api/workout/log/{userid}/{filter}")
    Call<List<LogWorkout>> getLogWorkout(@Header("Authorization") String token,@Path("userid") int userID, @Path("filter") String filter);

    @DELETE("api/food/log/delete/{id}")
    Call<InsertResponse> deleteLogFood(@Header("Authorization") String token,@Path("id") int logId);

    @DELETE("api/workout/log/delete/{id}")
    Call<InsertResponse> deleteLogWorkout(@Header("Authorization") String token,@Path("id") int logId);
}