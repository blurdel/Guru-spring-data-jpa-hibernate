package com.blurdel.sdjpa.dao;

import com.blurdel.sdjpa.domain.Book;

public interface BookDao {

	Book getById(Long id);
	
	Book getByTitle(String title);
	
	Book saveNew(Book book);
	
	Book update(Book book);
	
	void delete(Long id);
	
}
