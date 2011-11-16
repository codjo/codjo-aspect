/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect;
/**
 * Cette interface permet de simplifier la gestion des aspects, en réunissant les aspects
 * <code>before</code>, <code>after</code> et <code>error</code>.
 *
 * @version $Revision: 1.5 $
 */
public interface AspectHelper {
    /**
     * Lance l'exécution des {@link Aspect#setUp} des aspects <code>before</code>,
     * <code>after</code> et <code>error</code>.
     *
     * @param context Le contexte d'exécution de l'aspect.
     *
     * @throws AspectException si une erreur survient
     */
    public void setUp(AspectContext context) throws AspectException;


    /**
     * Lance l'exécution de l'aspect <code>before</code>.
     *
     * @param context Le contexte d'exécution de l'aspect.
     *
     * @throws AspectException si une erreur survient
     */
    public void runBefore(AspectContext context) throws AspectException;


    /**
     * Lance l'exécution de l'aspect <code>after</code>.
     *
     * @param context Le contexte d'exécution de l'aspect.
     *
     * @throws AspectException si une erreur survient
     */
    public void runAfter(AspectContext context) throws AspectException;


    /**
     * Lance l'exécution de l'aspect <code>error</code>.
     *
     * @param context Le contexte d'exécution de l'aspect.
     *
     * @throws AspectException si une erreur survient
     */
    public void runError(AspectContext context) throws AspectException;


    /**
     * Lance l'exécution des {@link Aspect#cleanUp} des aspects <code>before</code>,
     * <code>after</code> et <code>error</code>.
     *
     * @param context Le contexte d'exécution de l'aspect.
     *
     * @throws AspectException si une erreur survient
     */
    public void cleanUp(AspectContext context) throws AspectException;
}
