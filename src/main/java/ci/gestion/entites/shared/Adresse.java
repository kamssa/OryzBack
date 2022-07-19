package ci.gestion.entites.shared;

import javax.persistence.Embeddable;

@Embeddable
public class Adresse {
	private String boitePostal;
   
	private String pays;
	private String ville;
	private String lienFacebook;
	private String lienLinkedIn;
	private String lienTwitter;
    private String lientInstagram;
    private String siteWeb;
	public String getBoitePostal() {
		return boitePostal;
	}
	
	public Adresse() {
		super();
	}

public Adresse(String boitePostal, String pays, String ville, String siteWeb) {
		super();
		this.boitePostal = boitePostal;
		this.pays = pays;
		this.ville = ville;
		this.siteWeb = siteWeb;
		
	}

	public void setBoitePostal(String boitePostal) {
		this.boitePostal = boitePostal;
	}
	
	public String getPays() {
		return pays;
	}
	public void setPays(String pays) {
		this.pays = pays;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	
	public String getSiteWeb() {
		return siteWeb;
	}
	public void setSiteWeb(String siteWeb) {
		this.siteWeb = siteWeb;
	}

	public String getLienFacebook() {
		return lienFacebook;
	}

	public void setLienFacebook(String lienFacebook) {
		this.lienFacebook = lienFacebook;
	}

	public String getLienLinkedIn() {
		return lienLinkedIn;
	}

	public void setLienLinkedIn(String lienLinkedIn) {
		this.lienLinkedIn = lienLinkedIn;
	}

	public String getLienTwitter() {
		return lienTwitter;
	}

	public void setLienTwitter(String lienTwitter) {
		this.lienTwitter = lienTwitter;
	}

	public String getLientInstagram() {
		return lientInstagram;
	}

	public void setLientInstagram(String lientInstagram) {
		this.lientInstagram = lientInstagram;
	}
	
	
}
