package com.ygh.rxjavademo.i;

public interface Subscriber<K> {

    void subscribe(Observer<K> observer) ;

}
