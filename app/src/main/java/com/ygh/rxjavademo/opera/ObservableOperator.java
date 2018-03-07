package com.ygh.rxjavademo.opera;

import com.ygh.rxjavademo.i.Observer;

public interface ObservableOperator<D, U> {
    Observer<D> apply(Observer<U> observer);
}
