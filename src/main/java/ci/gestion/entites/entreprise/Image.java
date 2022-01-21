package ci.gestion.entites.entreprise;

import javax.persistence.Entity;

import ci.gestion.entites.personne.AbstractEntity;

@Entity
public class Image extends AbstractEntity{
	
	private static final long serialVersionUID = 1L;
	private String nomDoc;
	private Long infoDocId;
    private String path;
    
   
}
