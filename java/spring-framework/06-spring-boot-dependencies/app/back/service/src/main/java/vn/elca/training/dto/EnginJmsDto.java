package vn.elca.training.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EnginJmsDto {
    protected String lienPhoto;
    protected int numeroIdentification;
    protected String designation;
    protected String description;
    protected String numPlaque;
    protected String typePermis;
    protected String statut;
    private String localisationSecteurCodeEntSect;
    private String categorieExploitationErCode;
    private String entiteCode;
}
