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
import java.util.Date;
import org.apache.log4j.Logger;
import org.joda.time.Duration;
import org.joda.time.format.PeriodFormatterBuilder;
/**
 * Cette classe permet de simplifier l'implémentation de l'interface {@link Aspect}. Elle permet d'afficher
 * automatiquement des traces lors de l'appel des différentes méthodes. Elle permet aussi d'éviter que
 * l'aspect soit appelé plusieurs fois lorsqu'il est déclenché simultanément par plusieurs points de
 * connexion.
 *
 * @version $Revision: 1.6 $
 */
public abstract class AbstractBasicAspect implements Aspect {
    protected Logger log = Logger.getLogger(this.getClass());


    /**
     * Cette méthode doit être surchargée pour spécifier le comportement à adopter lors de déclenchements
     * multiples.
     *
     * @return <code>true</code> si l'aspect doit être protégé contre les appels multiples, sinon
     *         <code>false</code>.
     */
    protected abstract boolean isOneShotAspect();


    private AspectSynchronizerCounter getAspectSynchronizerCounter(AspectContext context) {
        AspectSynchronizerCounter sync = (AspectSynchronizerCounter)context.get(getAspectSyncKey());
        if (sync == null) {
            sync = new AspectSynchronizerCounter();
            context.put(getAspectSyncKey(), sync);
        }
        return sync;
    }


    /**
     * Retourne une clef spécifique a un aspect.
     *
     * <p> La clef ne doit pas dépendre de l'instance (plusieurs instance représente  le même aspect). </p>
     *
     * @return une clef unique pour l'aspect.
     */
    protected String getAspectSyncKey() {
        return getClass().getName() + "__inner_aspect_data.AspectSynchronizerCounter";
    }


    public final void setUp(AspectContext context, JoinPoint joinPoint) throws AspectException {
        AspectSynchronizerCounter counter = getAspectSynchronizerCounter(context);
        counter.callSetup();
        if (!counter.isSetupAllowed()) {
            logIfEnabled("SETUP IN ignoré (" + counter.getSetupCallCount() + ")");
        }
        else {
            logIfEnabled("SETUP IN (" + counter.getSetupCallCount() + ")");
            try {
                doSetUp(context, joinPoint);
            }
            catch (AspectException e) {
                logIfEnabled("SETUP OUT sur exception : " + e);
                throw e;
            }
            logIfEnabled("SETUP OUT ok");
        }
    }


    /**
     * Cette méthode doit être surchargée pour fournir l'implémentation de {@link
     * Aspect#setUp(net.codjo.aspect.AspectContext,net.codjo.aspect.JoinPoint)}.
     *
     * @param context   contexte d'appel de l'aspect
     * @param joinPoint point de jointure ayant déclenché l'aspect
     *
     * @throws AspectException si une erreur survient
     */
    protected abstract void doSetUp(AspectContext context, JoinPoint joinPoint) throws AspectException;


    public final void run(AspectContext context) throws AspectException {
        AspectSynchronizerCounter counter = getAspectSynchronizerCounter(context);
        counter.callRun();
        if (!counter.isRunAllowed()) {
            logIfEnabled("RUN IN ignoré (" + counter.getRunCallCount() + ")");
        }
        else {
            logIfEnabled("RUN IN (" + counter.getRunCallCount() + ")");
            try {
                Date startDate = new Date();
                doRun(context);
                if (log != null) {
                    log.info("Aspect has run for " + getOkRunningDuration(startDate, new Date()) + ".");
                }
            }
            catch (AspectException e) {
                logIfEnabled("RUN OUT sur exception : " + e);
                throw e;
            }
            logIfEnabled("RUN OUT ok");
        }
    }


    private String getOkRunningDuration(Date startDate, Date endDate) {
        return new Duration(startDate.getTime(), endDate.getTime()).toPeriod().toString(
              new PeriodFormatterBuilder()
                    .appendHours()
                    .appendSuffix(" h ")
                    .appendMinutes()
                    .appendSuffix(" min ")
                    .appendSecondsWithOptionalMillis()
                    .appendSuffix(" s")
                    .toFormatter());
    }


    private void logIfEnabled(final String message) {
        if (log != null && log.isDebugEnabled()) {
            log.debug(message);
        }
    }


    /**
     * Cette méthode doit être surchargée pour fournir l'implémentation de {@link
     * Aspect#run(net.codjo.aspect.AspectContext)}.
     *
     * @param context contexte d'appel de l'aspect
     *
     * @throws AspectException si une erreur survient
     */
    protected abstract void doRun(AspectContext context) throws AspectException;


    public final void cleanUp(AspectContext context) {
        AspectSynchronizerCounter counter = getAspectSynchronizerCounter(context);
        counter.callClean();
        if (!counter.isCleanAllowed()) {
            logIfEnabled("CLEAN IN ignoré (" + counter.getCleanCallCount() + ")");
        }
        else {
            logIfEnabled("CLEAN IN (" + counter.getCleanCallCount() + ")");
            try {
                doCleanUp(context);
            }
            catch (Exception e) {
                logIfEnabled("CLEAN OUT sur exception : " + e);
                // Pas de throw ici !
            }
            logIfEnabled("CLEAN OUT ok");
        }
    }


    /**
     * Cette méthode doit être surchargée pour fournir l'implémentation de {@link
     * Aspect#cleanUp(net.codjo.aspect.AspectContext)}
     *
     * @param context contexte d'appel de l'aspect
     *
     * @throws Exception si une erreur survient
     */
    protected abstract void doCleanUp(AspectContext context) throws Exception;


    private class AspectSynchronizerCounter {
        private int setupCallCount;
        private int runCallCount;
        private int cleanCallCount;


        public int getSetupCallCount() {
            return setupCallCount;
        }


        public int getRunCallCount() {
            return runCallCount;
        }


        public int getCleanCallCount() {
            return cleanCallCount;
        }


        private void callSetup() {
            setupCallCount++;
            runCallCount++;
            cleanCallCount++;
        }


        private void callRun() {
            runCallCount--;
        }


        private void callClean() {
            cleanCallCount--;
        }


        private boolean isSetupAllowed() {
            return setupCallCount == 1 || !isOneShotAspect();
        }


        private boolean isRunAllowed() {
            return runCallCount == 0 || !isOneShotAspect();
        }


        private boolean isCleanAllowed() {
            return cleanCallCount == 0 || !isOneShotAspect();
        }
    }
}
