/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.aspect.util.sql;
import net.codjo.aspect.Aspect;
import net.codjo.aspect.AspectContext;
import net.codjo.aspect.AspectException;
import net.codjo.aspect.JoinPoint;
import net.codjo.aspect.util.TransactionalPoint;
import net.codjo.variable.TemplateInterpreter;
import net.codjo.variable.UnknownVariableException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;
/**
 * Classe permettant de simplifier l'implémentation d'aspects effectuant des requêtes SQL.
 *
 * @version $Revision: 1.1 $
 */
@SuppressWarnings({"UnusedDeclaration"})
public abstract class AbstractSqlAspect implements Aspect {
    private final Logger log = Logger.getLogger(getClass());


    /**
     * Cette méthode doit être surchargée pour retourner l'objet décrivant le comportement SQL de l'aspect.
     *
     * @return l'objet comportement.
     */
    protected abstract SqlAspectBehaviour getSqlBehaviour();


    public final void setUp(AspectContext context, JoinPoint joinPoint)
          throws AspectException {
        try {
            runSql(context, getSqlBehaviour().getSqlForSetUp());
            doSetUp(context, joinPoint, getConnection(context));
        }
        catch (SQLException ex) {
            throw new AspectException("Erreur SQL : " + ex.getMessage(), ex);
        }
    }


    public final void run(AspectContext context) throws AspectException {
        try {
            doPreRun(context, AbstractSqlAspect.getConnection(context));
            runSql(context, getSqlBehaviour().getSqlCallForRun());
            doPostRun(context, AbstractSqlAspect.getConnection(context));
        }
        catch (SQLException ex) {
            throw new AspectException("Erreur SQL : " + ex.getMessage(), ex);
        }
    }


    public final void cleanUp(AspectContext context) {
        try {
            doCleanUp(context, getConnection(context));
            runSql(context, getSqlBehaviour().getSqlForCleanUp());
        }
        catch (SQLException e) {
            log.warn("Erreur SQL", e);
        }
        catch (AspectException e) {
            log.warn("Erreur lors de la destruction des tables temporaires", e);
        }
    }


    /**
     * Cette méthode doit être surchargée pour fournir l'implémentation de {@link
     * net.codjo.aspect.Aspect#setUp(net.codjo.aspect.AspectContext,net.codjo.aspect.JoinPoint)}.
     *
     * @param context    contexte d'appel de l'aspect
     * @param joinPoint  point de jointure ayant déclenché l'aspect
     * @param connection connexion JDBC à utiliser
     *
     * @throws AspectException si une erreur non-SQL survient
     * @throws SQLException    si une erreur SQL survient
     */
    protected void doSetUp(AspectContext context, JoinPoint joinPoint,
                           Connection connection) throws AspectException, SQLException {
    }


    /**
     * Cette méthode doit être surchargée pour exécuter du code avant l'exécution du comportement spécifié
     * dans le {@link SqlAspectBehaviour}
     *
     * @param context    contexte d'appel de l'aspect
     * @param connection connexion JDBC à utiliser
     *
     * @throws AspectException si une erreur non-SQL survient
     * @throws SQLException    si une erreur SQL survient
     */
    protected void doPreRun(AspectContext context, Connection connection)
          throws AspectException, SQLException {
    }


    /**
     * Cette méthode doit être surchargée pour exécuter du code après l'exécution du comportement spécifié
     * dans le {@link SqlAspectBehaviour}
     *
     * @param context    contexte d'appel de l'aspect
     * @param connection connexion JDBC à utiliser
     *
     * @throws AspectException si une erreur non-SQL survient
     * @throws SQLException    si une erreur SQL survient
     */
    protected void doPostRun(AspectContext context, Connection connection)
          throws AspectException, SQLException {
    }


    /**
     * Cette méthode doit être surchargée pour fournir l'implémentation de {@link
     * net.codjo.aspect.Aspect#cleanUp(net.codjo.aspect.AspectContext)}.
     *
     * @param context    contexte d'appel de l'aspect
     * @param connection connexion JDBC à utiliser
     *
     * @throws AspectException si une erreur non-SQL survient
     * @throws SQLException    si une erreur SQL survient
     */
    protected void doCleanUp(AspectContext context, Connection connection)
          throws AspectException, SQLException {
    }


    /**
     * Exécute plusieurs requêtes SQL séquentiellement. Si une erreur survient lors d'un appel, les suivants
     * ne sont pas exécutés.
     *
     * @param context    contexte d'appel de l'aspect
     * @param statements tableau de requêtes SQL à exécuter
     *
     * @throws SQLException    si une erreur SQL survient
     * @throws AspectException si une erreur non-SQL survient
     */
    protected void runSql(AspectContext context, String[] statements)
          throws SQLException, AspectException {
        if (statements == null) {
            return;
        }
        TemplateInterpreter templateInterpreter = new TemplateInterpreter();
        templateInterpreter.addAsVariable(context.asMap());

        Connection connection = getConnection(context);
        Statement statement = null;
        CallableStatement callableStatement = null;
        for (String statement1 : statements) {
            String sql;
            try {
                sql = templateInterpreter.evaluate(statement1);
            }
            catch (UnknownVariableException e) {
                throw new AspectException(
                      "Variable inconnue dans le contexte sur la requête :"
                      + statement1,
                      e);
            }
            try {
                if (sql.trim().startsWith("{")) {
                    callableStatement = connection.prepareCall(sql);
                    callableStatement.executeUpdate();
                }
                else {
                    statement = connection.createStatement();
                    statement.executeUpdate(sql);
                }
                if (log.isDebugEnabled()) {
                    log.debug("Exec : " + sql);
                }
            }
            finally {
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
                if (callableStatement != null) {
                    callableStatement.close();
                    callableStatement = null;
                }
            }
        }
    }


    public static Connection getConnection(AspectContext context) {
        return (Connection)context.get(TransactionalPoint.CONNECTION);
    }
}
