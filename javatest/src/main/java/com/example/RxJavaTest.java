package com.example;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by James on 217/1/12.
 */

public class RxJavaTest {
    public static void main(String[] args){
        //被观察者
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                // Emitter:发射器
                println("Emit 1");
                e.onNext(1);
                println("Emit 2");
                e.onNext(2);
                println("Emit 3");
                e.onNext(3);
                e.onComplete();
                println("onComplete");
//                e.onError(new Throwable("TestException"));
                println("Emit 4");
                e.onNext(4);
            }
        });

        //观察者
        Observer<Integer> observer = new Observer<Integer>() {
            private Disposable mDisposable;
            private int i ;
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Disposable");
                mDisposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("onNext"+integer);
                if (++i==2){
                    System.out.println("dispose");
                    mDisposable.dispose();
                    System.out.println("isDispose"+mDisposable.isDisposed());
                }
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError "+e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };
        observable.subscribe(observer);
    }

    private static  <T> void  println(T t){
        if (t instanceof String){
            System.out.println(t);
        }else {
            System.out.println(t+"");
        }
    }
}
