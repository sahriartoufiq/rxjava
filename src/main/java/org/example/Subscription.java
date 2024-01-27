package org.example;

public class Subscription implements Disposable {
    private boolean disposed = false;

    @Override
    public void dispose() {
        disposed = true;
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }
}
