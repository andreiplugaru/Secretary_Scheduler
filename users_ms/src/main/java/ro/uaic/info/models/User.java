package ro.uaic.info.models;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.security.jpa.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "users")
@UserDefinition
@Data
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "User.findByName", query = "SELECT u FROM User u WHERE u.name = :name")
})
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    @Username
    @Column(unique = true)
    private String name;
    @Roles
    private String role;
    @Password(value = PasswordType.MCF)
    private String password;

    public User(String name, String role, String password) {
        this.name = name;
        this.role = role;
        this.password = BcryptUtil.bcryptHash(password);
    }
    public enum Role{
        STUDENT, TEACHER, ADMIN, SECRETARY
    }
}
