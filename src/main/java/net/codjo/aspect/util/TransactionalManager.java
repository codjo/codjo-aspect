/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect.util;
import net.codjo.aspect.AspectContext;
/**
 * Gestionnaire de transaction pour un {@link TransactionalPoint}.
 */
public interface TransactionalManager {
    void begin(AspectContext ctxt) throws TransactionException;


    void commit(AspectContext ctxt) throws TransactionException;


    void rollback(AspectContext ctxt) throws TransactionException;


    void end(AspectContext ctxt) throws TransactionException;


    void flush(AspectContext ctxt) throws TransactionException;
}
