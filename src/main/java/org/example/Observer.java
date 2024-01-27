package org.example;

public interface Observer<T> {
    void onNext(T value);
    void onError(Throwable throwable);
    void onComplete();
}
