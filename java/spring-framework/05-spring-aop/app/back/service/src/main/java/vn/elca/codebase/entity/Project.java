package vn.elca.codebase.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PROJECT")
@Getter @Setter
@NoArgsConstructor
public class Project extends AbstractCodeBaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column
    private Date finishingDate;
	@OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
	private Set<Task> tasks = new HashSet<>();

    public Project(String name, Date finishingDate) {
        this.name = name;
        this.finishingDate = finishingDate;
    }

    public Project(Long id, String name, Date finishingDate) {
        this.id = id;
        this.name = name;
        this.finishingDate = finishingDate;
    }
}