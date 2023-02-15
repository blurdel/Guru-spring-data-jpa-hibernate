package com.blurdel.sdjpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.test.context.ActiveProfiles;

import com.blurdel.sdjpa.dao.AuthorDao;
import com.blurdel.sdjpa.dao.AuthorDaoImpl;
import com.blurdel.sdjpa.domain.Author;

@ActiveProfiles("mysql")
@DataJpaTest
//@ComponentScan(basePackages = {"com.blurdel.sdjpajdbc.dao"})
@Import(AuthorDaoImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorDaoIntegrationTest {

	@Autowired
	AuthorDao authorDao;
	
	
	@Test
	void testDeleteAuthor() {
		Author author = new Author();
		author.setFirstName("john");
		author.setLastName("t");
		
		Author saved = authorDao.saveNew(author);
		
		authorDao.delete(saved.getId());
		
		// Verify an exception is thrown (EmptyResultDataAccessException -> TransientDataAccessResourceException)
		assertThrows(TransientDataAccessResourceException.class, () -> {
			authorDao.getById(saved.getId());
		});
		
	}
	
	@Test
	void testUpdateAuthor() {
		Author author = new Author();
		author.setFirstName("john");
		author.setLastName("t");
		
		Author saved = authorDao.saveNew(author);
		
		saved.setLastName("Thompson");
		Author updated = authorDao.update(saved);
		
		assertThat(updated.getLastName()).isEqualTo("Thompson");
	}
	
	@Test
	void testSaveAuthor() {
		Author author = new Author();
		author.setFirstName("John");
		author.setLastName("thompson");
		Author saved = authorDao.saveNew(author);
		
		assertThat(saved).isNotNull();
	}
	
	@Test
	void testGetAuthorByName() {
		Author author = null;
		
		author = authorDao.getByName("Craig", "Walls");
		assertThat(author).isNotNull();
		
		assertThrows(EmptyResultDataAccessException.class, () -> {
			authorDao.getByName("David", "Anderson");
		});
	}
	
	@Test
	void testGetAuthorById() {
		Author author = authorDao.getById(1L);
		assertThat(author).isNotNull();
	}

}
