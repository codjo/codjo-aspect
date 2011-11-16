/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect;
/**
 * Exception levée lors de l'exécution d'un aspect.
 *
 * @version $Revision: 1.2 $
 */
public class AspectException extends Exception {
    /**
     * Constructeur
     *
     * @param message Le message expliquant l'exception
     */
    public AspectException(String message) {
        super(message);
    }


    public AspectException(String message, Throwable cause) {
        super(message, cause);
    }
}
