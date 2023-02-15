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
		EntityManager em = getEntityManager();
		
//		em.joinTransaction();
		em.getTransaction().begin();
		em.persist(author);
		em.flush(); // Force a write to database
		em.getTransaction().commit();
		
		return author;
	}

	@Override
	public Author update(Author author) {
		EntityManager em = getEntityManager();
		
		em.joinTransaction();
		em.merge(author);
		em.flush(); // Force a write to database
		em.clear(); // Clear first level cache
		
		return em.find(Author.class, author.getId());
	}

	@Override
	public void delete(Long id) {
		EntityManager em = getEntityManager();
		
		em.getTransaction().begin();
		Author author = em.find(Author.class, id);
		em.remove(author);
		em.flush();
		em.getTransaction().commit();
	}

	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
	
}
