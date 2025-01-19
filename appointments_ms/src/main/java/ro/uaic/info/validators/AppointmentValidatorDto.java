package ro.uaic.info.validators;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.inject.Instance;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import ro.uaic.info.clients.UserClient;
import ro.uaic.info.dtos.AppointmentDto;

//@Initialized(ApplicationScoped.class)
public class AppointmentValidatorDto implements ConstraintValidator<ValidAppointmentDto, AppointmentDto> {
    private static final Logger LOG = Logger.getLogger(AppointmentValidatorDto.class);

    @Inject
    @RestClient
    Instance<UserClient> userClient;

    @Override
    public boolean isValid(AppointmentDto appointmentDto, ConstraintValidatorContext context) {
        if(!userClient.get().checkIfUserExists(appointmentDto.getSecretaryId())) {
            LOG.error("Secretary with id " + appointmentDto.getSecretaryId() + " does not exist.");
            return false;
        }
        return true;
    }
}
