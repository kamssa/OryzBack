package ci.gestion.entites.shared;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import ci.gestion.entites.admin.Admin;
import ci.gestion.entites.entreprise.Employe;
import ci.gestion.entites.entreprise.Manager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "T_Personnes")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TYPE_PERSONNE", discriminatorType = DiscriminatorType.STRING, length = 10)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @Type(name = "ADMIN", value = Admin.class), 
	         @Type(name = "MANAGER", value = Manager.class),
		    @Type(name = "EMPLOYE", value = Employe.class)
           })
@NoArgsConstructor @AllArgsConstructor
@Data
public class Personne extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotBlank
	@Size(max = 40)
	private String nom;

	@NotBlank
	@Size(max = 15)
	private String prenom;

	@NaturalId
	@NotBlank
	@Size(max = 40)
	@Email
	private String email;
	
    private String telephone;
	@NotBlank
	@Size(max = 100)
	private String password;
	private String fonction;
	private String nomComplet;

	@Embedded
	private Adresse adresse;

	@Column(name = "TYPE_PERSONNE", insertable = false, updatable = false)
	private String type;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@PrePersist
	@PreUpdate
	public void setNomComplet() {
		this.nomComplet = nom + " " + prenom;
	}

}
