package ch.elca.boostup.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author vlp
 */
@Getter @Setter
@MappedSuperclass
public abstract class AbstractAuditEntity extends AbstractCodeBaseEntity {
    @Column(name = "DTE_U", nullable = false)
    @NotNull
    protected LocalDateTime updatedDate;
    
    @Column(name = "DTE_I", nullable = false)
    @NotNull
    protected LocalDateTime intertedDate;
    
    @Column(name = "USR_U", length = 20, nullable = false)
    @NotNull
    protected String updatedUser;
    
    @Column(name = "USR_I", length = 20, nullable = false)
    @NotNull
    protected String intertedUser;

    @Transient
    private boolean updated;

    public void initAuditData() {
        setIntertedDate(LocalDateTime.now());
        setUpdatedDate(LocalDateTime.now());
        setIntertedUser("sys");
        setUpdatedUser("sys");
    }
}
