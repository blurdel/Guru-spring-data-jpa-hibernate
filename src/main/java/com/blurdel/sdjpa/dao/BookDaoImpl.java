package com.blurdel.sdjpa.dao;

import java.util.List;

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
	public List<Book> findAll() {
		EntityManager em = getEntityManager();
		
		try {
			TypedQuery<Book> query = em.createNamedQuery("book_find_all", Book.class);
			return query.getResultList();
		}
		finally {
			em.close();
		}
	}
	
	@Override
	public Book findByISBN(String isbn) {
		EntityManager em = getEntityManager();
		
		try {
			TypedQuery<Book> query = em.createQuery("select b from Book b where b.isbn = :isbn", Book.class);
			query.setParameter("isbn", isbn);
			
			Book book = query.getSingleResult();
			return book;
		} 
		finally {
			em.close();
		}
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
		
//		TypedQuery<Book> query = em.createQuery("select a from Book a " +
//				"where a.title = :title", Book.class);
		
		TypedQuery<Book> query = em.createNamedQuery("find_by_title", Book.class);	
		
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
