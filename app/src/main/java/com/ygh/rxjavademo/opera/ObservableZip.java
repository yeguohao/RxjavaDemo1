package com.ygh.rxjavademo.opera;

import com.ygh.rxjavademo.i.Observable;
import com.ygh.rxjavademo.i.Observer;

import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ObservableZip<J> extends Observable<J> {

    private Observable source1;
    private Observable source2;
    private Combine<J> combine;
    private Observer<J> actual;
    private Observer[] observers;
    private Object[] objects;

    private AtomicInteger sign = new AtomicInteger(0);

    public ObservableZip(Observable<?> source1, Observable<?> source2, Combine<J> combine) {
        this.source1 = source1;
        this.source2 = source2;
        this.combine = combine;
    }

    @Override
    public void subscribeActual(Observer<J> observer) {
        actual = observer;
        observers = new Observer[] {new InnerObserver(), new InnerObserver()};
        objects = new Object[observers.length];
        source1.subscribe(observers[0]);
        source2.subscribe(observers[1]);
    }

    private void train() {
        if (sign.getAndIncrement() != 0) {
            return;
        }
        int cc = 1;
        while (true) {
            while (true) {
                boolean hasEmpty = false;
                for (int i = 0; i < observers.length; i++) {
                    if (objects[i] != null) {
                        continue;
                    }
                    PriorityQueue queue = ((InnerObserver) observers[i]).queue;
                    Object poll = queue.poll();
                    if (poll != null) {
                        objects[i] = poll;
                    } else {
                        hasEmpty = true;
                        break;
                    }
                }
                if (hasEmpty) {
                    break;
                } else {
                    actual.onNext(combine.combine(objects[0], objects[1]));
                    for (int i = 0; i < objects.length; i++) {
                        objects[i] = null;
                    }
                }
            }
            cc = sign.addAndGet(-cc);
            if (cc == 0) {
                break;
            }
        }
    }

    class InnerObserver implements Observer {

        private PriorityQueue queue = new PriorityQueue(10);

        @Override
        public void onNext(Object o) {
            queue.add(o);
            train();
        }

    }

}
