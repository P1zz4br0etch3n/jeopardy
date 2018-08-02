package org.dhbw.mosbach.ai.javae.jeopardy.exception;

/**
 * Exception if points are not valid
 */
public class PointsNotValidException extends Exception {
    public PointsNotValidException() {
        super("Given Integer is not valid Points. Please use 100, 200, 300, 400 or 500.");
    }
}
