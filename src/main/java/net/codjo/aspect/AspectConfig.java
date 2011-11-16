/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect;
import net.codjo.xml.fast.XmlParser;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.InputSource;
/**
 * Classe représentant la configuration d'un aspect, telle qu'elle est décrite dans le fichier XML associé.
 *
 * @version $Revision: 1.7 $
 */
class AspectConfig {
    private Class aspectClass;
    private String aspectClassName;
    private List joinPointList = new ArrayList();
    private String aspectId;


    AspectConfig() {
    }


    AspectConfig(String aspectId, String aspectClassName) {
        this.aspectId = aspectId;
        this.aspectClassName = aspectClassName;
    }


    /**
     * Retourne l'id de l'aspect.
     *
     * @return L'id de l'aspect
     */
    String getAspectId() {
        return aspectId;
    }


    /**
     * Positionne l'id de l'aspect.
     *
     * @param aspectId L'id de l'aspect à positionner
     */
    void setAspectId(String aspectId) {
        this.aspectId = aspectId;
    }


    /**
     * Retourne la Class de l'aspect.
     *
     * @return La Class de l'aspect
     *
     * @throws AspectConfigException Class de l'aspect est introuvable.
     */
    Class getAspectClass() throws AspectConfigException {
        if (aspectClassName != null && aspectClass == null) {
            loadClass();
        }

        return aspectClass;
    }


    /**
     * Positionne la Classe de l'aspect par le nom.
     *
     * @param aspectClassName La Class de l'aspect à positionner
     */
    public void setAspectClassName(String aspectClassName) {
        this.aspectClassName = aspectClassName;
    }


    /**
     * Positionne la Class de l'aspect.
     *
     * @param aspectClass La Class de l'aspect à positionner
     */
    void setAspectClass(Class aspectClass) {
        this.aspectClass = aspectClass;
    }


    /**
     * Ajoute un point de jointure à la liste des points de jointure de l'aspect.
     *
     * @param joinPoint Le point de jointure à ajouter
     */
    void addJoinPoint(JoinPoint joinPoint) {
        joinPointList.add(joinPoint);
    }


    /**
     * Positionne l'ensemble des points de jointure.
     *
     * @param joinPoints Un tableau de {@link JoinPoint}
     */
    void setJoinPointList(JoinPoint[] joinPoints) {
        for (JoinPoint joinPoint : joinPoints) {
            addJoinPoint(joinPoint);
        }
    }


    /**
     * Charge la configuration d'un aspect à partir d'un fichier XML.
     *
     * @param inputStream Le {@link InputStream} correspondant au fichier XML.
     *
     * @throws AspectConfigException si une erreur survient au chargement du fichier.
     */
    void load(InputStream inputStream) throws AspectConfigException {
        XmlParser xmlParser = new XmlParser();
        AspectContentHandler aspectContentHandler = new AspectContentHandler(this);
        try {
            xmlParser.parse(new InputSource(inputStream), aspectContentHandler);
        }
        catch (InternalLoadException e) {
            Exception cause = e.getCause();
            if (cause == null) {
                throw new AspectConfigException(e.getMessage(), e);
            }
            if (cause instanceof AspectConfigException) {
                throw (AspectConfigException)cause;
            }
            else {
                throw new AspectConfigException(cause.getMessage(), cause);
            }
        }
        catch (Exception e) {
            throw new AspectConfigException(e.getMessage(), e);
        }
    }


    /**
     * Détermine si cet AspectConfig contient un point de jointure correspondant aux paramètres.
     *
     * @param call     La méthode d'appel : {@link JoinPoint#CALL_BEFORE} ou {@link JoinPoint#CALL_AFTER}
     * @param point    Le point de jointure
     * @param argument L'argument du point de  jointure
     *
     * @return <code>true</code> si cet AspectConfig contient le point de jointure, sinon <code>false</code>.
     */
    boolean hasJoinPoint(int call, String point, String argument) {
        return (getJoinPoint(call, point, argument) != null);
    }


    JoinPoint getJoinPoint(int call, String point, String argument) {
        for (Object aJoinPointList : joinPointList) {
            JoinPoint joinPoint = (JoinPoint)aJoinPointList;
            if (joinPoint.getCall() == call
                && joinPoint.getPoint().equals(point)
                && isObjectEqual(joinPoint.getArgument(), argument)) {
                return joinPoint;
            }
        }
        return null;
    }


    /**
     * Renvoie l'id de l'aspect.
     *
     * @return L'id de l'aspect.
     */
    @Override
    public String toString() {
        return getAspectId();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AspectConfig that = (AspectConfig)o;

        if (aspectClassName != null ?
            !aspectClassName.equals(that.aspectClassName) :
            that.aspectClassName != null) {
            return false;
        }
        if (aspectId != null ? !aspectId.equals(that.aspectId) : that.aspectId != null) {
            return false;
        }
        if (joinPointList != null ? !joinPointList.equals(that.joinPointList) : that.joinPointList != null) {
            return false;
        }

        return true;
    }


    @Override
    public int hashCode() {
        int result = aspectClassName != null ? aspectClassName.hashCode() : 0;
        result = 31 * result + (joinPointList != null ? joinPointList.hashCode() : 0);
        result = 31 * result + (aspectId != null ? aspectId.hashCode() : 0);
        return result;
    }


    /**
     * Détermine si deux références sont équivalentes. Deux références nulles sont considérées comme équivalentes.
     *
     * @param o1 Premier objet
     * @param o2 Second objet
     *
     * @return <code>true</code> si les références sont équivalentes, sinon <code>false</code>.
     */
    private static boolean isObjectEqual(Object o1, Object o2) {
        if (o1 == null && o2 == null) {
            return true;
        }

        if (o1 == null || o2 == null) {
            return false;
        }

        return o1.equals(o2);
    }


    private void loadClass() throws AspectConfigException {
        try {
            aspectClass = Class.forName(aspectClassName);
        }
        catch (ClassNotFoundException e) {
            throw new AspectConfigException("La classe '" + aspectClassName
                                            + "' de l'aspect '" + getAspectId() + "' est introuvable", e);
        }
    }
}
