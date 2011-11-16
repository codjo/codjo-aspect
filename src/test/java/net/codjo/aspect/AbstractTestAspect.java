/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect;
import net.codjo.aspect.util.AbstractBasicAspect;
/**
 * Classe de base pour les aspects utilisés dans les tests unitaires.
 *
 * @version $Revision: 1.4 $
 */
public class AbstractTestAspect extends AbstractBasicAspect {
    static final String CALL_HISTORY = "callHistory";

    @Override
    public void doSetUp(AspectContext context, JoinPoint joinPoint)
            throws AspectException {
        logCall(context, "setUp");
    }


    @Override
    public void doRun(AspectContext context) throws AspectException {
        logCall(context, "run");
    }


    @Override
    public void doCleanUp(AspectContext context) throws AspectException {
        logCall(context, "cleanUp");
    }


    private void logCall(AspectContext context, String methodName) {
        String callHistory = (String)context.get(CALL_HISTORY);
        if (callHistory == null) {
            callHistory = "";
        }

        callHistory += getClass().getName() + "." + methodName + "() ";

        context.put(CALL_HISTORY, callHistory);
    }


    //permet de dire que l'on ne passe qu'une fois dans un aspect par appel
    @Override
    protected boolean isOneShotAspect() {
        return false;
    }
}
