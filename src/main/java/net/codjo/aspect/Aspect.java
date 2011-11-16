/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect;
/**
 * Interface représentant un aspect.
 *
 * @version $Revision: 1.4 $
 */
public interface Aspect {
    /**
     * Méthode appelée avant {@link #run}. Elle permet de préparer l'exécution de
     * l'aspect selon le point de jointure (ex : création des tables temporaires).
     *
     * @param context Le contexte d'exécution de l'aspect.
     * @param joinPoint Le {@link JoinPoint}.
     *
     * @throws AspectException si une erreur survient
     */
    public void setUp(AspectContext context, JoinPoint joinPoint)
            throws AspectException;


    /**
     * Lance l'exécution de l'aspect.
     *
     * @param context Le contexte d'exécution de l'aspect.
     *
     * @throws AspectException si une erreur survient
     */
    public void run(AspectContext context) throws AspectException;


    /**
     * Méthode appelée après {@link #run}. Elle permet de faire le ménage après
     * l'exécution de l'aspect (ex : suppression des tables temporaires).
     *
     * @param context Le contexte d'exécution de l'aspect.
     *
     * @throws AspectException si une erreur survient
     */
    public void cleanUp(AspectContext context) throws AspectException;
}
