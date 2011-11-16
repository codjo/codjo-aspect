/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect;
/**
 * Exception levée en interne lors du chargement d'un fichier XML.
 *
 * @version $Revision: 1.2 $
 */
class InternalLoadException extends AbstractAspectRuntimeException {
    /**
     * Constructeur
     *
     * @param message Le message expliquant l'exception
     */
    InternalLoadException(String message) {
        super(message);
    }


    InternalLoadException(String message, Exception cause) {
        super(message, cause);
    }
}
