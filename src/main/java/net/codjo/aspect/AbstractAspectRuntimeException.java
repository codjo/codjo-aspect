/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect;
import java.io.PrintStream;
import java.io.PrintWriter;
/**
 * Classe abstraite pour les exceptions de type {@link RuntimeException}. Cette classe
 * permet de gérer la cause de l'exception.
 *
 * @version $Revision: 1.3 $
 */
abstract class AbstractAspectRuntimeException extends RuntimeException {
    private Exception cause;

    protected AbstractAspectRuntimeException(String message) {
        super(message);
    }


    protected AbstractAspectRuntimeException(String message, Exception cause) {
        this(message);
        this.cause = cause;
    }

    @Override
    public Exception getCause() {
        return cause;
    }


    @Override
    public void printStackTrace(PrintWriter writer) {
        synchronized (writer) {
            super.printStackTrace(writer);
            if (getCause() != null) {
                writer.println(" ---- cause ---- ");
                getCause().printStackTrace(writer);
            }
        }
    }


    @Override
    public void printStackTrace() {
        printStackTrace(System.err);
    }


    @Override
    public void printStackTrace(PrintStream stream) {
        synchronized (stream) {
            super.printStackTrace(stream);
            if (getCause() != null) {
                stream.println(" ---- cause ---- ");
                getCause().printStackTrace(stream);
            }
        }
    }
}
