package com.cvmendes.papeterie.application;

import java.util.List;

import com.cvmendes.papeterie.bo.Article;
import com.cvmendes.papeterie.bo.Ramette;
import com.cvmendes.papeterie.bo.Stylo;
import com.cvmendes.papeterie.dal.dao.ArticleDao;
import com.cvmendes.papeterie.dal.dao.DaoFactory;
import com.cvmendes.papeterie.db.DB;
import com.cvmendes.papeterie.db.DbException;

public class AppliTestDal {

	public static void main(String[] args) {
		
		//Déclaration et instanciation de la DAO
		//ArticleDaoJdbcImpl articleDAO = new ArticleDaoJdbcImpl();
		ArticleDao articleDAO = DaoFactory.createArticleDAO();

		//Instanciation du jeu d'essai 
		Article a1 = new Stylo( "Bic", "BBOrange","Bic bille Orange", 1.2f, 20, "bleu");
		Article a2 = new Ramette(  "Clairef", "CRA4S", "Ramette A4 Sup", 9f, 20, 80);
		Article a3 = new Stylo( "Stypen", "PlumeS", "Stylo Plume Stypen", 5.5f, 20, "jaune");


		System.out.println("Ajout des articles... ");
		//TODO...
		try {
			articleDAO.insert(a1);
			System.out.println("Article ajouté  : " + a1.toString() );
			articleDAO.insert(a2);
			System.out.println("Article ajouté  : " + a2.toString() );
			articleDAO.insert(a3);
			System.out.println("Article ajouté  : " + a3.toString() );


			//Sélection de l'article par id
			Article a = articleDAO.selectById(a2.getIdArticle());
			System.out.println("\nSélection de l'article par id  : " + a.toString() );

			//Sélection de tous les articles
			List<Article> articles = articleDAO.selectAll();
			System.out.println("\nSélection de tous les articles  : "  );
			afficherArticles(articles);

			//Modification d'un article
			System.out.println("\nModification d'un article  : " );
			System.out.println("Article avant modification : "  + a1.toString());
			((Stylo) a1).setCouleur("noir");
			((Stylo) a1).setDesignation("Bic bille noir");
			((Stylo) a1).setReference("BBNoir");
			articleDAO.update(a1);
			System.out.println("Article après modification  : " + a1.toString() );
			
			
			//Suppression d'un article
			System.out.println("\nSuppression de l'article  : " + a1.toString());
			articleDAO.delete(a1.getIdArticle());
			articles = articleDAO.selectAll();
			System.out.println("Liste des articles après suppression : "  );
			afficherArticles(articles);
			System.out.println("---------------------------------------------------------------");

			
		} catch (DbException e) {
			throw new DbException(e.getMessage());
		}finally{
			DB.closeConnection();
		}

	}

	
	private static void afficherArticles(List<Article> articles){
		StringBuffer sb = new StringBuffer();
		for(Article art: articles){
			sb.append(art.toString());
			sb.append("\n");
		}
		System.out.println(sb.toString());

	}

}
