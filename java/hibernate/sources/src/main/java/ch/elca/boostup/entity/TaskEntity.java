package ch.elca.boostup.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TASK")
@Getter
@Setter
@NoArgsConstructor
public class TaskEntity extends AbstractAuditEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column
    private LocalDate deadline;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private ProjectEntity project;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private DeveloperEntity assignee;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TaskLogWorkEntity> logWorks = new HashSet<>(0);
    @Column(nullable = false)
    private String status;
}
