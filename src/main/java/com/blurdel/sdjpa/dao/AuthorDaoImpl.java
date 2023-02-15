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
		EntityManager em = getEntityManager();
		Author author = em.find(Author.class, id);
		em.close();
		return author;
	}

	@Override
	public Author getByName(String firstName, String lastName) {
		EntityManager em = getEntityManager();
		
		TypedQuery<Author> query = em.createQuery("select a from Author a " +
				"where a.firstName = :first_name and a.lastName = :last_name", Author.class);
		
		query.setParameter("first_name", firstName);
		query.setParameter("last_name", lastName);
		
		Author author = query.getSingleResult();
		em.close();
		return author;
	}

	@Override
	public Author saveNew(Author author) {
		EntityManager em = getEntityManager();
		
//		em.joinTransaction();
		em.getTransaction().begin();
		em.persist(author);
		em.flush(); // Force a write to database
		em.getTransaction().commit();
		em.close();
		
		return author;
	}

	@Override
	public Author update(Author author) {
		EntityManager em = getEntityManager();
		
		em.getTransaction().begin();
		em.merge(author);
		em.flush(); // Force a write to database
		em.clear(); // Clear first level cache
		
		Author updated = em.find(Author.class, author.getId());
		
		em.getTransaction().commit();
		em.close();
		return updated;
	}

	@Override
	public void delete(Long id) {
		EntityManager em = getEntityManager();
		
		em.getTransaction().begin();
		Author author = em.find(Author.class, id);
		em.remove(author);
		em.flush();
		em.getTransaction().commit();
		em.close();
	}

	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
	
}
