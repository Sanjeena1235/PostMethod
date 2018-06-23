package com.android.finalapp.retrofitclients;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 6/12/2018.
 */

public class Retrofitclient {


    public static Retrofit retrofit = null;

    public static String url = "http://192.168.43.130/androidfirstproject/admin/pages/";

//    public static String url = "http://clients.crystalinfosys.com/restroApp/public/api/";

    public static Retrofit getFormData(){

        if (retrofit ==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
