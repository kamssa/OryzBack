package ci.gestion.entites.personne;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor @AllArgsConstructor
public class Role extends AbstractEntity{
    
	private static final long serialVersionUID = 1L;
	@Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
    private RoleName name;

   
}