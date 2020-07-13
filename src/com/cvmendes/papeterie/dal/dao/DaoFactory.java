package com.cvmendes.papeterie.dal.dao;

import com.cvmendes.papeterie.dal.jdbc.ArticleDaoJdbcImpl;
import com.cvmendes.papeterie.db.DB;

public class DaoFactory {

	public static ArticleDao createArticleDAO() {
		
		return (ArticleDao) new ArticleDaoJdbcImpl(DB.getConnection());
	}
}
