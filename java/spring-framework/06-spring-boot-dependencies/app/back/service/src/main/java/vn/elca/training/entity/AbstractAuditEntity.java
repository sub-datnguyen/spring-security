package vn.elca.training.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * @author vlp
 */
@Getter @Setter
@MappedSuperclass
public abstract class AbstractAuditEntity extends AbstractEntity {
    @Column(name = "DTE_U", nullable = false)
    protected LocalDateTime updatedDate;
    
    @Column(name = "DTE_I", nullable = false)
    protected LocalDateTime intertedDate;
    
    @Column(name = "USR_U", length = 20, nullable = false)
    protected String updatedUser;
    
    @Column(name = "USR_I", length = 20, nullable = false)
    protected String intertedUser;
    
}
