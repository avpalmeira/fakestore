package com.fakestore.mobile.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fakestore.mobile.model.User;
import com.fakestore.mobile.service.login.UserClient;

import com.fakestore.mobile.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;

    private String BASE_URL;

    public static Retrofit retrofit;

    private LoginActivity self = this;

    private static String token;

    public static final String TAG = LoginActivity.class.toString();

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Button loginButton;

        BASE_URL = getString(R.string.connection_string);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);

        loginButton.setOnClickListener(handleLogin);

        Retrofit.Builder builder =
                new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        retrofit = builder.build();
    }


    private OnClickListener handleLogin = new OnClickListener() {

        @Override
        public void onClick(View view) {
            Log.i(TAG, "Login task called");

            // TODO :: separar a validacao para uma classe propria
            if (TextUtils.isEmpty(username.getText())) {

                username.setError(
                        "Username is required");
            } else if (TextUtils.isEmpty(password.getText())) {

                password.setError(
                        "Password is required");
            } else {

                UserClient client = retrofit.create(UserClient.class);

                // Pega o username e password e cria o header para a chamada
                String usernameVal = username.getText().toString();
                String passwordVal = password.getText().toString();


                User user = new User(usernameVal, passwordVal);

                Call<User> call = client.login(user);

                // Faz uma chamada assincrona para o servidor
                call.enqueue(new Callback<User>() {

                    @Override
                    public void onResponse(
                            Call<User> call,
                            Response<User> response) {

                        if (response.isSuccessful()) {

                            token = response.body().getToken();

                            Log.i(TAG, "TOKEN: "+token);

                            Intent intent = new Intent(self, HomeActivity.class);

                            // Acessando arquivo de shared preferences e editor
                            sharedPref = getSharedPreferences(
                                    getString(R.string.preference_file_key),
                                    Context.MODE_PRIVATE);
                            editor = sharedPref.edit();

                            // Salva token em shared preferences
                            editor.putString(
                                    getString(R.string.token_pref_key),
                                    token);
                            editor.apply();

                            startActivity(intent);

                        } else {

                            Toast.makeText(
                                    LoginActivity.this,
                                    "Your login credentials are invalid",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<User> call,
                            Throwable t) {

                        Log.e(TAG, t.getMessage());

                        // Informa qual o erro pelo Toast
                        Toast.makeText(
                                LoginActivity.this,
                                "You got an error: " + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
            }
        }
    };
}
