package main.exception;

/**
 * This exception is thrown if no location is found in the database.
 */
public class LocationNotFoundException extends Exception{
    public LocationNotFoundException(String s) { super(s);}
}
