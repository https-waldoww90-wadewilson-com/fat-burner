package edu.stts.fatburner.network;

import java.util.List;

import edu.stts.fatburner.classObject.Food;
import edu.stts.fatburner.classObject.User;
import edu.stts.fatburner.network.body.LoginBody;
import edu.stts.fatburner.network.model.LoginResponse;
import edu.stts.fatburner.network.model.RegisterResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface API {
    @POST("login")
    Call<LoginResponse> login(@Body LoginBody body);

    @POST("register")
    Call<RegisterResponse> register(@Body User body);

    @GET("foods")
    Call<List<Food>> getFoods();
}
