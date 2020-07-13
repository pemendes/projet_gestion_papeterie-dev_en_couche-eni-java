package com.cvmendes.papeterie.bo;

public class Ramette extends Article {
	
	private int grammage;

	public Ramette() {
	}

	public Ramette(Integer idArticle, String reference, String marque, String designation, float prixUnitaire,
			int qteStock, int grammage) {
		setIdArticle(idArticle);
		setReference(reference);
		setMarque(marque);
		setDesignation(designation);
		setPrixUnitaire(prixUnitaire);
		setQteStock(qteStock);
		setGrammage(grammage);
	}

	public Ramette(String reference, String marque, String designation, float prixUnitaire, int qteStock,
			int grammage) {
		super(reference, marque, designation, prixUnitaire, qteStock);
		this.grammage = grammage;
	}

	public int getGrammage() {
		return grammage;
	}

	public void setGrammage(int grammage) {
		this.grammage = grammage;
	}

	@Override
	public String toString() {
		
		StringBuffer strbf = new StringBuffer();
		strbf.append(super.toString());
		strbf.append("Ramette [grammage=");
		strbf.append(grammage);
		strbf.append("]");
		
		return strbf.toString();
	}

}
