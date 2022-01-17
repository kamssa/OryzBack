package ci.gestion.entites.personne;

import javax.persistence.Entity;

@Entity
public class Image extends AbstractEntity{
	
	private static final long serialVersionUID = 1L;
	private String nomDoc;
	private Long infoDocId;
    private String path;
    
   
}
