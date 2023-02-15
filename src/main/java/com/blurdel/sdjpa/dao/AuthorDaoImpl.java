package com.blurdel.sdjpa.dao;

import org.springframework.stereotype.Component;

import com.blurdel.sdjpa.domain.Author;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

@Component
public class AuthorDaoImpl implements AuthorDao {

	private final EntityManagerFactory emf;
	
		
	public AuthorDaoImpl(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public Author getById(Long id) {
		return getEntityManager().find(Author.class, id);
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

	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
	
}
