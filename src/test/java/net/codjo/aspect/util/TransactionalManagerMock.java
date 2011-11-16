package net.codjo.aspect.util;
import net.codjo.aspect.AspectContext;
import net.codjo.test.common.LogString;
/**
 *
 */
public class TransactionalManagerMock implements TransactionalManager {
    private LogString log;


    public TransactionalManagerMock() {
        this(new LogString());
    }


    public TransactionalManagerMock(LogString log) {
        this.log = log;
    }


    public void begin(AspectContext ctxt) throws TransactionException {
        log.call("begin");
    }


    public void commit(AspectContext ctxt) throws TransactionException {
        log.call("commit");
    }


    public void rollback(AspectContext ctxt) throws TransactionException {
        log.call("rollback");
    }


    public void end(AspectContext ctxt) throws TransactionException {
        log.call("end");
    }


    public void flush(AspectContext ctxt) throws TransactionException {
        log.call("flush");
    }
}
