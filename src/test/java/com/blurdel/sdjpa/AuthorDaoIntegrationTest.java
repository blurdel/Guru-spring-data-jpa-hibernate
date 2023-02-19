package com.blurdel.sdjpa;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
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
	void testFindAuthorByNamedNative() {
		Author author = authorDao.getByNameNative("Craig", "Walls");
		assertThat(author).isNotNull();
	}
	
//	@Test
//	void testFindAuthorByNamedCriteria() {
//		Author author = authorDao.getByNamedCriteria("Craig", "Walls");
//		assertThat(author).isNotNull();
//	}
	
	@Test
	void testFindAllAuthors() {
		List<Author> authors = authorDao.findAll();
		
		assertThat(authors).isNotNull();
		assertThat(authors.size()).isGreaterThan(0);		
	}
	
	@Test
	void testListAuthorsByLastNameLike() {
		List<Author> authors = authorDao.listAuthorByLastNameLike("Wall");
		
		assertThat(authors).isNotNull();
		assertThat(authors.size()).isGreaterThan(0);	
	}
	
	@Test
	void testDeleteAuthor() {
		Author author = new Author();
		author.setFirstName("john");
		author.setLastName("t");
		
		Author saved = authorDao.saveNew(author);
		
		authorDao.delete(saved.getId());
		
		Author deleted = authorDao.getById(saved.getId());
		assertThat(deleted).isNull();
		
		// Double-check
		assertThat(authorDao.getById(saved.getId())).isNull();
	}
	
	@Test
	void testUpdateAuthor() {
		Author author = new Author();
		author.setFirstName("john");
		author.setLastName("t");
		
		Author saved = authorDao.saveNew(author);
		
		saved.setLastName("Thompson");
		authorDao.update(saved);
		
		Author fetched = authorDao.getById(saved.getId());
		
		assertThat(fetched.getLastName()).isEqualTo("Thompson");
	}
	
	@Test
	void testSaveAuthor() {
		Author author = new Author();
		author.setFirstName("John");
		author.setLastName("thompson");
		Author saved = authorDao.saveNew(author);
		
		assertThat(saved).isNotNull();
		assertThat(saved.getId()).isNotNull(); // If no commit or transaction, id will be null here...
	}
	
	@Test
	void testGetAuthorByName() {
		Author author = authorDao.getByName("Craig", "Walls");
		
		assertThat(author).isNotNull();
		
//		assertThrows(EmptyResultDataAccessException.class, () -> {
//			authorDao.getByName("David", "Anderson");
//		});
	}
	
	@Test
	void testGetAuthorById() {
		Author author = authorDao.getById(1L);
		assertThat(author).isNotNull();
	}

}
