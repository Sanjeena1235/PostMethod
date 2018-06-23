package com.android.finalapp.signingpart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.finalapp.MainActivity;
import com.android.finalapp.R;
import com.android.finalapp.RetrofitInterface.ApiInterface;
import com.android.finalapp.modeling.SubmitData;
import com.android.finalapp.retrofitclients.Retrofitclient;
import com.android.finalapp.validate_class.Validate_Class;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.android.finalapp.modeling.SubmitData.getStatus;

public class Register extends AppCompatActivity {

    EditText usernames, passwords, emails;
    Button registering;
    SharedPreferences sp;
    String username, password;

    String name, email, passwordd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernames = (EditText) findViewById(R.id.user_name);
        passwords = (EditText) findViewById(R.id.pass_word);
        emails = (EditText) findViewById(R.id.e_mail);
        registering = (Button) findViewById(R.id.registers);


        registering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(Register.this,"hello",Toast.LENGTH_SHORT).show();

                inputData();
            }
        });

    }


    public void inputData() {

        name = usernames.getText().toString().trim();
        email = emails.getText().toString().trim();
        passwordd = passwords.getText().toString().trim();
        //Toast.makeText(this,name+email,Toast.LENGTH_SHORT).show();

        ///// validating the string if it is empty or not

        if (name.equals("")) {
            Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show();
        } else if (email.equals("")) {
            Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();
        } else if (!email.matches(Validate_Class.emailPattern)) {
            Toast.makeText(this, "Enter valid email", Toast.LENGTH_SHORT).show();
        } else if (passwordd.equals("")) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        } else if (passwordd.length() < 6) {
            Toast.makeText(this, "The password must be at least 6 characters", Toast.LENGTH_SHORT).show();

        } else {
            RegisterData(name, email, passwordd);
        }

    }

    public void RegisterData(String name, String email,String password) {
        ApiInterface registerApiInterface = Retrofitclient.getFormData().create(ApiInterface.class);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username",name)
                .addFormDataPart("email",email)
                .addFormDataPart("password",password)
                .build();

        final ProgressDialog pDialog =ProgressDialog.show(new ContextThemeWrapper(Register.this,R.style.NewDialog),"","Please wait..",true);

        pDialog.show();

        registerApiInterface.submitData(requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()){

                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String succes = jsonObject.optString("success");
                        String message = jsonObject.optString("message");

                        if (succes.equals("0")){
                            Toast.makeText(Register.this, message, Toast.LENGTH_SHORT).show();
                        }else if (succes.equals("1")){
                            //////// save data to shared preferences //////
//
////                                SharedPreferences.Editor editor = sharedPreferences.edit();
////                                editor.putString("password",password);
////                                editor.putString("phone",phone);
////                                editor.apply();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            Toast.makeText(Register.this, message, Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(Register.this, message, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }else{
                    System.out.println("Error : "+String .valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("Error : "+t.getMessage());
            }
        });



    }
}









