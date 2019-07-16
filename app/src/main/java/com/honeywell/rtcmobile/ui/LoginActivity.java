package com.honeywell.rtcmobile.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.honeywell.rtcmobile.model.User;
import com.honeywell.rtcmobile.service.login.UserClient;

import com.honeywell.rtcmobile.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    // TODO :: criar arquivo para configurar conexao
    private static final String BASE_URL = "http://10.0.60.75:8080/";

    private EditText username, password;
    private Retrofit retrofit;

    private static String TOKEN;

    private static final String TAG = LoginActivity.class.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        // TODO :: setar onClickListener no botao

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


            User user = new User(usernameVal, passwordVal);

            Call<User> call = client.login(user);

            // Faz uma chamada assincrona para o servidor
            call.enqueue(new Callback<User>() {

                @Override
                public void onResponse(
                        Call<User> call,
                        Response<User> response) {

                    Log.i(TAG, response.toString());

                    // TODO :: salvar token recebido

                    if (response.isSuccessful()) {
                        Toast.makeText(
                                LoginActivity.this,
                                "You got a response:\n" + response.body().getToken(),
                                Toast.LENGTH_LONG
                        ).show();
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
}
