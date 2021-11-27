
package net.exercise.store;

public class PersonStoreException extends RuntimeException {
    public PersonStoreException(String message, Throwable t) {
        super(message, t);
    }
}
