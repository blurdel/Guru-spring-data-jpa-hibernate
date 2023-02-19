package com.blurdel.sdjpa;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.blurdel.sdjpa.dao.BookDao;
import com.blurdel.sdjpa.dao.BookDaoImpl;
import com.blurdel.sdjpa.domain.Book;

@ActiveProfiles("mysql")
@DataJpaTest
//@ComponentScan(basePackages = {"com.blurdel.sdjpajdbc.dao"})
@Import(BookDaoImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookDaoIntegrationTest {

	@Autowired
	BookDao bookDao;
	
	
	@Test
	void testFindAllBooks() {
		List<Book> books = bookDao.findAll();
		
		assertThat(books).isNotNull();
		assertThat(books.size()).isGreaterThan(0);
	}
	
	@Test
	void testFindBookByTitle() {
		Book book = new Book();
		book.setIsbn("1235" + RandomString.make());
		book.setTitle("TITLE" + RandomString.make());
		
		bookDao.saveNew(book);
		
		Book fetched = bookDao.getByTitle(book.getTitle());
		assertThat(fetched).isNotNull();
	}
	
	@Test
	void testFindBookByTitleNative() {
		Book fetched = bookDao.getByTitleNative("Clean Code");
		assertThat(fetched).isNotNull();
	}
	
	@Test
	void testFindBookByISBN() {
		Book book = new Book();
		book.setIsbn("1234" + RandomString.make());
		book.setTitle("ISBN TEST");
		
		bookDao.saveNew(book);
		
		Book fetched = bookDao.findByISBN(book.getIsbn());
		assertThat(fetched).isNotNull();
	}
	
	@Test
    void testDeleteBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
        
        Book saved = bookDao.saveNew(book);

        bookDao.delete(saved.getId());
        
        Book deleted = bookDao.getById(saved.getId());
        assertThat(deleted).isNull();

        // Double-check
        assertThat(bookDao.getById(saved.getId())).isNull();
    }

    @Test
    void updateBookTest() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
        book.setAuthorId(1L);
        
        Book saved = bookDao.saveNew(book);

        saved.setTitle("New Book");
        bookDao.update(saved);
        
        Book fetched = bookDao.getById(saved.getId());

        assertThat(fetched.getTitle()).isEqualTo("New Book");
    }

    @Test
    void testSaveBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
        book.setAuthorId(1L);
        Book saved = bookDao.saveNew(book);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull(); // If no commit or transaction, id will be null here...
    }

    @Test
    void testGetBookByName() {
        Book book = bookDao.getByTitle("Clean Code");

        assertThat(book).isNotNull();
    }

    @Test
    void testGetBook() {
        Book book = bookDao.getById(3L);
        assertThat(book.getId()).isNotNull();
    }
    
}

