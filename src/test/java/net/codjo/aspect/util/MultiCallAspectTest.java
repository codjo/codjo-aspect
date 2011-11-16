/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect.util;
import net.codjo.aspect.Aspect;
import net.codjo.aspect.AspectContext;
import net.codjo.aspect.JoinPoint;
import net.codjo.aspect.AspectMock;
import net.codjo.test.common.LogString;
import junit.framework.TestCase;

/**
 *
 */
public class MultiCallAspectTest extends TestCase {
    private LogString log = new LogString();
    private AspectContext context = new AspectContext();


    public void test_oneShotAspectWithoutJoinPointAspect() throws Exception {
        AbstractMultiCallAspect multiCallAspect =
              new MultiCallAspect() {
                  @Override
                  protected Aspect getJoinPointAspect(JoinPoint point) {
                      return null;
                  }
              };

        multiCallAspect.setUp(context, createJoinPoint("control.dispatch", "MA_TABLE"));
        multiCallAspect.setUp(context, createJoinPoint("control.dispatch", "MA_TABLE"));

        multiCallAspect.run(context);
        multiCallAspect.run(context);

        multiCallAspect.cleanUp(context);
        multiCallAspect.cleanUp(context);

        log.assertContent("oneShot.setUp(" + context
                          + ", JoinPoint{after, control.dispatch(MA_TABLE), fork = false})" + ", oneShot.run("
                          + context + ")" + ", oneShot.cleanUp(" + context + ")");
    }


    public void test_oneJoinPointAspect_WithoutOneShot() throws Exception {
        MultiCallAspect multiCallAspect =
              new MultiCallAspect() {
                  @Override
                  protected Aspect getOneShotAspect() {
                      return null;
                  }
              };

        multiCallAspect.setUp(context, createJoinPoint("control", "MA_TABLE"));
        multiCallAspect.run(context);
        multiCallAspect.cleanUp(context);

        log.assertContent("control.setUp(" + context
                          + ", JoinPoint{after, control(MA_TABLE), fork = false})" + ", control.run(" + context + ")"
                          + ", control.cleanUp(" + context + ")");
    }


    public void test_twoJoinPointAspect_WithoutOneShot() throws Exception {
        MultiCallAspect multiCallAspect =
              new MultiCallAspect() {
                  @Override
                  protected Aspect getOneShotAspect() {
                      return null;
                  }
              };

        multiCallAspect.setUp(context, createJoinPoint("control", "MA_TABLE"));
        multiCallAspect.setUp(context, createJoinPoint("handler", "monHandler"));

        multiCallAspect.run(context);
        multiCallAspect.run(context);

        multiCallAspect.cleanUp(context);
        multiCallAspect.cleanUp(context);

        log.assertContent("control.setUp(" + context
                          + ", JoinPoint{after, control(MA_TABLE), fork = false})" + ", handler.setUp(" + context
                          + ", JoinPoint{after, handler(monHandler), fork = false})" + ", control.run(" + context
                          + ")" + ", handler.run(" + context + ")" + ", control.cleanUp(" + context
                          + ")" + ", handler.cleanUp(" + context + ")");
    }


    public void test_all() throws Exception {
        MultiCallAspect multiCallAspect = new MultiCallAspect();

        multiCallAspect.setUp(context, createJoinPoint("control", "MA_TABLE"));
        multiCallAspect.setUp(context, createJoinPoint("control2", "MA_TABLE"));

        multiCallAspect.run(context);
        multiCallAspect.run(context);

        multiCallAspect.cleanUp(context);
        multiCallAspect.cleanUp(context);

        log.assertContent("oneShot.setUp(" + context
                          + ", JoinPoint{after, control(MA_TABLE), fork = false})" + ", control.setUp(" + context
                          + ", JoinPoint{after, control(MA_TABLE), fork = false})" + ", control2.setUp(" + context
                          + ", JoinPoint{after, control2(MA_TABLE), fork = false})" + ", control.run(" + context
                          + ")" + ", control2.run(" + context + ")" + ", oneShot.run(" + context + ")"
                          + ", control.cleanUp(" + context + ")" + ", control2.cleanUp(" + context
                          + ")" + ", oneShot.cleanUp(" + context + ")");
    }


    private JoinPoint createJoinPoint(String point, String argument) {
        JoinPoint joinPoint = new JoinPoint();
        joinPoint.setCall(JoinPoint.CALL_AFTER);
        joinPoint.setPoint(point);
        joinPoint.setArgument(argument);
        return joinPoint;
    }


    public class MultiCallAspect extends AbstractMultiCallAspect {
        @Override
        protected Aspect getOneShotAspect() {
            return new AspectMock(new LogString("oneShot", log));
        }


        @Override
        protected Aspect getJoinPointAspect(JoinPoint point) {
            return new AspectMock(new LogString(point.getPoint(), log));
        }
    }
}
