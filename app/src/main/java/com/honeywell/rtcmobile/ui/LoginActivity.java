package com.honeywell.rtcmobile.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.honeywell.rtcmobile.api.model.User;
import com.honeywell.rtcmobile.api.service.UserClient;

import com.honeywell.rtcmobile.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    // Instancia PHP em maquina da rede local da ASSERT
    private static final String BASE_URL = "http://10.0.60.108/";

    private EditText username, password;
    private Retrofit retrofit;

    private static final String TAG = LoginActivity.class.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        Retrofit.Builder builder =
                new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        retrofit = builder.build();
    }

    public void handleLogin(View view) {

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

            // TODO :: usar classe Credentials para gerar o header
            // Pega o username e password e cria o header para a chamada
            String usernameVal = username.getText().toString();
            String passwordVal = password.getText().toString();
            String credentials = usernameVal+":"+passwordVal;

            String base = Base64.encodeToString(
                    credentials.getBytes(),
                    Base64.NO_WRAP);

            String authHeader = "Basic "+base;

            Call<User> call = client.getUser(authHeader);

            // Faz uma chamada assincrona para o servidor
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(
                        Call<User> call,
                        Response<User> response) {

                    Log.i(TAG, response.toString());

                    // Tendo qualquer resposta, lança um Toast com body da resposta
                    Toast.makeText(
                            LoginActivity.this,
                            "You got a response:\n" + response.body(),
                            Toast.LENGTH_LONG
                    ).show();
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
}
