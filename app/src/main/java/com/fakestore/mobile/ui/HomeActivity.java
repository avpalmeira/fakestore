package com.fakestore.mobile.ui;

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

import com.fakestore.mobile.R;
import com.fakestore.mobile.service.login.UserClient;

import com.fakestore.mobile.model.TestValue;

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

            Call<List<TestValue>> valuesCall =
                    client.getValues("Bearer "+token);

            Log.i(TAG, "Called task to retrieve products.");

            valuesCall.enqueue(new Callback<List<TestValue>>() {

                @Override
                public void onResponse(
                        Call<List<TestValue>> call,
                        Response<List<TestValue>> response) {

                    if (response.isSuccessful()) {
                        List<TestValue> listValues = response.body();

                        results.setText("");

                        for (TestValue value : listValues) {

                            Log.i(TAG, "Value: "+value.getValue());

                            results.append(value.getValue()+"\n\n");
                        }
                    } else {

                        results.setText("Your response failed: "+response.code());
                    }
                }

                @Override
                public void onFailure(
                        Call<List<TestValue>> call,
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
