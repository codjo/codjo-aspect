/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect;
/**
 * Exception levée lors de l'échec d'instanciation d'un aspect.
 *
 * @version $Revision: 1.1 $
 */
public class AspectInstanciationException extends AbstractAspectRuntimeException {
    /**
     * Constructeur
     *
     * @param message Le message expliquant l'exception
     */
    AspectInstanciationException(String message) {
        super(message);
    }


    AspectInstanciationException(String message, Exception cause) {
        super(message, cause);
    }
}
