/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect;
/**
 * Cette classe permet de simplifier la gestion des aspects, en réunissant les aspects <code>before</code>,
 * <code>after</code> et <code>error</code>.
 *
 * @version $Revision: 1.5 $
 */
class MonoAspectHelper implements AspectHelper {
    private Aspect beforeAspect;
    private Aspect afterAspect;
    private Aspect errorAspect;
    private JoinPoint joinPointBefore;
    private JoinPoint joinPointAfter;
    private JoinPoint joinPointError;


    MonoAspectHelper(String point,
                     String argument,
                     Aspect beforeAspect,
                     Aspect afterAspect,
                     Aspect errorAspect) {
        joinPointBefore = new JoinPoint(JoinPoint.CALL_BEFORE, point, argument);
        joinPointAfter = new JoinPoint(JoinPoint.CALL_AFTER, point, argument);
        joinPointError = new JoinPoint(JoinPoint.CALL_ERROR, point, argument);

        this.beforeAspect = beforeAspect;
        this.afterAspect = afterAspect;
        this.errorAspect = errorAspect;
    }


    /**
     * Lance l'exécution des {@link Aspect#setUp} des aspects <code>before</code>, <code>after</code> et
     * <code>error</code>.
     *
     * @param context Le contexte d'exécution de l'aspect.
     *
     * @throws AspectException si une erreur survient
     */
    public void setUp(AspectContext context) throws AspectException {
        beforeAspect.setUp(context, joinPointBefore);
        afterAspect.setUp(context, joinPointAfter);
        errorAspect.setUp(context, joinPointError);
    }


    /**
     * Lance l'exécution de l'aspect <code>before</code>.
     *
     * @param context Le contexte d'exécution de l'aspect.
     *
     * @throws AspectException si une erreur survient
     */
    public void runBefore(AspectContext context) throws AspectException {
        beforeAspect.run(context);
    }


    /**
     * Lance l'exécution de l'aspect <code>after</code>.
     *
     * @param context Le contexte d'exécution de l'aspect.
     *
     * @throws AspectException si une erreur survient
     */
    public void runAfter(AspectContext context) throws AspectException {
        afterAspect.run(context);
    }


    /**
     * Lance l'exécution de l'aspect <code>error</code>.
     *
     * @param context Le contexte d'exécution de l'aspect.
     *
     * @throws AspectException si une erreur survient
     */
    public void runError(AspectContext context) throws AspectException {
        errorAspect.run(context);
    }


    /**
     * Lance l'exécution des {@link Aspect#cleanUp} des aspects <code>before</code>, <code>after</code> et
     * <code>error</code>.
     *
     * @param context Le contexte d'exécution de l'aspect.
     *
     * @throws AspectException si une erreur survient
     */
    public void cleanUp(AspectContext context) throws AspectException {
        beforeAspect.cleanUp(context);
        afterAspect.cleanUp(context);
        errorAspect.cleanUp(context);
    }
}
