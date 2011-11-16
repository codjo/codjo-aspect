/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect;
import java.util.List;
import org.apache.log4j.Logger;
/**
 * Classe qui fusionne tous les aspects (soit before, soit after) d'un noeud donné. Elle peut fusionner de 0 à
 * n aspects.
 *
 * @version $Revision: 1.5 $
 */
class MultipleAspect implements Aspect {
    private static final Logger LOG = Logger.getLogger(Aspect.class);
    private List aspectList;


    MultipleAspect(List aspectList) {
        this.aspectList = aspectList;
    }


    public void setUp(AspectContext context, JoinPoint joinPoint)
            throws AspectException {
        if (LOG.isDebugEnabled() && aspectList.size() > 0) {
            LOG.debug("Declenchement Setup(" + joinPoint + ") : " + aspectList);
        }
        for (Object anAspectList : aspectList) {
            Aspect aspect = (Aspect)anAspectList;
            aspect.setUp(context, joinPoint);
        }
    }


    public void run(AspectContext context) throws AspectException {
        if (LOG.isDebugEnabled() && aspectList.size() > 0) {
            LOG.debug("Declenchement run() : " + aspectList);
        }
        for (Object anAspectList : aspectList) {
            Aspect aspect = (Aspect)anAspectList;
            aspect.run(context);
        }
    }


    public void cleanUp(AspectContext context) throws AspectException {
        if (LOG.isDebugEnabled() && aspectList.size() > 0) {
            LOG.debug("Declenchement cleanUp() : " + aspectList);
        }
        for (Object anAspectList : aspectList) {
            Aspect aspect = (Aspect)anAspectList;
            aspect.cleanUp(context);
        }
    }
}
