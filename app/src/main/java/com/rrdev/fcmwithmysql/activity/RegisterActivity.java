package com.rrdev.fcmwithmysql.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rrdev.fcmwithmysql.ApiService.BaseApiService;
import com.rrdev.fcmwithmysql.ApiService.UtilsApi;
import com.rrdev.fcmwithmysql.R;
import com.rrdev.fcmwithmysql.SharedPref.SharedPrefLogin;
import com.rrdev.fcmwithmysql.SharedPref.SharedPrefToken;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextUsername, editTextPasswod;
    private ProgressDialog progressDialog;
    private SharedPrefLogin sharedPrefLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextUsername = findViewById(R.id.etUsernameRegister);
        editTextPasswod = findViewById(R.id.etPasswordRegister);

        sharedPrefLogin = new SharedPrefLogin(this);
        if (sharedPrefLogin.getIsLogin()) {
            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            this.finish();
        }
    }

    public void toLogin(View view) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void toRegister(View view) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering Device...");
        progressDialog.show();

        final String token = SharedPrefToken.getInstance(this).getDeviceToken();
        final String username = editTextUsername.getText().toString();
        final String password = editTextPasswod.getText().toString();

        if (token == null) {
            progressDialog.dismiss();
            Toast.makeText(this, "Token not generated", Toast.LENGTH_LONG).show();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UtilsApi.BASE_URL_API)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        BaseApiService api = retrofit.create(BaseApiService.class);
        Call<String> call = api.registerRequest(username, password, token);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                Log.i("response", response.body().toString());
                //Toast.makeText()
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());
                        progressDialog.dismiss();
                        String jsonresponse = response.body().toString();
                        try {
                            parseRegData(jsonresponse);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    private void parseRegData(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        if (jsonObject.optString("status").equals("false")) {
            Toast.makeText(RegisterActivity.this, "Registered Success!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        } else if (jsonObject.optString("status").equals("true")) {
            Toast.makeText(RegisterActivity.this, "Username sudah ada..!", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }else {
            Toast.makeText(RegisterActivity.this, "gagalll...!!", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }
}
