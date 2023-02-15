package com.blurdel.sdjpa.dao;

import org.springframework.stereotype.Component;

import com.blurdel.sdjpa.domain.Author;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

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
		TypedQuery<Author> query = getEntityManager().createQuery("select a from Author a " +
				"where a.firstName = :first_name and a.lastName = :last_name", Author.class);
		
		query.setParameter("first_name", firstName);
		query.setParameter("last_name", lastName);

		return query.getSingleResult();
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
