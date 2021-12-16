package vn.elca.training.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author tcb
 */

@Getter
@Setter
@Entity
@Table(name = "ENGIN")
public class EnginEntity extends AbstractAuditEntity {
    private static final String ENGIN_ID_SEQUENCE = "ENGIN_ID_SEQ";
    @Id
    @Column(name = "ID", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ENGIN_ID_SEQUENCE)
    @SequenceGenerator(name = ENGIN_ID_SEQUENCE, sequenceName = ENGIN_ID_SEQUENCE, allocationSize = 1)
    private Long id;
    
    @Column(name = "ID_PHOTO", length = 250)
    private String lienPhoto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCALISATION_SECTEUR_ID")
    private SecteurEntity localisationSecteur;

    @Column(name = "NUMERO_IDENTIFICATION", length = 10, nullable = false)
    @NotNull
    private Long numeroIdentification;
    
    @Column(name = "DESIGNATION", length = 40)
    private String designation;
    
    @Column(name = "DESCRIPTION", length = 1000)
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORIE_EXPLOITATION_ER_ID")
    private CategorieExploitationErEntity categorieExploitationEr;
    
    @Column(name = "NUM_PLAQUE", length = 40)
    private String numPlaque;
    
    @Column(name = "TYPE_PERMIS", length = 100)
    private String typePermis;
    
    @Column(name = "STATUT", length = 100)
    private String statut;
    
    @Column(name = "SYNCHRO_DRIVE")
    private LocalDateTime synchroDrive;
}
