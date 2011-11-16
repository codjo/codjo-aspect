/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect;
/**
 * Un aspect qui provoque des exceptions.
 *
 * @version $Revision: 1.2 $
 */
public class BugAspect implements Aspect {
    public void setUp(AspectContext context, JoinPoint joinPoint)
            throws AspectException {
        try {
            Class.forName("net.codjo.aspect.SomeUnknownClass");
        }
        catch (ClassNotFoundException e) {
            throw new AspectException("Impossible d'initialiser l'aspect.", e);
        }
    }


    public void run(AspectContext context) throws AspectException {}


    public void cleanUp(AspectContext context) throws AspectException {}
}
