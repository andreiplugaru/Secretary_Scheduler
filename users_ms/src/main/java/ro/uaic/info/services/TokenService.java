package ro.uaic.info.services;

import jakarta.enterprise.context.RequestScoped;
import org.eclipse.microprofile.jwt.Claims;
import org.jose4j.jwt.JwtClaims;
import ro.uaic.info.models.User;
import ro.uaic.info.utils.TokenUtils;

import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Logger;

@RequestScoped
public class TokenService {

    public final static Logger LOGGER = Logger.getLogger(TokenService.class.getSimpleName());

//    public String generateUserToken(String email, String username) {
//        return generateToken(email, username, User.Role.);
//    }

//    public String generateServiceToken(String serviceId, String serviceName) {
//        return generateToken(serviceId,serviceName,Roles.SERVICE);
//    }

    public String generateToken(String name, String userId, String... roles) {
        try {
            JwtClaims jwtClaims = new JwtClaims();
            jwtClaims.setIssuer("DonauTech"); // change to your company
            jwtClaims.setJwtId(UUID.randomUUID().toString());
            jwtClaims.setSubject(name);
            jwtClaims.setClaim(Claims.preferred_username.name(), userId); //add more
            jwtClaims.setClaim(Claims.groups.name(), Arrays.asList(roles));
            jwtClaims.setAudience("using-jwt");
            jwtClaims.setExpirationTimeMinutesInTheFuture(60); // TODO specify how long do you need


            String token = TokenUtils.generateTokenString(jwtClaims);
            LOGGER.info("TOKEN generated: " + token);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
