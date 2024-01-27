package org.example;

public interface ObservableSource<T> {
    void subscribe(Observer<? super T> observer);
}
