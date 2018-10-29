/*
 * Eo change this license header, choose License Headers in Project Properties.
 * Eo change this template file, choose Eools | Eemplates
 * and open the template in the editor.
 */
package com.ub.easymoney.daos.commons;

import com.ub.easymoney.daos.exceptions.ForeignKeyException;
import com.ub.easymoney.entities.commons.commons.IEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
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
            return findAll(false, 1, 0).get(0);
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public T findOne(K id) throws Exception {
        return getEMInstance().find(claseEntity, id);
    }

    public List<T> findAll(int max) throws Exception {
        return findAll(false, max, 0);
    }

    public List<T> findAll() throws Exception {
        return findAll(true, -1, -1);
    }

    public List<T> findAll(int maxResults, int firstResult) throws Exception {
        return findAll(false, maxResults, firstResult);
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

    /**
     * ejecuta un select con los atributos en attributes y efectua una paginación desde from hasta to
     *
     * @param from indice inferior
     * @param to indice superior
     * @param attributes strings con los nombres de los atributos
     * @return lista de resutados de la consulta a db
     */
    public List select(Integer from, Integer to, String... attributes) {
        EntityManager em = this.getEMInstance();
        String selects = "";
        for (String attribute : attributes) {
            selects += "t." + attribute + ",";
        }
        selects = selects.substring(0, selects.length() - 1);
        Query q = em.createQuery("SELECT " + selects + " FROM " + claseEntity.getSimpleName() + " t");
        if (from != null) {
            q.setFirstResult(from);
        }
        if (to != null) {
            q.setMaxResults(to - from + 1);
        }
        return q.getResultList();
    }

    /**
     * ejecuta un select con los atributos en attributes y efectua una paginación desde from hasta to
     *
     * @param attributes strings con los nombres de los atributos
     * @return lista de resutados de la consulta a db
     */
    public List select(String... attributes) {
        EntityManager em = this.getEMInstance();
        String selects = "";
        for (String attribute : attributes) {
            selects += "t." + attribute + ",";
        }
        selects = selects.substring(0, selects.length() - 1);
        Query q = em.createQuery("SELECT " + selects + " FROM " + claseEntity.getSimpleName() + " t");
        return q.getResultList();
    }

    public List<T> findRange(final int rangoInicial, final int rangoFinal) {
        int resultados = rangoFinal - rangoInicial + 1;
        EntityManager em = this.getEMInstance();
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(claseEntity));
        Query q = em.createQuery(cq);
        q.setMaxResults(resultados);
        q.setFirstResult(rangoInicial);
        return q.getResultList();
    }

}
