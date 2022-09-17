package ci.gestion.entites.banque;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlType;

@Entity
@DiscriminatorValue("R")
@XmlType(name="R")
public class RetraitBanque extends Operation {

}
