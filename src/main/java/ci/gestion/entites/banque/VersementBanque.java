package ci.gestion.entites.banque;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlType;

@Entity
@DiscriminatorValue("V")
@XmlType(name="V")
public class VersementBanque extends Operation{

}
