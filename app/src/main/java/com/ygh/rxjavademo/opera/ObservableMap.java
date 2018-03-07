package com.ygh.rxjavademo.opera;

import com.ygh.rxjavademo.i.Observable;
import com.ygh.rxjavademo.i.Observer;

public class ObservableMap<T, J> extends Observable<J> implements Observer<T> {

    private Observable<T> parent;
    private Observer<J> child;
    private Fun1<T, J> f;

    public ObservableMap(Observable<T> parent, Fun1<T, J> f) {
        this.parent = parent;
        this.f = f;
    }

    @Override
    public void onNext(T o) {
        child.onNext(f.apply(o));
    }

    @Override
    public void subscribeActual(Observer<J> observer) {
        child = observer;
        parent.subscribeActual(this);
    }
}
