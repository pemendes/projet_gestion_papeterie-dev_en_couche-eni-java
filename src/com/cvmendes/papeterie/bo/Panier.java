package com.cvmendes.papeterie.bo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pedro 
 * Le panier stocke les articles s�lectionn�s par
 * l'utilisateur au cours de sa navigation. Le panier n'est pas sauvegard�.
 */
public class Panier {
	
	private float montant;
	private List<Ligne> lignesPanier = new ArrayList<>();
	
	public Panier() {
	}

	public float getMontant() {
		return montant;
	}

	public Ligne getLigne(int index) {
		return lignesPanier.get(index);
	}

	public List<Ligne> getLignesPanier() {
		return lignesPanier;
	}
	
	public void setMontant(float montant) {
		this.montant = montant;
	}
	
	/**
	 * Ajouter une ligne au panier. Le prix de la ligne est calcul� (Qte*prix)
	 *
	 * @param article
	 * @param qte
	 * 
	 */
	public void addLigne(int qte, Article article) {
		Ligne newLigne = new Ligne(qte, article);
		lignesPanier.add(newLigne);
		
	}
	
	/**
	 * Modifier la quantit� plac�e dans le panier La quantit� en stock augment
	 * ou diminue en fonction de cette nouvelle qt�
	 * 
	 * @param index
	 * @param newQte
	 * 
	 */
	public void updateLigne(int index, int newQte) {
		lignesPanier.get(index).setQte(newQte);
		
	}
	
	/**
	 * Supprimer la ligne du panier. La quantit� en stock augmente de la
	 * quantit� associ�e � la ligne
	 * 
	 * @param numero
	 */
	public void removeLigne(int index) {
		lignesPanier.remove(index);
		
	}

	@Override
	public String toString() {
		StringBuffer strbf =new StringBuffer();
		strbf.append("Panier : \n\n");
		for (Ligne ligne : lignesPanier) {
			if (ligne != null){
				strbf.append("ligne " + lignesPanier.indexOf(ligne) + " :\t");
				strbf.append(ligne.toString());
				strbf.append("\n");
			} else break;
		}
		strbf.append("\nValeur du panier : " + getMontant());
		strbf.append("\n\n");
		return strbf.toString();
	}
	
	

}
