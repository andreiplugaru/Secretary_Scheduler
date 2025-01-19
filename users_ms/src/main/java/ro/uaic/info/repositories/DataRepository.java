package ro.uaic.info.repositories;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import ro.uaic.info.exceptions.DuplicateResourceException;

import java.io.Serializable;
import java.sql.SQLException;

public abstract class DataRepository<T, ID extends Serializable> {
    protected Class<T> entityClass;
    @Inject
    EntityManager em;

    protected DataRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    public void persist(T entity) {
        try {
            em.persist(entity);
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof PSQLException && ((PSQLException) cause).getSQLState().equals("23505")) {
                throw new DuplicateResourceException("User with the same name already exists.");
            }
        }
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
