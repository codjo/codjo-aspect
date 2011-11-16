package net.codjo.aspect;
import net.codjo.test.common.LogString;

public class AspectMock implements Aspect {
    private LogString log;


    public AspectMock(LogString log) {
        this.log = log;
    }


    public void setUp(AspectContext context, JoinPoint joinPoint) throws AspectException {
        log.call("setUp", context, joinPoint);
    }


    public void run(AspectContext context) throws AspectException {
        log.call("run", context);
    }


    public void cleanUp(AspectContext context) throws AspectException {
        log.call("cleanUp", context);
    }
}
