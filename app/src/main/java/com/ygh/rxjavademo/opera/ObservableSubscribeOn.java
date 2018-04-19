package com.ygh.rxjavademo.opera;

import com.ygh.rxjavademo.i.Observable;
import com.ygh.rxjavademo.i.Observer;

public class ObservableSubscribeOn<T> extends Observable<T> implements Observer<T> {

    private Observer<T> child;
    private Observable<T> parent;

    public ObservableSubscribeOn(Observable<T> parent) {
        this.parent = parent;
    }

    @Override
    public void subscribeActual(Observer<T> observer) {
        super.subscribeActual(observer);
        this.child = observer;
        new Thread(() -> parent.subscribeActual(ObservableSubscribeOn.this),"SubscribeOn 线程").start();
    }

    @Override
    public void onNext(T t) {
        child.onNext(t);
    }

}
