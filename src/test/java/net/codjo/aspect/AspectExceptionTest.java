/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect;
import junit.framework.TestCase;
/**
 * Classe de test de {@link AspectException}.
 */
public class AspectExceptionTest extends TestCase {
    public void test_message() throws Exception {
        String message = "Un message";
        AspectException exception = new AspectException(message);
        try {
            throw exception;
        }
        catch (AspectException e) {
            assertSame(message, e.getMessage());
        }
    }


    public void test_message_cause() throws Exception {
        String message = "Un message";
        Exception cause = new RuntimeException();
        AspectException exception = new AspectException(message, cause);
        try {
            throw exception;
        }
        catch (AspectException e) {
            assertSame(message, e.getMessage());
            assertSame(cause, e.getCause());
        }
    }
}
