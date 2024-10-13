package org.example;

import java.util.function.Function;
import java.util.function.Predicate;

public class Observable<T> implements ObservableSource<T> {

    private ObservableOnSubscribe<T> source;

    private Observable(ObservableOnSubscribe<T> source) {
        this.source = source;
    }

    public static <T> Observable<T> create(ObservableOnSubscribe<T> source) {
        return new Observable<>(source);
    }

    @Override
    public void subscribe(Observer<? super T> observer) {

        // Create a subscription for this observer
        Disposable subscription = new Subscription();

        // Propagate events downstream
        source.subscribe(observer, subscription);
    }

    public <R> Observable<R> map(Function<? super T, ? extends R> mapper) {

        return create((observer, disposable) -> {

            // Modified subscribe logic to apply map transformation
            source
                    .subscribe(new Observer<T>() {

                                   @Override
                                   public void onNext(T value) {
                                       R transformedValue = mapper.apply(value);
                                       observer.onNext(transformedValue);
                                   }

                                   @Override
                                   public void onError(Throwable throwable) {
                                       observer.onError(throwable);
                                   }

                                   @Override
                                   public void onComplete() {
                                       observer.onComplete();
                                   }
                               },
                            disposable
                    );
        });
    }

    public Observable<T> filter(Predicate<? super T> predicate) {
        return create((observer, disposable) -> {

            // Modified subscribe logic to apply filter
            source
                    .subscribe(new Observer<T>() {

                                   @Override
                                   public void onNext(T value) {

                                       if (predicate.test(value)) {
                                           observer.onNext(value);
                                       }
                                   }

                                   @Override
                                   public void onError(Throwable throwable) {
                                       observer.onError(throwable);
                                   }

                                   @Override
                                   public void onComplete() {
                                       observer.onComplete();
                                   }
                               },
                            disposable
                    );
        });
    }
}
