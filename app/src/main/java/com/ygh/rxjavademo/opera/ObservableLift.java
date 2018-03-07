package com.ygh.rxjavademo.opera;

import com.ygh.rxjavademo.i.Observable;
import com.ygh.rxjavademo.i.Observer;

public class ObservableLift<T, J> extends Observable<J> implements Observer<T> {

    private Observable<T> parent;
    private ObservableOperator<T, J> operator;

    public ObservableLift(Observable<T> parent, ObservableOperator<T, J> operator) {
        this.parent = parent;
        this.operator = operator;
    }

    @Override
    public void onNext(T o) {
    }

    @Override
    public void subscribeActual(Observer<J> observer) {
        Observer<T> apply = operator.apply(observer);
        parent.subscribe(apply);
    }
}
