package com.ygh.rxjavademo.opera;

import com.ygh.rxjavademo.i.Observable;
import com.ygh.rxjavademo.i.Observer;

public class ObservableFlatMap<T, J> extends Observable<J> implements Observer<T> {

    private Observable<T> parent;
    private Observer<J> child;
    private Fun1<T, ? extends Observable<J>> f;

    public ObservableFlatMap(Observable<T> parent, Fun1<T, ? extends Observable<J>> f) {
        this.parent = parent;
        this.f = f;
    }

    @Override
    public void onNext(T o) {
        Observable<J> apply = f.apply(o);
        apply.subscribe(o1 -> child.onNext(o1));
    }

    @Override
    public void subscribeActual(Observer<J> observer) {
        child = observer;
        parent.subscribeActual(this);
    }

}
