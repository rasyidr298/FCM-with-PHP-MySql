package com.rrdev.fcmwithmysql.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.rrdev.fcmwithmysql.R;
import com.rrdev.fcmwithmysql.SharedPref.SharedPrefLogin;

public class HomeActivity extends AppCompatActivity {

    private TextView tvUsername;
    private Button btnlogout;
    private SharedPrefLogin sharedPrefLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sharedPrefLogin = new SharedPrefLogin(this);

        btnlogout = findViewById(R.id.btnLogout);
        tvUsername = findViewById(R.id.tvUsername);
        tvUsername.setText("Welcome "+ sharedPrefLogin.getUsername());

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefLogin.putIsLogin(false);
                Intent intent = new Intent(HomeActivity.this, RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                HomeActivity.this.finish();
            }
        });
    }
}