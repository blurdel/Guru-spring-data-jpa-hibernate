package com.blurdel.sdjpa.dao;

import org.springframework.stereotype.Component;

import com.blurdel.sdjpa.domain.Author;

@Component
public class AuthorDaoImpl implements AuthorDao {

	@Override
	public Author getById(Long id) {
		return null;
	}

	@Override
	public Author getByName(String firstName, String lastName) {
		return null;
	}

	@Override
	public Author saveNew(Author author) {
		return null;
	}

	@Override
	public Author update(Author author) {
		return null;
	}

	@Override
	public void delete(Long id) {
		
	}

}
