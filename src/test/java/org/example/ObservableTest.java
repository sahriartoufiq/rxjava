package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class ObservableTest {

    @Test
    @DisplayName("After subscription the observer will emit data and complete ->")
    void testOnComplete() {

        Observable<Integer> observable = Observable.create((observer, disposable) -> {
            observer.onNext(1);
            observer.onNext(2);
            observer.onNext(3);
            observer.onComplete();
        });

        TestObserver<Integer> testObserver = new TestObserver<>();
        observable.subscribe(testObserver);

        testObserver
                .assertValueCount(3)
                .assertValues(1, 2, 3)
                .assertComplete()
                .assertNoErrors();
    }

    @Test
    @DisplayName("After subscription the observer will emit mapped data by multiplying them by 2 and complete ->")
    void testMapOperator() {

        Observable<Integer> observable = Observable.create((observer, disposable) -> {
            observer.onNext(1);
            observer.onNext(2);
            observer.onNext(3);
            observer.onComplete();
        });

        Observable<Integer> mappedObservable = observable.map(x -> x * 2);

        TestObserver<Integer> testObserver = new TestObserver<>();
        mappedObservable.subscribe(testObserver);

        testObserver
                .assertValueCount(3)
                .assertValues(2, 4, 6)
                .assertComplete()
                .assertNoErrors();
    }

    @Test
    @DisplayName("After subscription the observer will emit filtered data which are even and complete ->")
    void testFilterOperator() {

        Observable<Integer> observable = Observable.create((observer, disposable) -> {
            observer.onNext(1);
            observer.onNext(2);
            observer.onNext(3);
            observer.onNext(4);
            observer.onComplete();
        });

        Observable<Integer> filteredObservable = observable.filter(x -> x % 2 == 0);

        TestObserver<Integer> testObserver = new TestObserver<>();
        filteredObservable.subscribe(testObserver);

        testObserver
                .assertValueCount(2)
                .assertValues(2, 4)
                .assertComplete()
                .assertNoErrors();
    }

    @Test
    @DisplayName("After subscription the observer will emit signal ->")
    void testErrorPropagation() {

        Observable<Integer> observable = Observable.create((observer, disposable) -> {
            observer.onNext(1);
            observer.onError(new RuntimeException("Test error"));
        });

        TestObserver<Integer> testObserver = new TestObserver<>();
        observable.subscribe(testObserver);

        testObserver
                .assertValueCount(1)
                .assertValues(1)
                .assertError(RuntimeException.class);
    }
}