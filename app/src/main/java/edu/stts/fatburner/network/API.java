package edu.stts.fatburner.network;

import edu.stts.fatburner.network.body.LoginBody;
import edu.stts.fatburner.network.model.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface API {
    @POST("login")
    Call<LoginResponse> login(@Body LoginBody body);
}
