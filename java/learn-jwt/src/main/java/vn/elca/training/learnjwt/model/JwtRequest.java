package vn.elca.training.learnjwt.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author vlp
 */
@Getter @Setter
@AllArgsConstructor
public class JwtRequest implements Serializable {
    private String username;
    private String password;
}
