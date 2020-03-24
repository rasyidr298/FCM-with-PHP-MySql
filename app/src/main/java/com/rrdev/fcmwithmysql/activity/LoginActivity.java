package com.rrdev.fcmwithmysql.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rrdev.fcmwithmysql.R;
import com.rrdev.fcmwithmysql.ApiService.BaseApiService;
import com.rrdev.fcmwithmysql.SharedPref.SharedPrefLogin;
import com.rrdev.fcmwithmysql.ApiService.UtilsApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText etUname, etPass;
    private SharedPrefLogin sharedPrefLogin;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPrefLogin = new SharedPrefLogin(this);
        etUname = findViewById(R.id.etUsernamelogin);
        etPass = findViewById(R.id.etPassowrdlogin);
    }

    public void login (View view){
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        loginUser();
    }

    private void loginUser() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Login...");
        progressDialog.show();

        final String username = etUname.getText().toString().trim();
        final String password = etPass.getText().toString().trim();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL_API)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        BaseApiService api = retrofit.create(BaseApiService.class);
        Call<String> call = api.loginRequest(username,password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("Responsestring", response.body().toString());
                //Toast.makeText()
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());

                        String jsonresponse = response.body().toString();
                        parseLoginData(jsonresponse);

                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    private void parseLoginData(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")) {
                saveInfo(response);
                Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                this.finish();
            }else {
                Toast.makeText(LoginActivity.this, "Login gagall..!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private void saveInfo(String response){
        sharedPrefLogin.putIsLogin(true);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")) {
                JSONArray dataArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    sharedPrefLogin.putUsername(dataobj.getString("username"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}