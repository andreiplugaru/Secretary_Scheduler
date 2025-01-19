package ro.uaic.info.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import ro.uaic.info.models.UserSchedule;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ScheduleRepository extends DataRepository<UserSchedule, UUID> {
    protected ScheduleRepository() {
        super(UserSchedule.class);
    }

    public UserSchedule getUserScheduleByUserId(UUID userId) {
        return em.createNamedQuery("UserSchedule.findByUserId", UserSchedule.class)
                .setParameter("userId", userId)
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    public List<UserSchedule> findAll() {
        return em.createNamedQuery("UserSchedule.findAll", UserSchedule.class).getResultList();
    }
}
