package org.example;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestObserver<T> implements Observer<T> {

    private final List<T> values = new ArrayList<>();

    private Throwable error;

    private boolean completed;

    @Override
    public void onNext(T value) {
        values.add(value);
    }

    @Override
    public void onError(Throwable throwable) {
        this.error = throwable;
    }

    @Override
    public void onComplete() {
        this.completed = true;
    }

    public TestObserver<T> assertValueCount(int count) {

        assertEquals(count, values.size());

        return this;
    }

    public TestObserver<T> assertValues(T... expectedValues) {

        assertEquals(List.of(expectedValues), values);

        return this;
    }

    public TestObserver<T> assertComplete() {

        assertTrue(completed);

        return this;
    }

    public TestObserver<T> assertError(Class<? extends Throwable> errorClass) {

        assertNotNull(error);
        assertTrue(errorClass.isInstance(error));

        return this;
    }

    public TestObserver<T> assertNoErrors() {

        assertNull(error);

        return this;
    }
}
