package ro.uaic.info.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import ro.uaic.info.exceptions.DuplicateResourceException;
import ro.uaic.info.models.User;
import ro.uaic.info.repositories.UserRepository;

import java.util.UUID;

@ApplicationScoped
public class UserService {
    @Inject
    UserRepository userRepository;
    public void registerUser(User user) {
        if (userRepository.checkIfUserExists(user.getName())) {
            throw new DuplicateResourceException("Username " + user.getName() + " already exists.");
        }
        userRepository.persist(user);
    }

    public User getUserByName(String name) {
        return userRepository.getUserByName(name);
    }

    public boolean checkIfUserExists(UUID id) {
        return userRepository.getById(id) != null;
    }
//    public User findUserByEmail(String username, String password) {
//        return userRepository.find("email", email).firstResult();
//    }
}
