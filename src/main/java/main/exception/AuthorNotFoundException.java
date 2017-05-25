package main.exception;

/**
 * This exception is thrown if no author is found in the database.
 */
public class AuthorNotFoundException extends Exception {
    public AuthorNotFoundException(String s) {super(s);}
}
