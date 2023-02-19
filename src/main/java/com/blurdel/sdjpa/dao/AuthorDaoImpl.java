package com.blurdel.sdjpa.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.blurdel.sdjpa.domain.Author;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Component
public class AuthorDaoImpl implements AuthorDao {

	private final EntityManagerFactory emf;
	
		
	public AuthorDaoImpl(EntityManagerFactory emf) {
		this.emf = emf;
	}
	
	
	@Override
	public List<Author> findAll() {
		EntityManager em = getEntityManager();
		
		try {
			TypedQuery<Author> query = em.createNamedQuery("author_find_all", Author.class);
			return query.getResultList();
		} 
		finally {
			em.close();
		}
	}
	
	@Override
	public List<Author> listAuthorByLastNameLike(String lastName) {
		EntityManager em = getEntityManager();
		
		try {
			Query query = em.createQuery("select a from Author a where a.lastName like :last_name");
			query.setParameter("last_name", lastName + "%");
			List<Author> list = query.getResultList();
			
			return list;
		} 
		finally {
			em.close();
		}
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
		
//		TypedQuery<Author> query = em.createQuery("select a from Author a " +
//				"where a.firstName = :first_name and a.lastName = :last_name", Author.class);
		
		TypedQuery<Author> query = em.createNamedQuery("find_by_name", Author.class);
		
		query.setParameter("first_name", firstName);
		query.setParameter("last_name", lastName);
		
		Author author = query.getSingleResult();
		em.close();
		return author;
	}
	
	@Override
	public Author getByNameNative(String firstName, String lastName) {
		EntityManager em = getEntityManager();
		
		try {
			// Note: Native query table names must match database
			Query query = em.createNativeQuery("select * from author a where a.first_name = ? and a.last_name = ?", Author.class);
			
			query.setParameter(1, firstName);
			query.setParameter(2, lastName);
			
			return (Author) query.getSingleResult();
		} 
		finally {
			em.close();
		}
	}
	
//	@Override
//	public Author getByNamedCriteria(String firstName, String lastName) {
//		EntityManager em = getEntityManager();
//		
//		try {
//			
//			CriteriaBuilder cb = em.getCriteriaBuilder();
//			CriteriaQuery<Author> query = cb.createQuery(Author.class);
//			
//			Root<Author> root = query.from(Author.class);
//			ParameterExpression<String> peFirstName = cb.parameter(String.class);
//			ParameterExpression<String> peLastName = cb.parameter(String.class);
//			
//			Predicate predFirst = cb.equal(root.get("firstName"), peFirstName);
//			Predicate predLast = cb.equal(root.get("lastName"), peLastName);
//			
//			query.select(root).where(cb.and(predFirst, predLast));
//			
//			TypedQuery<Author> typedQuery = em.createQuery(query);
//			typedQuery.setParameter(peFirstName, firstName);
//			typedQuery.setParameter(peLastName, lastName);
//			
//			return typedQuery.getSingleResult();
//		} 
//		finally {
//			em.close();
//		}		
//	}

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
