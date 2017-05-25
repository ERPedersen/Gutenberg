package main.exception;

import com.sun.jersey.api.MessageException;

/**
 * This exception is thrown if no location is found in the database.
 */
public class LocationNotFoundException extends MessageException {
    public LocationNotFoundException(String s) { super(s);}
}
