/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect;
/**
 * Exception levée lors d'une erreur dans la configuration des aspects.
 *
 * @version $Revision: 1.3 $
 */
public class AspectConfigException extends Exception {
    /**
     * Constructeur
     *
     * @param message Le message expliquant l'exception
     */
    AspectConfigException(String message) {
        super(message);
    }


    AspectConfigException(String message, Throwable cause) {
        super(message, cause);
    }
}
