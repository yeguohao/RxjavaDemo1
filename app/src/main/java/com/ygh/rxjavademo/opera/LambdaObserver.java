package com.ygh.rxjavademo.opera;

import com.ygh.rxjavademo.i.Observable;
import com.ygh.rxjavademo.i.Observer;

public class LambdaObserver<T> {

    private Observable<T> observable;

    public LambdaObserver(Observable<T> observable, Observer<T> next) {
        this.observable = observable;
        this.observable.subscribeActual(next);
    }

}
