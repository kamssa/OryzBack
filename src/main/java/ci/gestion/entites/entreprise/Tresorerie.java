package ci.gestion.entites.entreprise;

import java.time.LocalDate;

import javax.persistence.Entity;

import ci.gestion.entites.banque.Banque;
import ci.gestion.entites.caisse.Caisse;
import ci.gestion.entites.client.Client;
import ci.gestion.entites.facture.Facture;
import ci.gestion.entites.fournisseur.Fournisseur;
import ci.gestion.entites.salaire.Salaire;
import ci.gestion.entites.shared.AbstractEntity;
import ci.gestion.entites.vehicule.Carburant;
import ci.gestion.entites.vehicule.StationEssence;
import ci.gestion.entites.vehicule.Vehicule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
public class Tresorerie extends AbstractEntity{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private Banque banque;
    private Caisse caisse;
    private Client client;
    private Fournisseur fournisseur;
    private Salaire salaire;
    private Facture facture;
}
