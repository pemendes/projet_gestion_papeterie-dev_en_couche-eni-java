package com.cvmendes.papeterie.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.cvmendes.papeterie.bo.Article;
import com.cvmendes.papeterie.bo.Ramette;
import com.cvmendes.papeterie.bo.Stylo;
import com.cvmendes.papeterie.dal.dao.ArticleDao;
import com.cvmendes.papeterie.db.DB;
import com.cvmendes.papeterie.db.DbException;

public class ArticleDaoJdbcImpl implements ArticleDao {
	
	private Connection conn;
	private static final String TYPE_STYLO = "STYLO";
	private static final String TYPE_RAMETTE = "RAMETTE";

	private static final String sqlSelectById = "select idArticle, reference, marque, designation, prixUnitaire, qteStock, grammage, couleur, type "
			+ " from articles where idArticle = ?";
	private static final String sqlSelectAll = "select idArticle, reference, marque, designation, prixUnitaire, qteStock, grammage, couleur, type "
			+ " from articles";
	private static final String sqlUpdate = "update articles set reference=?,marque=?,designation=?,prixUnitaire=?,qteStock=?,grammage=?,couleur=? where idArticle=?";
	private static final String sqlInsert = "insert into articles(reference,marque,designation,prixUnitaire,qteStock,type,grammage,couleur) values(?,?,?,?,?,?,?,?)";
	private static final String sqlDelete = "delete from articles where idArticle=?";
	private static final String sqlSelectByMarque = "select reference, marque, designation, prixUnitaire, qteStock, grammage, couleur, type "
			+ " from articles where marque = ?";
	private static final String sqlSelectByMotCle = "select reference, marque, designation, prixUnitaire, qteStock, grammage, couleur, type "
			+ " from articles where marque like ? or designation like ?";
	
	public ArticleDaoJdbcImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Article art) {
		
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, art.getReference());
			st.setString(2, art.getMarque());
			st.setString(3, art.getDesignation());
			st.setFloat(4, art.getPrixUnitaire());
			st.setInt(5, art.getQteStock());
			if (art instanceof Ramette) {
				Ramette r = (Ramette) art;
				st.setString(6, TYPE_RAMETTE);
				st.setInt(7, r.getGrammage());
				st.setNull(8, Types.VARCHAR);
			}
			if (art instanceof Stylo) {
				Stylo s = (Stylo) art;
				st.setString(6, TYPE_STYLO);
				st.setNull(7, Types.INTEGER);
				st.setString(8, s.getCouleur());
			}

			int nbRows = st.executeUpdate();
			if (nbRows == 1) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					art.setIdArticle(rs.getInt(1));
				}
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Article art) {
		
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(sqlUpdate, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, art.getReference());
			st.setString(2, art.getMarque());
			st.setString(3, art.getDesignation());
			st.setFloat(4, art.getPrixUnitaire());
			st.setInt(5, art.getQteStock());
			st.setInt(8, art.getIdArticle());
			if (art instanceof Ramette) {
				Ramette r = (Ramette) art;
				st.setInt(6, r.getGrammage());
				st.setNull(7, Types.VARCHAR);
			}
			if (art instanceof Stylo) {
				Stylo s = (Stylo) art;
				st.setNull(6, Types.INTEGER);
				st.setString(7, s.getCouleur());
			}
			st.executeUpdate();
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void delete(Integer id) {
		
		PreparedStatement st = null;
		try {
			// l'intégrité référentielle s'occupe d'invalider la suppression
			// si l'article est référencé dans une ligne de commande
			st = conn.prepareStatement(sqlDelete);
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public Article selectById(Integer id) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		Article art = null;
		
		try {
			st = conn.prepareStatement(sqlSelectById);
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if (rs.next()) {
				if (TYPE_STYLO.equalsIgnoreCase(rs.getString("type").trim())) {

					art = new Stylo(rs.getInt("idArticle"), rs.getString("marque"), rs.getString("reference").trim(),
							rs.getString("designation"), rs.getFloat("prixUnitaire"), rs.getInt("qteStock"),
							rs.getString("couleur"));
				}
				if (TYPE_RAMETTE.equalsIgnoreCase(rs.getString("type").trim())) {
					art = new Ramette(rs.getInt("idArticle"), rs.getString("marque"), rs.getString("reference").trim(),
							rs.getString("designation"), rs.getFloat("prixUnitaire"), rs.getInt("qteStock"),
							rs.getInt("grammage"));
				}
				
			}
			
		} catch (Exception e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		return art;
	}

	@Override
	public List<Article> selectAll() {
		
		Statement st = null;
		ResultSet rs = null;
		List<Article> liste = new ArrayList<Article>();
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sqlSelectAll);
			Article art = null;

			while (rs.next()) {
				if (TYPE_STYLO.equalsIgnoreCase(rs.getString("type").trim())) {

					art = new Stylo(rs.getInt("idArticle"), rs.getString("marque"), rs.getString("reference").trim(),
							rs.getString("designation"), rs.getFloat("prixUnitaire"), rs.getInt("qteStock"),
							rs.getString("couleur"));
				}
				if (TYPE_RAMETTE.equalsIgnoreCase(rs.getString("type").trim())) {
					art = new Ramette(rs.getInt("idArticle"), rs.getString("marque"), rs.getString("reference").trim(),
							rs.getString("designation"), rs.getFloat("prixUnitaire"), rs.getInt("qteStock"),
							rs.getInt("grammage"));
				}
				liste.add(art);
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		return liste;
	}

	@Override
	public List<Article> selectByMarque(String marque) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Article> liste = new ArrayList<Article>();
		try {
			st = conn.prepareStatement(sqlSelectByMarque);
			st.setString(1, marque);
			rs = st.executeQuery();
			Article art = null;

			while (rs.next()) {
				if (TYPE_STYLO.equalsIgnoreCase(rs.getString("type").trim())) {

					art = new Stylo(rs.getInt("idArticle"), rs.getString("marque"), rs.getString("reference").trim(),
							rs.getString("designation"), rs.getFloat("prixUnitaire"), rs.getInt("qteStock"),
							rs.getString("couleur"));
				}
				if (TYPE_RAMETTE.equalsIgnoreCase(rs.getString("type").trim())) {
					art = new Ramette(rs.getInt("idArticle"), rs.getString("marque"), rs.getString("reference").trim(),
							rs.getString("designation"), rs.getFloat("prixUnitaire"), rs.getInt("qteStock"),
							rs.getInt("grammage"));
				}
				liste.add(art);
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		return liste;
	}

	@Override
	public List<Article> selectByMotCle(String motCle) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Article> liste = new ArrayList<Article>();
		try {
			st = conn.prepareStatement(sqlSelectByMotCle);
			st.setString(1, motCle);
			rs = st.executeQuery();
			Article art = null;

			while (rs.next()) {
				if (TYPE_STYLO.equalsIgnoreCase(rs.getString("type").trim())) {

					art = new Stylo(rs.getInt("idArticle"), rs.getString("marque"), rs.getString("reference").trim(),
							rs.getString("designation"), rs.getFloat("prixUnitaire"), rs.getInt("qteStock"),
							rs.getString("couleur"));
				}
				if (TYPE_RAMETTE.equalsIgnoreCase(rs.getString("type").trim())) {
					art = new Ramette(rs.getInt("idArticle"), rs.getString("marque"), rs.getString("reference").trim(),
							rs.getString("designation"), rs.getFloat("prixUnitaire"), rs.getInt("qteStock"),
							rs.getInt("grammage"));
				}
				liste.add(art);
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		return liste;
	}
}
