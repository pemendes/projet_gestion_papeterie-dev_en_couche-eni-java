package com.cvmendes.papeterie.bo;

public class Stylo extends Article {
	
	private String couleur;

	public Stylo() {
	}

	public Stylo(Integer idArticle, String reference, String marque, String designation, float prixUnitaire,
			int qteStock, String couleur) {
		setIdArticle(idArticle);
		setMarque(marque);
		setReference(reference);
		setDesignation(designation);
		setPrixUnitaire(prixUnitaire);
		setQteStock(qteStock);
		setCouleur(couleur);
	}

	public Stylo(String reference, String marque, String designation, float prixUnitaire, int qteStock,
			String couleur) {
		super(reference, marque, designation, prixUnitaire, qteStock);
		this.couleur = couleur;
	}

	public String getCouleur() {
		return couleur;
	}

	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}

	@Override
	public String toString() {
		StringBuffer strbf = new StringBuffer();
		strbf.append(super.toString());
		strbf.append("Stylo [couleur=");
		strbf.append(couleur);
		strbf.append("]");
		
		return strbf.toString();
	}
	
	
}
