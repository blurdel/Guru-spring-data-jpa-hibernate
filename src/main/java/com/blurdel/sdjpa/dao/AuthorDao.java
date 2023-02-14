package com.blurdel.sdjpa.dao;

import com.blurdel.sdjpa.domain.Author;

public interface AuthorDao {

	Author getById(Long id);
	
	Author getByName(String firstName, String lastName);
	
	Author saveNew(Author author);
	
	Author update(Author author);
	
	void delete(Long id);
	
}