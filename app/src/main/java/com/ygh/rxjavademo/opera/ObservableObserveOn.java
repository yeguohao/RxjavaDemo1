package com.ygh.rxjavademo.opera;

import com.ygh.rxjavademo.i.Observable;
import com.ygh.rxjavademo.i.Observer;

public class ObservableObserveOn<T> extends Observable<T> implements Observer<T> {

    private Observable<T> parent;
    private Observer<T> child;

    public ObservableObserveOn(Observable<T> parent) {
        this.parent = parent;
    }

    @Override
    public void subscribeActual(Observer<T> observer) {
        super.subscribeActual(observer);
        this.child = observer;
        parent.subscribeActual(this);
    }


    @Override
    public void onNext(T t) {
        new Thread(() -> child.onNext(t),"ObserveOn 线程").start();
    }
}
