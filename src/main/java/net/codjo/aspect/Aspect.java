/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect;
/**
 * Interface repr�sentant un aspect.
 *
 * @version $Revision: 1.4 $
 */
public interface Aspect {
    /**
     * M�thode appel�e avant {@link #run}. Elle permet de pr�parer l'ex�cution de
     * l'aspect selon le point de jointure (ex : cr�ation des tables temporaires).
     *
     * @param context Le contexte d'ex�cution de l'aspect.
     * @param joinPoint Le {@link JoinPoint}.
     *
     * @throws AspectException si une erreur survient
     */
    public void setUp(AspectContext context, JoinPoint joinPoint)
            throws AspectException;


    /**
     * Lance l'ex�cution de l'aspect.
     *
     * @param context Le contexte d'ex�cution de l'aspect.
     *
     * @throws AspectException si une erreur survient
     */
    public void run(AspectContext context) throws AspectException;


    /**
     * M�thode appel�e apr�s {@link #run}. Elle permet de faire le m�nage apr�s
     * l'ex�cution de l'aspect (ex : suppression des tables temporaires).
     *
     * @param context Le contexte d'ex�cution de l'aspect.
     *
     * @throws AspectException si une erreur survient
     */
    public void cleanUp(AspectContext context) throws AspectException;
}
