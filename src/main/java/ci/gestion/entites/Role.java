package ci.gestion.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import org.hibernate.annotations.NaturalId;

@Entity
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Version
	private Long version;
	@Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
    private RoleName name;
	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Role(RoleName name) {
		super();
		this.name = name;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	public RoleName getName() {
		return name;
	}
	public void setName(RoleName name) {
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	@Override
	public String toString() {
		return "Role [id=" + id + ", version=" + version + ", name=" + name + "]";
	}
	
	
}
