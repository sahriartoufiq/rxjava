package org.example;

public interface ObservableOnSubscribe<T> {
    void subscribe(Observer<? super T> observer, Disposable disposable);
}
