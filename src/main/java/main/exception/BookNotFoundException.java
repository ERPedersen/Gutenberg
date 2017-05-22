package main.exception;

import com.sun.jersey.api.MessageException;

/**
 * This exception is thrown if no books were found in the database.
 */
public class BookNotFoundException extends MessageException {
    public BookNotFoundException(String s) {
        super(s);
    }
}
