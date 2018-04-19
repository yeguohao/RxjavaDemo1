package com.ygh.rxjavademo.opera;

import com.ygh.rxjavademo.i.Observable;
import com.ygh.rxjavademo.i.Observer;
import com.ygh.rxjavademo.i.Subscriber;


public class ObservableCreate<T> extends Observable<T> {

    private Subscriber<T> source;

    public ObservableCreate(Subscriber<T> source) {
        this.source = source;
    }

    @Override
    public void subscribeActual(Observer<T> observer) {
        super.subscribeActual(observer);
        source.subscribe(observer);
    }

}
