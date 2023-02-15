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
		return getEntityManager().find(Book.class, id);
	}

	@Override
	public Book getByTitle(String title) {
		TypedQuery<Book> query = getEntityManager().createQuery("select a from Book a " +
				"where a.title = :title", Book.class);
		
		query.setParameter("title", title);
		
		return query.getSingleResult();
	}

	@Override
	public Book saveNew(Book book) {
		EntityManager em = getEntityManager();
		
		em.getTransaction().begin();
		em.persist(book);
		em.flush();
		em.getTransaction().commit();
		
		return book;
	}

	@Override
	public Book update(Book book) {
		EntityManager em = getEntityManager();
		
		em.joinTransaction();
		em.merge(book);
		em.flush();
		em.clear();
		
		return em.find(Book.class, book.getId());
	}

	@Override
	public void delete(Long id) {
		EntityManager em = getEntityManager();
		
		em.getTransaction().begin();
		Book book = em.find(Book.class, id);
		em.remove(book);
		em.flush();
		em.getTransaction().commit();		
	}
	
	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

}
