package ci.gestion.entites.personne;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.persistence.JoinColumn;
import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import ci.gestion.entites.entreprise.Employe;
import ci.gestion.entites.entreprise.Manager;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity 
@Table(name = "Personne", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "email"
        })
})
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TYPE_PERSONNE", discriminatorType = DiscriminatorType.STRING, length = 10)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
		{ @Type(name = "ADMIN", value = Admin.class), 
		@Type(name = "EMPLOYE", value = Employe.class),
		@Type(name = "MANAGER", value = Manager.class)
		})
@Data
@NoArgsConstructor @AllArgsConstructor
public class Personne extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	@NaturalId
    @NotBlank
    @Size(max = 40)
    @Email
    private String email;
	private String nom;
	private String prenom;
	private String nomComplet;
	 private String telephone;
    @NotBlank
    @Size(max = 100)
    private String password;
    private String codePays;
    private String fonction;
    @Column(name = "TYPE_PERSONNE", insertable = false, updatable = false)
	private String type;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "personne_roles",
            joinColumns = @JoinColumn(name = "personne_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @PrePersist
    @PreUpdate
	public void setNomComplet() {
		this.nomComplet = nom + " " + prenom;
	}
}
