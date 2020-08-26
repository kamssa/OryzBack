package ci.gestion.entites;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

import ci.gestion.metier.model.DateAudit;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "email"
        })
})
public class User extends DateAudit{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Version
	private Long version;
	@NotBlank
    @Size(max = 40)
    private String name;
	
    @NaturalId
    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(max = 100)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    
    

	public User() {
		super();
		
	}

	public User(@NotBlank @Size(max = 40) String name, @NotBlank @Size(max = 40) @Email String email,
			@NotBlank @Size(max = 100) String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public User(@NotBlank @Size(max = 40) @Email String email, @NotBlank @Size(max = 100) String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public User(@NotBlank @Size(max = 40) @Email String email, @NotBlank @Size(max = 100) String password,
			Set<Role> roles) {
		super();
		this.email = email;
		this.password = password;
		this.roles = roles;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", version=" + version + ", email=" + email + ", password=" + password + ", roles="
				+ roles + "]";
	}
    
    
	
}
