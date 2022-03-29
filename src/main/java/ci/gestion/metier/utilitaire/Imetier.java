package ci.gestion.metier.utilitaire;

import java.util.List;

import ci.gestion.metier.exception.InvalideOryzException;


public interface Imetier <T,U>{
	
	public T creer(T entity) throws InvalideOryzException;
	
 	public T modifier(T entity) throws InvalideOryzException;
	
	public List<T> findAll();
	 
	public T findById(U id);

	public boolean supprimer(U id);
	
	public boolean supprimer(List<T> entites);
	
	public boolean existe(U id);
	

}
