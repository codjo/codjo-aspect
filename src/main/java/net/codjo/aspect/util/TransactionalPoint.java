/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect.util;
import net.codjo.aspect.AspectContext;
import net.codjo.aspect.AspectException;
import net.codjo.aspect.AspectHelper;
import net.codjo.aspect.AspectManager;
import net.codjo.aspect.AspectFilter;
import java.sql.Connection;
import java.sql.SQLException;
/**
 * Classe utilitaire pour l'implémentation de l'exécution du point transactionnel et de tous les aspects qui
 * lui sont attachés.
 *
 * <p> exemple d'implementation : </p>
 * <pre>
 * <code>
 * void executeDispatch(TransactionalPoint point, final Connection con, ...) {
 *        AspectContext ctxt = newAspectContextForMe();
 *       ctxt.put(TransactionalPoint.CONNECTION, con);
 *       ctxt.put(TransactionalPoint.ARGUMENT, ctrl.getQuarantineTable());
 *        PointRunner runner =
 *           new PointRunner() {
 *               public void run() throws PointRunnerException {
 *                   try {
 *                       ctrl.executeDispatch(...);
 *                   }
 *                   catch (Exception e) {
 *                       throw new PointRunnerException(e);
 *                   }
 *               }
 *           };
 *         point.run(ctxt, runner);
 *   }
 *  </code>
 * </pre>
 */
public class TransactionalPoint {
    public static final String CONNECTION = "connection";
    public static final String ARGUMENT = "argument";
    private String pointId;
    private AspectManager manager;
    private TransactionalManager txManager;


    public TransactionalPoint(String pointId, AspectManager manager) {
        this(pointId, manager, new JdbcTransactionalManager());
    }


    public TransactionalPoint(String pointId, AspectManager manager, TransactionalManager txManager) {
        this.txManager = txManager;
        this.pointId = pointId;
        this.manager = manager;
    }


    public void run(AspectContext context, PointRunner pointRunner)
          throws TransactionException, AspectException, PointRunnerException {
        getNotNull(context, TransactionalPoint.CONNECTION);
        AspectHelper aspectHelper = buildHelper(context);

        try {
            aspectHelper.setUp(context);
            txManager.begin(context);

            try {
                aspectHelper.runBefore(context);
                pointRunner.run();
                txManager.flush(context);
                aspectHelper.runAfter(context);
                txManager.commit(context);
            }
            catch (PointRunnerException ex) {
                // On rollback d'abord la transaction, puis la même connexion en autocommit
                // est utilisée par runError.
                txManager.rollback(context);
                runError(aspectHelper, context);
                throw ex;
            }
            catch (AspectException ex) {
                // Idem
                txManager.rollback(context);
                runError(aspectHelper, context);
                throw ex;
            }
            catch (Throwable e) {
                txManager.rollback(context);
                throw new PointRunnerException(e.getLocalizedMessage(), e);
            }
            finally {
                txManager.end(context);
            }
        }
        finally {
            aspectHelper.cleanUp(context);
        }
    }


    public String getPointId() {
        return pointId;
    }


    public AspectManager getManager() {
        return manager;
    }


    private static Object getNotNull(AspectContext context, String key) {
        Object obj = context.get(key);
        assertNotNull(obj, key);
        return obj;
    }


    private static void assertNotNull(Object obj, String key) {
        if (null == obj) {
            throw new IllegalArgumentException(
                  "Objet manquant dans le context sous la clé : " + key);
        }
    }


    private void runError(AspectHelper aspectHelper, AspectContext context) {
        try {
            txManager.end(context);
            aspectHelper.runError(context);
        }
        catch (Exception e) {
            // En cas d'erreur, on ignore : c'est l'exception d'origine (qui a
            // provoqué l'appel de l'aspect d'erreur) qui sera remontée au code
            // appelant.
            ;
        }
    }


    private AspectHelper buildHelper(AspectContext context)
          throws AspectException {
        Object arg = context.get(TransactionalPoint.ARGUMENT);
        if (arg instanceof String[]) {
            return manager.createHelper(pointId, (String[])arg, AspectFilter.ALL);
        }
        else {
            return manager.createHelper(pointId, (String)arg, AspectFilter.ALL);
        }
    }


    private static class JdbcTransactionalManager implements TransactionalManager {
        public void begin(AspectContext ctxt) throws TransactionException {
            try {
                getConnection(ctxt).setAutoCommit(false);
            }
            catch (SQLException e) {
                throw new TransactionException(e);
            }
        }


        public void commit(AspectContext ctxt) throws TransactionException {
            try {
                getConnection(ctxt).commit();
            }
            catch (SQLException e) {
                throw new TransactionException(e);
            }
        }


        public void rollback(AspectContext ctxt) throws TransactionException {
            try {
                getConnection(ctxt).rollback();
            }
            catch (SQLException e) {
                throw new TransactionException(e);
            }
        }


        public void end(AspectContext ctxt) throws TransactionException {
            try {
                getConnection(ctxt).setAutoCommit(true);
            }
            catch (SQLException e) {
                throw new TransactionException(e);
            }
        }


        public void flush(AspectContext ctxt) throws TransactionException {
        }


        private Connection getConnection(AspectContext ctxt) {
            return (Connection)getNotNull(ctxt, TransactionalPoint.CONNECTION);
        }
    }
}
