package com.android.finalapp.RetrofitInterface;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by user on 6/12/2018.
 */

public interface ApiInterface {

    @POST("registerwithjson.php")
    Call<ResponseBody> submitData (@Body RequestBody body);

}
