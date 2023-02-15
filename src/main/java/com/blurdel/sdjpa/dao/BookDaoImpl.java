package com.blurdel.sdjpa.dao;

import com.blurdel.sdjpa.domain.Book;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

public class BookDaoImpl implements BookDao {

	private final EntityManagerFactory emf;
	
	
	public BookDaoImpl(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public Book getById(Long id) {
		EntityManager em = getEntityManager();
		Book book = em.find(Book.class, id);
		em.close();
		return book;
	}

	@Override
	public Book getByTitle(String title) {
		EntityManager em = getEntityManager();
		
		TypedQuery<Book> query = em.createQuery("select a from Book a " +
				"where a.title = :title", Book.class);
		
		query.setParameter("title", title);
		
		Book book =  query.getSingleResult();
		em.close();
		return book;
	}

	@Override
	public Book saveNew(Book book) {
		EntityManager em = getEntityManager();
		
		em.getTransaction().begin();
		em.persist(book);
		em.flush();
		em.getTransaction().commit();
		em.close();
		
		return book;
	}

	@Override
	public Book update(Book book) {
		EntityManager em = getEntityManager();
		
		em.getTransaction().begin();
		em.merge(book);
		em.flush();
		em.clear();
		
		Book updated = em.find(Book.class, book.getId());
		
		em.getTransaction().commit();
		em.close();
		return updated;
	}

	@Override
	public void delete(Long id) {
		EntityManager em = getEntityManager();
		
		em.getTransaction().begin();
		Book book = em.find(Book.class, id);
		em.remove(book);
		em.flush();
		em.getTransaction().commit();
		em.close();
	}
	
	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

}
