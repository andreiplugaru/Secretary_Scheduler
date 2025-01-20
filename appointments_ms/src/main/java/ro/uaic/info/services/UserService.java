package ro.uaic.info.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import ro.uaic.info.clients.UserClient;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class UserService {
    @Inject
    @RestClient
    UserClient userClient;

    Map<UUID, String> users = new HashMap<>();
    public String getUserNameById(UUID userId) {
        if (users.containsKey(userId)) {
            return users.get(userId);
        }
        String userName = userClient.getUserNameById(userId);
        users.put(userId, userName);
        return userName;
    }
}
