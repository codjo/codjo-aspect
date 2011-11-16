/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect.util;
/**
 * Exception au cas ou l'execution du {@link PointRunner#run()} se passe mal.
 */
public class PointRunnerException extends Exception {
    public PointRunnerException(String msg) {
        super(msg);
    }


    public PointRunnerException(Exception cause) {
        super(cause);
    }


    public PointRunnerException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
