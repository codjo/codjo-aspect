/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect.util;
/**
 * Exception lanc� par le {@link TransactionalManager} lors d'une erreur li� � la gestion
 * de la transaction.
 */
public class TransactionException extends Exception {
    public TransactionException(String msg) {
        super(msg);
    }


    public TransactionException(Throwable cause) {
        super(cause);
    }


    public TransactionException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
