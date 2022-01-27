package ci.gestion.entites.entreprise;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import ci.gestion.entites.shared.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
public class Image extends AbstractEntity{
	
	
	private static final long serialVersionUID = 1L;
	private String nomDoc;
	private Long infoDocId;
    private String path;
    
    
	
}
