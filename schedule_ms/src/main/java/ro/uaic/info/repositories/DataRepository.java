package ro.uaic.info.repositories;


import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;

import java.io.Serializable;
import java.util.List;

public abstract class DataRepository<T, ID extends Serializable> {
    protected Class<T> entityClass;
    @Inject
    EntityManager em;

    protected DataRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    public void persist(T entity) {
            em.persist(entity);
    }
    public void merge(T entity) {
        em.merge(entity);
    }
    public T getById(ID id) {
        return em.find(entityClass, id);
    }
    public void delete(String id) {
        T entity = em.find(entityClass, id);
        if (entity != null) {
            em.remove(entity);
        } else {
            throw new EntityNotFoundException("Entity not found");
        }
    }


}

