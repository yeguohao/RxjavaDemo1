package com.ygh.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ygh.rxjavademo.i.Observable;
import com.ygh.rxjavademo.i.Observer;
import com.ygh.rxjavademo.i.Subscriber;
import com.ygh.rxjavademo.opera.Fun1;
import com.ygh.rxjavademo.opera.ObservableOperator;

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
                .flatMap(integer -> { // 把一条数据转换成发出两条数据的数据源
                        final int i = integer;
                        return Observable.create(
                                (Subscriber<String>) observer -> {
                                    int j = i;
                                    observer.onNext((++j) + "");
                                    observer.onNext((++j) + "");
                                }
                        );
                        }
                )
                .lift(new ObservableOperator<String, Integer>() { // flatMap 能做的事 lift 也能做，反之亦然
                    String das;
                    @Override
                    public Observer<String> apply(Observer<Integer> observer) {
                        return s -> {
                            if (das == null) das = s;
                            else {
                                int dai = Integer.parseInt(das);
                                int i = Integer.parseInt(s);
                                observer.onNext(dai + i);
                            }
                        };
                    }
                })
                .subscribeOn()
                .observeOn()
                .subscribe(s -> {
                    Log.d(TAG, "onNext() 所在线程: " + Thread.currentThread().getName());
                    Log.d(TAG, "onNext() 结果: " + s); // 结果为 5
                });
    }
}
