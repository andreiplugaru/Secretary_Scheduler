package ro.uaic.info.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.
                PARAMETER
})
@Constraint(validatedBy = AppointmentValidatorDto.class)
public @interface ValidAppointmentDto {

    String message() default "Teacher Id or secretary Id is incorrect";

    Class<? extends Payload>[] payload() default {};

    Class<?>[] groups() default {};

}
