package ci.gestion.metier.model;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
	   @NotBlank
	    private String emailOrTelephone;

	    @NotBlank
	    private String password;

	   

	    public String getEmailOrTelephone() {
			return emailOrTelephone;
		}

		public void setEmailOrTelephone(String emailOrTelephone) {
			this.emailOrTelephone = emailOrTelephone;
		}

		public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }
}
