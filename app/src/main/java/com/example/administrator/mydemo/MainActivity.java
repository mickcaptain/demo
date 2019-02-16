package com.example.administrator.mydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.retrofit.impl.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        Button retrofit = (Button) findViewById(R.id.retrofit);
        retrofit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.retrofit:
                retrofitRequest();
                break;
            default:
                break;
        }
    }


    public void retrofitRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.kuaidi100.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService service = retrofit.create(RetrofitService.class);
        Call<com.yl.retrofitdemo.bean.PostInfo> call = service.getPostInfo("yuantong", "11111111111");
        call.enqueue(new Callback<com.yl.retrofitdemo.bean.PostInfo>() {
            @Override
            public void onResponse(Call<com.yl.retrofitdemo.bean.PostInfo> call, Response<com.yl.retrofitdemo.bean.PostInfo> response) {
                Log.i("http返回：", response.body().toString() + "");
            }

            @Override
            public void onFailure(Call<com.yl.retrofitdemo.bean.PostInfo> call, Throwable t) {

            }
        });

    }
}
