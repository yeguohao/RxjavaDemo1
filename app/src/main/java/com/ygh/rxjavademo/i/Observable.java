package com.ygh.rxjavademo.i;


import com.ygh.rxjavademo.opera.Fun1;
import com.ygh.rxjavademo.opera.LambdaObserver;
import com.ygh.rxjavademo.opera.ObservableCreate;
import com.ygh.rxjavademo.opera.ObservableFlatMap;
import com.ygh.rxjavademo.opera.ObservableLift;
import com.ygh.rxjavademo.opera.ObservableMap;
import com.ygh.rxjavademo.opera.ObservableObserveOn;
import com.ygh.rxjavademo.opera.ObservableOperator;
import com.ygh.rxjavademo.opera.ObservableSubscribeOn;

public class Observable<T> {


    public static <T> Observable<T> create(Subscriber<T> source) {
        return new ObservableCreate<>(source);
    }

    public <J> Observable<J> map(Fun1<T,J> fun1) {
        return new ObservableMap<>(this,fun1);
    }

    public <J> Observable<J> flatMap(Fun1<T, ? extends Observable<J>> fun1) {
        return new ObservableFlatMap<>(this, fun1);
    }

    public <J> Observable<J> lift(ObservableOperator<T, J> operator) {
        return new ObservableLift<>(this,operator);
    }

    public LambdaObserver<T> subscribe(Observer<T> observer) {
        return new LambdaObserver<>(this,observer);
    }

    public Observable<T> subscribeOn() {
        return new ObservableSubscribeOn<>(this);
    }

    public Observable<T> observeOn() {
        return new ObservableObserveOn<>(this);
    }

    public void subscribeActual(Observer<T> observer) {

    }
}
