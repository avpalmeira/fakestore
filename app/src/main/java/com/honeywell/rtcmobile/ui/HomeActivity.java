package com.honeywell.rtcmobile.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

    private String token = "";

    private TextView results;

    HomeActivity self = this;

    private SharedPreferences sharedPref;

    public static final String TAG = HomeActivity.class.toString();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Button fetch, logout;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        logout = findViewById(R.id.logout);
        fetch = findViewById(R.id.fetch_products);
        results = findViewById(R.id.products_list);

        // Resgatar token de shared preferences
        sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key),
                Context.MODE_PRIVATE
        );
        token = sharedPref.getString(
                getString(R.string.token_pref_key),
                token
        );

        Log.d(TAG, "Token retrieved: "+token);

        fetch.setOnClickListener(fetchListener);
        logout.setOnClickListener(logoutHandler);
    }


    private OnClickListener fetchListener = new OnClickListener() {

        @Override
        public void onClick(View view) {

            // TODO :: throw error message
            if (token == null || token.equals("")) {

                Log.e(TAG, "An error occurred fetching data");
                return;
            }

            UserClient client = LoginActivity.retrofit.create(UserClient.class);

            // TODO:FIX :: call deve retornar somente uma lista de produtos, e nao um lista dentro de outra
            Call<List<List<Product>>> productCall =
                    client.getProducts("Bearer "+token);

            Log.i(TAG, "Called task to retrieve products.");

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

            token = "";

            // Remove token de shared preferences
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.remove(getString(R.string.token_pref_key));
            editor.apply();

            Log.i(TAG, "Logout task called");

            Intent intent = new Intent(self, LoginActivity.class);
            startActivity(intent);
        }
    };
}
