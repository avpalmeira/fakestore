package com.honeywell.rtcmobile.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.honeywell.rtcmobile.R;
import com.honeywell.rtcmobile.model.Product;
import com.honeywell.rtcmobile.service.login.UserClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeActivity extends AppCompatActivity {

    private String token;
    private Button fetch, logout;
    private TextView results;
    private UserClient client;

    HomeActivity self = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        logout = findViewById(R.id.logout);
        fetch = findViewById(R.id.fetch_products);
        results = findViewById(R.id.products_list);

        Intent intent = getIntent();
        token = intent.getStringExtra(LoginActivity.TAG);

        client = LoginActivity.retrofit.create(UserClient.class);

        fetch.setOnClickListener(fetchListener);
        logout.setOnClickListener(logoutHandler);
    }


    private OnClickListener fetchListener = new OnClickListener() {

        @Override
        public void onClick(View view) {

            // TODO :: throw error message
            if (token == null) {
                return;
            }

            Call<List<List<Product>>> productCall = client.getProducts("Bearer "+token);

            productCall.enqueue(new Callback<List<List<Product>>>() {

                @Override
                public void onResponse(
                        Call<List<List<Product>>> call,
                        Response<List<List<Product>>> response) {

                    if (response.isSuccessful()) {
                        List<Product> listProducts = response.body().get(0);

                        results.setText("");

                        for (Product product : listProducts) {

                            results.append(product.toString());
                        }
                    } else {

                        results.setText("Your response failed: "+response.code());
                    }
                }

                @Override
                public void onFailure(
                        Call<List<List<Product>>> call,
                        Throwable t) {

                    results.setText("There was an error: "+t.getMessage());
                }
            });
        }
    };

    private OnClickListener logoutHandler = new OnClickListener() {

        @Override
        public void onClick(View view) {

            token = null;

            Intent intent = new Intent(self, LoginActivity.class);
            startActivity(intent);
        }
    };
}
