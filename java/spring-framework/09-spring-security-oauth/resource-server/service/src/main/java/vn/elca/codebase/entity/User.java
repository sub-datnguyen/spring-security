package vn.elca.codebase.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * @author hmt
 */
@Entity
@Table(name = "USER")
@Getter
@Setter
@NoArgsConstructor
public class User extends AbstractCodeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    /**
     * Password using bcrypt encoder
     */
    @Column
    private String password;

    @ManyToMany
    private Set<Role> roles = new HashSet<>();
}
