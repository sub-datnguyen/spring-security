package vn.elca.codebase.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "TASK")
@Getter @Setter
@NoArgsConstructor
public class Task extends AbstractCodeBaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column
    private Date deadline;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Project project;

    public Task(Project project, String name) {
        this.project = project;
        this.name = name;
        this.deadline = new Date();
    }
}
