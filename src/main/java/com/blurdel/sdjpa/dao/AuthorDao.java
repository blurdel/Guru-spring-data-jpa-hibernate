package com.blurdel.sdjpa.dao;

import java.util.List;

import com.blurdel.sdjpa.domain.Author;

public interface AuthorDao {

	List<Author> findAll();
	
	List<Author> listAuthorByLastNameLike(String lastName);
	
	Author getById(Long id);
	
	Author getByName(String firstName, String lastName);
	
	Author saveNew(Author author);
	
	Author update(Author author);
	
	void delete(Long id);
	
}
