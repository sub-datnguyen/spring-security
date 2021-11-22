package ch.elca.boostup.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TASK_LOGWORK")
@Getter
@Setter
@NoArgsConstructor
public class TaskLogWorkEntity extends AbstractAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private int spendTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private TaskEntity task;
    @ManyToOne
    @JoinColumn
    private DeveloperEntity author;
}
