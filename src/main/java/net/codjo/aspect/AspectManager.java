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
import org.apache.log4j.Logger;
import org.xml.sax.InputSource;
/**
 * Classe qui connait tous les aspects relatifs à tous les traitements.
 *
 * @version $Revision: 1.18 $
 */
public class AspectManager {
    private static final Logger LOG = Logger.getLogger(AspectManager.class);
    private List<AspectConfig> aspectConfigList = new ArrayList<AspectConfig>();


    /**
     * Charge tous les aspects configurés dans un fichier XML contenant les ressources à utiliser. Les nouveaux aspects sont ajoutés à la liste des aspects déjà existants.
     *
     * @param inputStream Le {@link InputStream} correspondant au fichier XML.
     *
     * @throws AspectConfigException si une erreur survient au chargement du fichier.
     */
    public void load(InputStream inputStream) throws AspectConfigException {
        if (inputStream == null) {
            return;
        }
        XmlParser xmlParser = new XmlParser();
        AspectsContentHandler aspectsContentHandler = new AspectsContentHandler();
        try {
            xmlParser.parse(new InputSource(inputStream), aspectsContentHandler);
        }
        catch (InternalLoadException e) {
            Exception cause = e.getCause();
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

        aspectConfigList.addAll(aspectsContentHandler.getAspectConfigList());
    }


    /**
     * load le fichier /META-INF/Aspects.xml
     *
     * @throws AspectConfigException si une erreur survient au chargement du fichier.
     */
    public void load() throws AspectConfigException {
        InputStream stream =
              AspectManager.class.getResourceAsStream("/META-INF/Aspects.xml");
        if (stream == null) {
            LOG.debug("Le fichier '/META-INF/Aspects.xml' est introuvable !");
        }
        load(stream);
    }


    /**
     * Ajoute un aspect à la liste des aspects déjà existants.
     *
     * @param aspectId Identifiant de l'aspect.
     * @param points   Tableau des points de jointure de l'aspect.
     * @param clazz    Classe de l'aspect.
     */
    public void addAspect(String aspectId, JoinPoint[] points, Class clazz) {
        AspectConfig aspectConfig = new AspectConfig();
        aspectConfig.setAspectId(aspectId);
        aspectConfig.setAspectClass(clazz);
        aspectConfig.setJoinPointList(points);
        aspectConfigList.add(aspectConfig);
    }


    /**
     * Renvoie l'aspect correspondant à un point de jointure, et devant être exécuté avant lui. Si plusieurs aspects existent, ils sont aggrégés pour se comporter comme un seul et même aspect.
     *
     * @param point    Type de point de jointure.
     * @param argument Identifiant du point de jointure.
     *
     * @return L'aspect demandé
     */
    Aspect getBeforeAspect(String point, String argument) {
        return getAspect(JoinPoint.CALL_BEFORE, point, argument, AspectFilter.ALL, null);
    }


    /**
     * Renvoie l'aspect correspondant à un point de jointure, et devant être exécuté après lui. Si plusieurs aspects existent, ils sont aggrégés pour se comporter comme un seul et même aspect.
     *
     * @param point    Type de point de jointure.
     * @param argument Identifiant du point de jointure.
     *
     * @return L'aspect demandé
     */
    Aspect getAfterAspect(String point, String argument) {
        return getAspect(JoinPoint.CALL_AFTER, point, argument, AspectFilter.ALL, null);
    }


    /**
     * Renvoie l'aspect correspondant à un point de jointure, et devant être exécuté en cas d'erreur. Si plusieurs aspects existent, ils sont aggrégés pour se comporter comme un seul et même aspect.
     *
     * @param point    Type de point de jointure.
     * @param argument Identifiant du point de jointure.
     *
     * @return L'aspect demandé
     */
    Aspect getErrorAspect(String point, String argument) {
        return getAspect(JoinPoint.CALL_ERROR, point, argument, AspectFilter.ALL, null);
    }


    /**
     * Implémentation des méthodes {@link #getBeforeAspect} et  {@link #getAfterAspect}
     *
     * @param call        La méthode d'appel : {@link net.codjo.aspect.JoinPoint#CALL_BEFORE} ou {@link net.codjo.aspect.JoinPoint#CALL_AFTER}
     * @param point       Type de point de jointure.
     * @param argument    Identifiant du point de jointure.
     * @param circularRef Liste de String contenant les noms d'aspects faisant partie de la chaîne des aspects sur aspect.
     *
     * @return L'aspect demandé
     *
     * @throws AspectInstanciationException si l'instanciation de l'aspect a échoué.
     */
    private Aspect getAspect(int call,
                             String point,
                             String argument,
                             AspectFilter aspectFilter,
                             List<String> circularRef) {
        List<AspectOnAspectEncapsulator> aspectList = new ArrayList<AspectOnAspectEncapsulator>();

        for (AspectConfig config : aspectConfigList) {
            JoinPoint joinPoint = config.getJoinPoint(call, point, argument);
            if ((joinPoint != null) && aspectFilter.accept(joinPoint, config.getAspectId())) {
                try {
                    Aspect aspect = (Aspect)config.getAspectClass().newInstance();
                    aspectList.add(new AspectOnAspectEncapsulator(aspect, this, aspectFilter, circularRef));
                }
                catch (Exception e) {
                    throw new AspectInstanciationException(e.getMessage(), e);
                }
            }
        }

        return new MultipleAspect(aspectList);
    }


    private Aspect getAspect(String aspectId, AspectFilter filter) {
        for (Object anAspectConfigList : aspectConfigList) {
            AspectConfig config = (AspectConfig)anAspectConfigList;
            if (aspectId.equals(config.getAspectId())) {
                try {
                    Aspect aspect = (Aspect)config.getAspectClass().newInstance();
                    return new AspectOnAspectEncapsulator(aspect, this, filter, null);
                }
                catch (Exception e) {
                    throw new AspectInstanciationException(e.getMessage(), e);
                }
            }
        }
        return null;
    }


    boolean isExistsAspect(String point, String argument) {
        for (Object anAspectConfigList : aspectConfigList) {
            AspectConfig config = (AspectConfig)anAspectConfigList;
            if (config.hasJoinPoint(JoinPoint.CALL_AFTER, point, argument)
                || config.hasJoinPoint(JoinPoint.CALL_BEFORE, point, argument)
                || config.hasJoinPoint(JoinPoint.CALL_ERROR, point, argument)) {
                return true;
            }
        }
        return false;
    }


    String getAspectId(Class clazz) throws AspectConfigException {
        for (Object anAspectConfigList : aspectConfigList) {
            AspectConfig config = (AspectConfig)anAspectConfigList;

            if (clazz == config.getAspectClass()) {
                return config.getAspectId();
            }
        }
        return null;
    }


    /**
     * Utilisé seulement par les tests
     *
     * @return la liste des AspectConfig
     */
    List<AspectConfig> getAspectConfigList() {
        return aspectConfigList;
    }


    AspectHelper createHelper(String point, String argument,
                              AspectFilter aspectFilter, List<String> circularRef) {
        Aspect before = getAspect(JoinPoint.CALL_BEFORE, point, argument, aspectFilter, circularRef);
        Aspect after = getAspect(JoinPoint.CALL_AFTER, point, argument, aspectFilter, circularRef);
        Aspect error = getAspect(JoinPoint.CALL_ERROR, point, argument, aspectFilter, circularRef);
        return new MonoAspectHelper(point, argument, before, after, error);
    }


    public AspectHelper createHelper(String point, String argument, AspectFilter aspectFilter)
          throws AspectException {
        return createHelper(point, argument, aspectFilter, null);
    }


    public AspectHelper createHelper(String point, String[] argumentList, AspectFilter aspectFilter)
          throws AspectException {
        MultiAspectHelper multi = new MultiAspectHelper();
        for (String arg : argumentList) {
            multi.add(createHelper(point, arg, aspectFilter));
        }
        return multi;
    }


    public AspectHelper createHelper(final JoinPoint joinPoint, String aspectId, AspectFilter filter) {
        final Aspect aspect = getAspect(aspectId, filter);
        if (aspect == null) {
            throw new IllegalStateException("Pas d'aspect '"
                                            + aspectId
                                            + "' au point de jointure '"
                                            + joinPoint.toString()
                                            + "'");
        }
        return new AspectHelper() {
            public void setUp(AspectContext context) throws AspectException {
                aspect.setUp(context, joinPoint);
            }


            public void runBefore(AspectContext context) throws AspectException {
                // do nothing, it is not allowed...
            }


            public void runAfter(AspectContext context) throws AspectException {
                aspect.run(context);
            }


            public void runError(AspectContext context) throws AspectException {
                throw new IllegalStateException("");
            }


            public void cleanUp(AspectContext context) throws AspectException {
                aspect.cleanUp(context);
            }
        };
    }
}
