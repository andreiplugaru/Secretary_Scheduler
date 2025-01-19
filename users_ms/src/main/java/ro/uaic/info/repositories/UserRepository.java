package ro.uaic.info.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import ro.uaic.info.models.User;

import java.util.UUID;
@ApplicationScoped
public class UserRepository extends DataRepository<User, UUID> {
    protected UserRepository() {
        super(User.class);
    }

    public User getUserByName(String name) {
        return em.createNamedQuery("User.findByName", User.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public boolean checkIfUserExists(String name) {
        return !em.createNamedQuery("User.findByName", User.class)
                    .setParameter("name", name).getResultList().isEmpty();
    }
}
