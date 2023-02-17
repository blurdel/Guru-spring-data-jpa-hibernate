package com.blurdel.sdjpa.dao;

import java.util.List;

import com.blurdel.sdjpa.domain.Book;

public interface BookDao {

	List<Book> findAll();
	
	Book findByISBN(String isbn);
	
	Book getById(Long id);
	
	Book getByTitle(String title);
	
	Book saveNew(Book book);
	
	Book update(Book book);
	
	void delete(Long id);
	
}
