/*
 * Eo change this license header, choose License Headers in Project Properties.
 * Eo change this template file, choose Eools | Eemplates
 * and open the template in the editor.
 */
package com.ub.easymoney.utils.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import org.jinq.jpa.JPAJinqStream;
import org.jinq.jpa.JinqJPAStreamProvider;

/**
 * Facade Data Access Object para entidades SQL
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com
 * @param <T> Entidad JPA a utilizar por el controlador C JPA respaldado de DaoSQLFacade
 * @param <K> Tipo de dato de la llave primaria de la entidad
 */
public abstract class DaoSQLFacade<T extends IEntity, K> {

    private final Class<T> claseEntity;
    private final Class<K> clasePK;
    private final EntityManagerFactory eMFactory;
    private final JinqJPAStreamProvider streamProvider;

    /**
     * al sobreescribir considerar la fabrica de EntityManager, que sea la que apunte a la base de datos adecuada, que la clase entidad sea correcta y la clase que represente la llave primaria tambien corresponda
     *
     * @param eMFactory fabrica de manejadores de entidad EntityManager que corresponda a la base de datos con la cual trabajar
     * @param claseEntity clase de la entidad con la cual operar
     * @param clasePk clase que represente la llave primaria de la entidad
     */
    public DaoSQLFacade(EntityManagerFactory eMFactory, Class<T> claseEntity, Class<K> clasePk) {
        this.eMFactory = eMFactory;
        this.claseEntity = claseEntity;
        this.clasePK = clasePk;

        streamProvider = new JinqJPAStreamProvider(eMFactory);
        streamProvider.registerAttributeConverterType(UUID.class);
    }

    public Class<T> getClaseEntity() {
        return claseEntity;
    }

    public Class<K> getClasePK() {
        return clasePK;
    }

    public EntityManagerFactory geteMFactory() {
        return eMFactory;
    }

    public JinqJPAStreamProvider getStreamProvider() {
        return streamProvider;
    }

    /**
     * obtiene una nueva instancia de un EntityManager de la fabrica proporsionada al construir el objeto
     *
     * @return EntityManager de la fabrica de este Data Access Object
     */
    public EntityManager getEMInstance() {
        return eMFactory.createEntityManager();
    }

    /**
     * construye un JPQL Query con el parametro obtenido
     *
     * @param jpql cadena con el JPQL para construir un query
     * @return query contruido con el JPQL
     */
    protected Query createQuery(String jpql) {
        return this.getEMInstance().createQuery(jpql);
    }

    /**
     * construye un JPQL Query con el parametro obtenido
     *
     * @param <X> tipado de retorno
     * @param jpql cadena con el JPQL para construir un query
     * @param clazz tipado de retorno de la instruccion JPQL
     * @return query contruido con el JPQL
     */
    protected <X> TypedQuery createQuery(String jpql, Class<X> clazz) {
        return this.getEMInstance().createQuery(jpql, clazz);
    }

    /**
     * construye un Stream de datos de tipo JPAJinq, esto para poder realizar consultas con funciones lambda
     *
     * @return strema de datos de la entidad con la cual operar
     */
    public JPAJinqStream<T> stream() {
        JPAJinqStream<T> stream = streamProvider.streamAll(eMFactory.createEntityManager(), claseEntity);
//        stream.setHint(
//                "queryLogger", (JPAQueryLogger) (String query, Map<Integer, Object> positionParameters, Map<String, Object> namedParameters) -> {
//                    System.out.println("queryLogr -> " + query);
//                });
        return stream;
    }

    /**
     * transforma una cadena serializada en pk (UUID, Long, Integer)
     *
     * @param s cadena representativa de la pk
     * @return K tipo de dato asignado a la llave primaria de la entidad
     */
    public K stringToPK(String s) {
        if (clasePK.getName().equals(Integer.class.getName())) {
            return (K) Integer.valueOf(s);
        } else {
            if (clasePK.getName().equals(Long.class.getName())) {
                return (K) Long.valueOf(s);
            } else {
                if (clasePK.getName().equals(UUID.class.getName())) {
                    return (K) UUID.fromString(s);
                }
            }
        }
        return (K) s;
    }

    //<editor-fold defaultstate="collapsed" desc="¡LEEME!">
    //Todos los metodos siguientes tiene con objetivo hacer y solo hacer lo que su nombre indica       
    //</editor-fold>
    public void persist(T entity) throws Exception {
        EntityManager em = this.getEMInstance();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<T> persistAll(List<T> entities) throws Exception {
        EntityManager em = this.getEMInstance();
        try {
            em.getTransaction().begin();
            entities.forEach((entity) -> {
                em.persist(entity);
            });
            em.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return entities;
    }

    public void delete(K id) throws ForeignKeyException, Exception {
        EntityManager em = this.getEMInstance();
        try {
            em.getTransaction().begin();
            em.remove(em.getReference(claseEntity, id));
            em.getTransaction().commit();
        } catch (Exception e) {
            if (e.getMessage().contains("violates foreign key constraint")) {
                throw new ForeignKeyException(this.foreignKeyMessage(e.getMessage(), "eliminar"));
            }
            throw e;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void deleteAll(List<K> ids) throws Exception {
        EntityManager em = this.getEMInstance();
        try {
            em.getTransaction().begin();
            for (Object id : ids) {
                em.remove(em.getReference(claseEntity, id));
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void update(T entity) throws Exception {
        EntityManager em = this.getEMInstance();
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public T findFirst() throws Exception {
        try {
            return DaoSQLFacade.this.findAll(false, 1, 0).get(0);
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public T findOne(K id) throws Exception {
        return getEMInstance().find(claseEntity, id);
    }

    public List<T> findAll(int max) throws Exception {
        return DaoSQLFacade.this.findAll(false, max, 0);
    }

    public List<T> findAll() throws Exception {
        return DaoSQLFacade.this.findAll(true, -1, -1);
    }

    public List<T> findAll(int maxResults, int firstResult) throws Exception {
        return DaoSQLFacade.this.findAll(false, maxResults, firstResult);
    }

    private List<T> findAll(boolean all, int maxResults, int firstResult) throws Exception {
        EntityManager em = getEMInstance();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(claseEntity));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } catch (Exception e) {
            throw e;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Set the page to query with the from and to query param
     *
     * @param q query to set tha page
     * @param from index to start the query
     * @param to index to stop que query
     */
    protected void paginateQuery(Query q, Integer from, Integer to) {
        if (from != null && to != null) {
            q.setMaxResults(to - from + 1);
            q.setFirstResult(from);
        } else {
            if (to != null) {
                q.setMaxResults(to);
            }
        }
    }

    /**
     * Creates de result set mapped as a generic list from the query string provided
     *
     * @param select contains the columns and filters
     * @param orderBy
     * @param from pagination index from
     * @param to pagination index to
     * @return result set mapped with properties names
     */
    protected List<Map<String, Object>> findAll(String select, String orderBy, Integer from, Integer to) {
        String entityName = this.claseEntity.getSimpleName();
        String queryToExcecute = "SELECT ";
        String queryFrom = " From " + entityName + " t";
        Set<String> querySelects = new LinkedHashSet<>();
        Set<String> queryWheres = new LinkedHashSet<>();
        Set<String> queryFroms = new LinkedHashSet<>();
        Set<String> queryOrder = new LinkedHashSet<>();
        String[] querySelections = select.split(",");
        for (String selection : querySelections) {
            String whereSelection = whereSelection(selection);
            if (whereSelection.isEmpty()) {
                querySelects.add("t." + selection);
            } else {
                if (whereSelection.equals("><")) { //between operator
                    String[] selectionParts = selection.split(whereSelection);
                    String[] values = selectionParts[1].split("\\|");
                    querySelects.add("t." + selectionParts[0]);
                    queryWheres.add("t." + selectionParts[0] + " BETWEEN " + values[0] + " AND " + values[1]);
                } else {
                    if (whereSelection.equals("%")) {
                        String[] selectionParts = selection.split(whereSelection);
                        querySelects.add("t." + selectionParts[0]);
                        queryWheres.add("t." + selectionParts[0] + " LIKE CONCAT(" + selectionParts[1] + ",'%')");
                    } else {
                        String[] selectionParts = selection.split(whereSelection);
                        querySelects.add("t." + selectionParts[0]);
                        queryWheres.add("t." + selectionParts[0] + " " + whereSelection + " " + selectionParts[1]);
                    }
                }
            }
        }

        queryToExcecute += String.join(",", querySelects);
        queryToExcecute += queryFrom + String.join("", queryFroms);
        if (!queryWheres.isEmpty()) {
            queryToExcecute += " WHERE " + String.join(" AND ", queryWheres);
        }

        if (orderBy != null && !orderBy.isEmpty()) {
            String[] orderParts = orderBy.split(",");
            for (String orderPart : orderParts) {
                String[] fieldAndWay = orderPart.split("\\|");
                String field = fieldAndWay[0];
                String way = "asc";
                if (fieldAndWay.length > 1) {
                    way = fieldAndWay[1];
                }
                queryOrder.add("t." + field + " " + way);
            }
        }

        if (!queryOrder.isEmpty()) {
            queryToExcecute += " ORDER BY " + String.join(" , ", queryOrder);
        }

        EntityManager em = this.getEMInstance();
        Query query = em.createQuery(queryToExcecute);
        paginateQuery(query, from, to);

        //mapping to the list of object array into maps with the properties requested
        List<Object[]> result = query.getResultList();
        List<Map<String, Object>> mappedResults = new ArrayList<>();

        int index = 0;
        for (int i = 0; i < result.size(); i++) {
            HashMap<String, Object> mappedResult = new HashMap<>(querySelects.size());
            for (String querySelect : querySelects) {
                String[] selectParts = querySelect.split("\\.");
                if (selectParts.length == 2) {
                    mappedResult.put(selectParts[1], result.get(i)[index]);
                } else {
                    mappedResult.put(selectParts[selectParts.length - 2] + "_" + selectParts[selectParts.length - 1], result.get(i)[index]);
                }
                index++;
            }
            index = 0;
            mappedResults.add(mappedResult);
        }
        em.close();
        return mappedResults;
    }

    /**
     * check if there is a where condition in a selection
     *
     * @param selection the field selection
     * @return the operator to use in the where clause like '=' or '!='
     */
    protected String whereSelection(String selection) {
        if (selection.contains("%")) {
            return "%";
        }
        if (selection.contains(">=")) {
            return ">=";
        }
        if (selection.contains("<=")) {
            return "<=";
        }
        if (selection.contains("><")) { //between, its important the order of the questions
            return "><";
        }
        if (selection.contains("!=")) {
            return "!=";
        }
        if (selection.contains("=")) {
            return "=";
        }
        if (selection.contains(">")) {
            return ">";
        }
        if (selection.contains("<")) {
            return "<";
        }
        return "";
    }



    
    /**
     * Cuenta el numero de registros de una entidad
     *
     * @return cantidad de registros que existen en la base de datos de la entidad
     * @throws Exception si existe un error de I/O
     */
    public long count() throws Exception {
        EntityManager em = this.getEMInstance();
        CriteriaBuilder qb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        cq.select(qb.count(cq.from(claseEntity)));
        return em.createQuery(cq).getSingleResult();
    }

    /**
     * Genera el mensaje personalizado por entidad de un problema de relacion de llave foranea
     *
     * @param s mensaje de exception con el cual trabajar
     * @param accionAMostrar action del usuario a mostrar
     * @return mensaje formado a partir de la entidad, para mostrar al usuario final
     */
    private String foreignKeyMessage(String s, String accionAMostrar) {
        Pattern p = Pattern.compile("\"(.+?)\"");
        Matcher m = p.matcher(s);
        List<String> incidencias = new ArrayList<>(5);
        while (m.find()) {
            incidencias.add(m.group(1));
        }
        return "No se pudo " + accionAMostrar + " " + incidencias.get(0) + " por que aun está siendo utilizado en algún " + incidencias.get(incidencias.size() - 1);
    }

}
