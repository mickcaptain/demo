package com.example.administrator.mydemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.IMyAidlInterface;
import com.example.administrator.aidl.bean.Person;
import com.example.administrator.retrofit.impl.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "captain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        Button retrofit = (Button) findViewById(R.id.retrofit);
        Button aidlTest = (Button) findViewById(R.id.aidl);
        retrofit.setOnClickListener(this);
        aidlTest.setOnClickListener(this);
        bindService();

    }


    private void bindService() {
        Intent serverIntent = new Intent();
        serverIntent.setClassName("com.example.administrator.myservice", "com.example.administrator.myservice.AidlService");
        bindService(serverIntent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.retrofit:
                retrofitRequest();
                break;

            case R.id.aidl:
                if (mAidl == null) {
                    Log.d(TAG, " aidl == null");
                    return;
                }
                try {
                    Log.d(TAG, " 添加信息 ");
                    mAidl.addPerson(new Person("hello aidl"));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    private IMyAidlInterface mAidl;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "onServiceConnected");
            mAidl = IMyAidlInterface.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected");
            mAidl = null;
        }
    };


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
