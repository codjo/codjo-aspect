/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect.util;
import net.codjo.aspect.Aspect;
import net.codjo.aspect.AspectContext;
import net.codjo.aspect.AspectException;
import net.codjo.aspect.JoinPoint;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
/**
 * Cette classe permet de gérer plusieurs déclenchements du même aspect. Il permet
 * d'avoir à la fois un comportement de type oneShot ainsi qu'un comportement spécifique
 * par type de JoinPoint.
 * 
 * <p>
 * Exemple :
 * <pre>
 *   public class MyMultiCallAspect extends AbstractMultiCallAspect {
 *     protected Aspect getOneShotAspect() {
 *       return new ComputeAspect();
 *     }
 *     protected Aspect getJoinPointAspect(JoinPoint point) {
 *        if ("OstAdjustmentRateAspectId".equals(joinPoint.getArgument())) {
 *          return new OstSpecificJoinPointAspect();
 *        }
 *        else if ("DividendAdjustmentRateAspectId".equals(joinPoint.getArgument())) {
 *          return new DarSpecificPointAspect();
 *        }
 *        throw new AspectException("JoinPoint inconnu : " + joinPoint);
 *     }
 *  }
 * </pre>
 * </p>
 */
public abstract class AbstractMultiCallAspect implements Aspect {
    private static final Aspect NULL_ASPECT = new EmptyAspect();

    public void setUp(AspectContext context, JoinPoint joinPoint)
            throws AspectException {
        getOneShotWrapper().setUp(context, joinPoint);

        Aspect joinPointAspect = getJoinPointAspect(joinPoint);
        if (joinPointAspect != null) {
            joinPointAspect.setUp(context, joinPoint);
            TodoTask task = new TodoTask(joinPoint, joinPointAspect);
            getTodoList(context, getTodoKey("run")).add(task);
            getTodoList(context, getTodoKey("cleanUp")).add(task);
        }
    }


    public void run(AspectContext context) throws AspectException {
        popAspect(context, "run").run(context);
        getOneShotWrapper().run(context);
    }


    public void cleanUp(AspectContext context) throws AspectException {
        popAspect(context, "cleanUp").cleanUp(context);
        getOneShotWrapper().cleanUp(context);
    }


    protected abstract Aspect getOneShotAspect() throws AspectException;


    protected abstract Aspect getJoinPointAspect(JoinPoint point)
            throws AspectException;


    private Aspect popAspect(AspectContext context, String todoType) {
        List todoList = getTodoList(context, getTodoKey(todoType));
        if (todoList.size() > 0) {
            return ((TodoTask)todoList.remove(0)).getAspect();
        }
        return NULL_ASPECT;
    }


    private List getTodoList(AspectContext context, String todoKey) {
        List todos = (List)context.get(todoKey);
        if (todos == null) {
            todos = new ArrayList();
            context.put(todoKey, todos);
        }
        return todos;
    }


    private String getTodoKey(String todoType) {
        return getClass().getName() + "__todo_" + todoType;
    }


    private Aspect getOneShotWrapper() throws AspectException {
        return new OneShotWrapper(getOneShotAspect());
    }

    private static class OneShotWrapper extends AbstractBasicAspect {
        private final Aspect subAspect;

        OneShotWrapper(Aspect subAspect) {
            if (subAspect == null) {
                subAspect = NULL_ASPECT;
                log = null;
            }
            else {
                log = Logger.getLogger(subAspect.getClass());
            }
            this.subAspect = subAspect;
        }

        @Override
        protected boolean isOneShotAspect() {
            return true;
        }


        @Override
        protected String getAspectSyncKey() {
            return subAspect.getClass().getName() + "__wrapper";
        }


        @Override
        protected void doSetUp(AspectContext context, JoinPoint joinPoint)
                throws AspectException {
            subAspect.setUp(context, joinPoint);
        }


        @Override
        protected void doRun(AspectContext context)
                throws AspectException {
            subAspect.run(context);
        }


        @Override
        protected void doCleanUp(AspectContext context)
                throws Exception {
            subAspect.cleanUp(context);
        }
    }


    private static class EmptyAspect implements Aspect {
        public void setUp(AspectContext context, JoinPoint joinPoint) {}


        public void run(AspectContext context) {}


        public void cleanUp(AspectContext context) {}
    }


    private static class TodoTask {
        private JoinPoint joinPoint;
        private Aspect aspect;

        TodoTask(JoinPoint joinPoint, Aspect aspect) {
            this.joinPoint = joinPoint;
            this.aspect = aspect;
        }

        public JoinPoint getJoinPoint() {
            return joinPoint;
        }


        public Aspect getAspect() {
            return aspect;
        }
    }
}
