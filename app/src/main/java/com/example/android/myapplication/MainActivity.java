package com.example.android.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import com.example.android.myapplication.pojo.DataBody;
import com.example.android.myapplication.pojo.MyWebService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    // tag, który jest wykorzystany do logowania
    private static final String CLASS_TAG = "MainActivity";

    // adapter REST z Retrofita
    Retrofit retrofit;
    // nasz interfejs
    MyWebService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClientBuilder.addInterceptor(logging);
        // ustawiamy wybrane parametry adaptera
        retrofit = new Retrofit.Builder()
                // adres API
                .baseUrl("http://damianchodorek.com")
                .client(okHttpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // tworzymy klienta
        service = retrofit.create(MyWebService.class);

        // ustawiamy przycisk GET
        findViewById(R.id.button_get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    service.getData().enqueue(new Callback<DataBody>() {

                        @Override
                        public void onResponse(Call<DataBody> call, Response<DataBody> response) {
                            Log.d(CLASS_TAG, response.body().getName());
                        }

                        @Override
                        public void onFailure(Call<DataBody> call, Throwable t) {
                            Log.d(CLASS_TAG, t.getLocalizedMessage());
                        }
                    });

                } catch (Exception e) {
                    Log.d(CLASS_TAG, e.toString());
                }
            }
        });

        // ustawiamy przycisk POST
        findViewById(R.id.button_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // zdefiniujmy dane, które mają by wysłane
                    DataBody body = new DataBody();
                    body.setId(1234);
                    body.setName("my name!");

                    service.postData(body).enqueue(new Callback<DataBody>() {

                        @Override
                        public void onResponse(Call<DataBody> call, Response<DataBody> response) {
                            Log.d(CLASS_TAG, response.body().getName());
                        }

                        @Override
                        public void onFailure(Call<DataBody> call, Throwable t) {
                            Log.d(CLASS_TAG, t.getLocalizedMessage());
                        }
                    });

                } catch (Exception e) {
                    Log.d(CLASS_TAG, e.toString());
                }
            }
        });
    }
}