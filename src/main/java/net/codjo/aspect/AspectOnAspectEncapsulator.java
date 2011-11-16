/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect;
import java.util.ArrayList;
import java.util.List;
/**
 * Classe permettant d'encapsuler un aspect afin de créer tous les aspects sur aspect qui
 * en dépendent.
 *
 * @version $Revision: 1.4 $
 */
class AspectOnAspectEncapsulator implements Aspect {
    private Aspect realAspect;
    private AspectHelper aspectHelper;

    AspectOnAspectEncapsulator(Aspect son,
                               AspectManager manager,
                               AspectFilter aspectFilter,
                               List<String> circularRef)
            throws AspectConfigException, AspectException {
        realAspect = son;
        String aspectId = manager.getAspectId(son.getClass());
        if (aspectId == null) {
            return;
        }

        if (circularRef != null && circularRef.contains(aspectId)) {
            throw new AspectException("Reference circulaire dans les aspect sur aspect :"
                + circularRef.toString() + " -> " + aspectId);
        }
        if (manager.isExistsAspect(JoinPoint.ON_ASPECT, aspectId)) {
            if (circularRef == null) {
                circularRef = new ArrayList<String>();
            }
            circularRef.add(aspectId);
            aspectHelper = manager.createHelper(JoinPoint.ON_ASPECT, aspectId, aspectFilter, circularRef);
        }
    }

    public void setUp(AspectContext context, JoinPoint joinPoint)
            throws AspectException {
        realAspect.setUp(context, joinPoint);
        if (aspectHelper != null) {
            aspectHelper.setUp(context);
        }
    }


    public void run(AspectContext context) throws AspectException {
        if (aspectHelper != null) {
            aspectHelper.runBefore(context);
        }
        realAspect.run(context);
        if (aspectHelper != null) {
            aspectHelper.runAfter(context);
        }
    }


    public void cleanUp(AspectContext context) throws AspectException {
        realAspect.cleanUp(context);

        if (aspectHelper != null) {
            aspectHelper.cleanUp(context);
        }
    }


    @Override
    public String toString() {
        return "AspectOnAspect call " + this.realAspect.toString();
    }
}
