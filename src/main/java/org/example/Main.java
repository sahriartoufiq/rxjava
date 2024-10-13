package org.example;

public class Main {

    public static void main(String[] args) {

        Observable<String> observable = Observable
                .create((observer, disposable) -> {

                    observer.onNext("One");
                    observer.onNext("Two");
                    observer.onNext("Three");
                    observer.onComplete();
                });

        Observer<String> observer = new Observer<>() {

            @Override
            public void onNext(String value) {
                System.out.println("Received: " + value);
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("Error: " + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        };

        observable
                .filter(x -> !x.equals("One"))
                .map(x -> "Emitting -> " + x)
                .subscribe(observer);
    }
}
