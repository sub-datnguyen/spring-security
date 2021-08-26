package vn.elca.training.learnjwt.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author vlp
 */
@Getter @Setter
@AllArgsConstructor
public class TokenData {
    private String sessionId;
    private boolean valid;
    private Date expiryDate;
}
