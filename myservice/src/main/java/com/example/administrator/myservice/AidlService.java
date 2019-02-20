package com.example.administrator.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.administrator.IMyAidlInterface;
import com.example.administrator.aidl.bean.Person;

/**
 * Created by xiepengtao on 2019/2/20.
 */
public class AidlService extends Service {
    public static final String TAG = "captain";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    private IBinder mIBinder = new IMyAidlInterface.Stub() {

        @Override
        public void addPerson(Person person) throws RemoteException {
            Log.d(TAG, "addPerson " + person.toString());
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }
}
