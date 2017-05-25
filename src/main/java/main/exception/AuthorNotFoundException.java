package main.exception;

import com.sun.jersey.api.MessageException;

/**
 * This exception is thrown if no author is found in the database.
 */
public class AuthorNotFoundException extends MessageException {
    public AuthorNotFoundException(String s) {super(s);}
}
