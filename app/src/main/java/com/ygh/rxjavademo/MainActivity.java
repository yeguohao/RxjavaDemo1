package com.ygh.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ygh.rxjavademo.i.Observable;
import com.ygh.rxjavademo.i.Subscriber;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable.create(
                (Subscriber<String>) observer -> {
                    Log.d(TAG, "Subscriber() 所在线程: " + Thread.currentThread().getName());
                    observer.onNext("1");
                })
                .map(Integer::parseInt)
                .subscribeOn()
                .observeOn()
                .subscribe(s -> {
                    Log.d(TAG, "onNext() 所在线程: " + Thread.currentThread().getName());
                    Log.d(TAG, "onNext() 结果: " + s);
                });
    }
}
