package com.cvmendes.papeterie.dal.dao;

import java.util.List;

import com.cvmendes.papeterie.bo.Article;

public interface ArticleDao {

	void insert(Article article);
	void update(Article article);
	void delete(Integer id);
	Article selectById(Integer id);
	List<Article> selectAll();
	List<Article> selectByMarque(String marque);
	List<Article> selectByMotCle(String motCle);
}
