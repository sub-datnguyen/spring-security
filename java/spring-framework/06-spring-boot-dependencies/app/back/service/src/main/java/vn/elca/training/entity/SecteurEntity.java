package vn.elca.training.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author tcb
 */

@Getter
@Setter
@Entity
@Table(name = "SECTEUR")
public class SecteurEntity extends AbstractAuditEntity {
    private static final String SECTEUR_ID_SEQUENCE = "SECTEUR_ID_SEQUENCE";
    @Id
    @Column(name = "ID", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SECTEUR_ID_SEQUENCE)
    @SequenceGenerator(name = SECTEUR_ID_SEQUENCE, sequenceName = SECTEUR_ID_SEQUENCE, allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "CODE", length = 10, nullable = false)
    private Long code;
    
    @NotNull
    @Column(name = "CODE_ENTITE_SECTEUR", length = 10, nullable = false)
    private String codeEntiteSecteur;
    
    @NotNull
    @Column(name = "NOM", length = 100, nullable = false)
    private String nom;
}
