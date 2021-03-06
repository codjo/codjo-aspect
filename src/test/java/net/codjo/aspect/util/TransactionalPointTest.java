/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect.util;
import java.sql.SQLException;
import net.codjo.aspect.Aspect;
import net.codjo.aspect.AspectContext;
import net.codjo.aspect.AspectException;
import net.codjo.aspect.AspectManager;
import net.codjo.aspect.JoinPoint;
import net.codjo.test.common.LogString;
import net.codjo.test.common.mock.ConnectionMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TransactionalPointTest {
    private static final String POINT_ID = "pointId";
    private static final String CURRENT_ARG = "currentArgument";
    private static MyTransactionalManager txManager;
    private static StringBuffer globalExecuteList;
    private static final String LOG_STRING = "LogString";
    private LogString log = new LogString();
    private TransactionalPoint transactionalPoint;
    private AspectContext aspectContext;
    private ConnectionMock con;
    private AspectManager manager;
    private MyPointRunner myPointRunner;


    @Before
    public void setUp() throws Exception {
        globalExecuteList = new StringBuffer();

        txManager = new MyTransactionalManager();
        aspectContext = new AspectContext();
        con = new ConnectionMock(new LogString("connection", log));

        aspectContext.put(TransactionalPoint.CONNECTION, con.getStub());
        aspectContext.put(TransactionalPoint.ARGUMENT, CURRENT_ARG);
        aspectContext.put(LOG_STRING, log);

        manager = new AspectManager();
        transactionalPoint = new TransactionalPoint(POINT_ID, manager);

        myPointRunner = new MyPointRunner();
        FakeBeforeAspect.failInSetup = false;
        FakeBeforeAspect.failInRun = false;
    }


    @Test
    public void test_aspect_multipleJoinPoint() throws Exception {
        // Aspect
        manager.addAspect("aspectJPa",
                          newJoinPoint(JoinPoint.CALL_BEFORE, POINT_ID, "argA"), FakeBeforeAspect.class);
        manager.addAspect("aspectAfter",
                          newJoinPoint(JoinPoint.CALL_AFTER, POINT_ID, "argB"), FakeAfterAspect.class);

        // Appel
        aspectContext.put(TransactionalPoint.ARGUMENT, new String[]{"argA", "argB"});

        transactionalPoint.run(aspectContext, myPointRunner);

        // Verification
        assertEquals("setUp, run, end", FakeBeforeAspect.executeList.toString());
        assertEquals("setUp, run, end", FakeAfterAspect.executeList.toString());
    }


    @Test
    public void test_aspect_ok() throws Exception {
        // Aspect
        manager.addAspect("aspectBefore",
                          newJoinPoint(JoinPoint.CALL_BEFORE, POINT_ID, CURRENT_ARG),
                          FakeBeforeAspect.class);
        manager.addAspect("aspectAfter",
                          newJoinPoint(JoinPoint.CALL_AFTER, POINT_ID, CURRENT_ARG),
                          FakeAfterAspect.class);

        // Appel
        transactionalPoint.run(aspectContext, myPointRunner);

        // Verification
        assertEquals("setUp, run, end", FakeBeforeAspect.executeList.toString());
        assertEquals("setUp, run, end", FakeAfterAspect.executeList.toString());
        Assert.assertSame("Le bon contexte est pass� aux Aspects", aspectContext,
                          FakeBeforeAspect.aspectCtxt);

        assertEquals("FakeBeforeAspect.run FakeAfterAspect.run ",
                     globalExecuteList.toString());
    }


    @Test
    public void test_transaction_ok() throws Exception {
        // Aspect
        manager.addAspect("aspectBefore",
                          newJoinPoint(JoinPoint.CALL_BEFORE, POINT_ID, CURRENT_ARG),
                          FakeBeforeAspect.class);

        // Appel
        transactionalPoint.run(aspectContext, myPointRunner);

        // Assert sur aspect
        assertEquals("setUp, run, end", FakeBeforeAspect.executeList.toString());
        assertFalse("SetUp pas en transaction", FakeBeforeAspect.isSetupInTx);
        assertTrue("Run pas en transaction", FakeBeforeAspect.isRunInTx);
        assertFalse("CleanUp pas en transaction", FakeBeforeAspect.isCleanUpInTx);

        // Assert sur pointRunner
        assertTrue("Runner appel�", myPointRunner.runHasBeenCalled);
        assertTrue("Runner appel� en Tx", myPointRunner.runInTx);
    }


    @Test
    public void test_transaction_flush() throws Exception {
        // Aspect
        transactionalPoint = new TransactionalPoint(POINT_ID, manager, txManager);
        manager.addAspect("aspectAfter",
                          newJoinPoint(JoinPoint.CALL_AFTER, POINT_ID, CURRENT_ARG),
                          FakeAfterAspect.class);

        // Appel
        transactionalPoint.run(aspectContext, myPointRunner);

        // Assert sur aspect
        assertTrue("Run apr�s le flush", FakeAfterAspect.isRunAfterFlush);
    }


    @Test
    public void test_transaction_userDefined_ok() throws Exception {
        transactionalPoint = new TransactionalPoint(POINT_ID, manager, txManager);

        transactionalPoint.run(aspectContext, myPointRunner);

        assertEquals("begin, flush, commit, end", txManager.executeList.toString());
    }


    @Test
    public void test_argument_userNotDefined_ok() throws Exception {
        transactionalPoint = new TransactionalPoint(POINT_ID, manager, txManager);

        aspectContext.put(TransactionalPoint.ARGUMENT, null);

        transactionalPoint.run(aspectContext, myPointRunner);

        assertEquals("begin, flush, commit, end", txManager.executeList.toString());
    }


    @Test
    public void test_context_connection_nok() throws Exception {
        // Appel
        AspectContext emptyContext = new AspectContext();
        try {
            transactionalPoint.run(emptyContext, myPointRunner);
            fail("Echec : context vide");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Objet manquant dans le context sous la cl� : "
                         + TransactionalPoint.CONNECTION, e.getMessage());
        }
    }


    /**
     * Test le cas d'echec � l'init d'un aspect.
     *
     * @throws Exception erreur.
     */
    @Test
    public void test_fail_init() throws Exception {
        // Aspect
        manager.addAspect("aspectBefore",
                          newJoinPoint(JoinPoint.CALL_BEFORE, POINT_ID, CURRENT_ARG),
                          FakeBeforeAspect.class);

        // Appel
        FakeBeforeAspect.failInSetup = true;
        try {
            transactionalPoint.run(aspectContext, myPointRunner);
            fail("Echec durant l'init");
        }
        catch (AspectException e) {
            assertEquals("failInSetup", e.getMessage());
        }

        assertEquals("setUp, end", FakeBeforeAspect.executeList.toString());
        assertEquals("Rien n'est pas appel� sur la connection", "", log.getContent());
    }


    /**
     * Test le cas d'echec durant l'execution d'un aspect.
     *
     * @throws Exception erreur.
     */
    @Test
    public void test_fail_run() throws Exception {
        // Aspect
        manager.addAspect("aspectBefore",
                          newJoinPoint(JoinPoint.CALL_BEFORE, POINT_ID, CURRENT_ARG),
                          FakeBeforeAspect.class);
        manager.addAspect("aspectError",
                          newJoinPoint(JoinPoint.CALL_ERROR, POINT_ID, CURRENT_ARG),
                          FakeErrorAspect.class);

        // Appel
        FakeBeforeAspect.failInRun = true;
        try {
            transactionalPoint.run(aspectContext, myPointRunner);
            fail("Echec durant le run");
        }
        catch (AspectException e) {
            assertEquals("failInRun", e.getMessage());
        }

        assertEquals("setUp, run, end", FakeBeforeAspect.executeList.toString());
        assertEquals("setUp, run, end", FakeErrorAspect.executeList.toString());
        log.assertContent("connection.setAutoCommit(false), connection.rollback(), connection.setAutoCommit(true), connection.setAutoCommit(true)");
    }


    /**
     * Test le cas d'echec durant l'execution d'un aspect.
     *
     * @throws Exception    erreur.
     * @throws SQLException erreur
     */
    @Test
    public void test_fail_runAndRollBack() throws Exception {
        // Aspect
        manager.addAspect("aspectBefore",
                          newJoinPoint(JoinPoint.CALL_BEFORE, POINT_ID, CURRENT_ARG),
                          FakeBeforeAspect.class);

        // Appel
        FakeBeforeAspect.failInRun = true;
        con = new ConnectionMock(new LogString("connection", log)) {
            @Override
            public void rollback() throws SQLException {
                super.rollback();
                throw new SQLException("Erreur rollback");
            }
        };
        aspectContext.put(TransactionalPoint.CONNECTION, con.getStub());

        try {
            transactionalPoint.run(aspectContext, myPointRunner);
            fail("Echec durant l'init");
        }
        catch (TransactionException e) {
            assert (e.getMessage().contains("java.sql.SQLException: Erreur rollback"));
        }

        assertEquals("setUp, run, end", FakeBeforeAspect.executeList.toString());
        log.assertContent("connection.setAutoCommit(false), connection.rollback(), connection.setAutoCommit(true)");
    }


    private JoinPoint[] newJoinPoint(int call, String point, String argument) {
        JoinPoint joinPoint = new JoinPoint();
        joinPoint.setArgument(argument);
        joinPoint.setPoint(point);
        joinPoint.setCall(call);
        return new JoinPoint[]{joinPoint};
    }


    public static class FakeBeforeAspect implements Aspect {
        static StringBuffer executeList;
        static AspectContext aspectCtxt;
        static boolean isSetupInTx;
        static boolean isRunInTx;
        static boolean isCleanUpInTx;
        static boolean failInSetup = false;
        static boolean failInRun = false;


        public void setUp(AspectContext context, JoinPoint joinPoint)
              throws AspectException {
            executeList = new StringBuffer();
            executeList.append("setUp");
            aspectCtxt = context;
            isSetupInTx = isInTx(context);
            if (failInSetup) {
                throw new AspectException("failInSetup");
            }
        }


        public void run(AspectContext context) throws AspectException {
            executeList.append(", run");
            isRunInTx = isInTx(context);
            globalExecuteList.append("FakeBeforeAspect.run ");
            if (failInRun) {
                throw new AspectException("failInRun");
            }
        }


        public void cleanUp(AspectContext context)
              throws AspectException {
            executeList.append(", end");
            isCleanUpInTx = isInTx(context);
        }


        private boolean isInTx(AspectContext context) {
            LogString log = (LogString)context.get(LOG_STRING);
            String content = log.getContent();
            int auto = content.lastIndexOf("setAutoCommit(true)");
            int notAuto = content.lastIndexOf("setAutoCommit(false)");
            boolean autoCommit = true;
            if (notAuto > auto) {
                autoCommit = false;
            }
            return !autoCommit;
        }
    }

    public static class FakeAfterAspect implements Aspect {
        static StringBuffer executeList;
        static boolean isRunAfterFlush = false;


        public void setUp(AspectContext context, JoinPoint joinPoint)
              throws AspectException {
            executeList = new StringBuffer();
            executeList.append("setUp");
        }


        public void run(AspectContext context) throws AspectException {
            executeList.append(", run");
            globalExecuteList.append("FakeAfterAspect.run ");
            isRunAfterFlush = txManager.isFlushCalled();
        }


        public void cleanUp(AspectContext context)
              throws AspectException {
            executeList.append(", end");
        }
    }

    public static class FakeErrorAspect implements Aspect {
        static StringBuffer executeList;


        public void setUp(AspectContext context, JoinPoint joinPoint)
              throws AspectException {
            executeList = new StringBuffer();
            executeList.append("setUp");
        }


        public void run(AspectContext context) throws AspectException {
            executeList.append(", run");
        }


        public void cleanUp(AspectContext context)
              throws AspectException {
            executeList.append(", end");
        }
    }

    public class MyPointRunner implements PointRunner {
        boolean runHasBeenCalled;
        boolean runInTx;


        public void run() throws PointRunnerException {
            runHasBeenCalled = true;
            try {
                runInTx = !con.getAutoCommit();
            }
            catch (SQLException e) {
                throw new InternalError("Erreur " + e);
            }
        }
    }

    public static class MyTransactionalManager implements TransactionalManager {
        StringBuffer executeList = new StringBuffer();


        public void begin(AspectContext ctxt) {
            executeList.append("begin");
        }


        public void flush(AspectContext ctxt) {
            executeList.append(", flush");
        }


        public void commit(AspectContext ctxt) {
            executeList.append(", commit");
        }


        public void rollback(AspectContext ctxt) {
            executeList.append(", rollback");
        }


        public void end(AspectContext ctxt) {
            executeList.append(", end");
        }


        public boolean isFlushCalled() {
            return executeList.toString().contains("flush");
        }
    }
}
