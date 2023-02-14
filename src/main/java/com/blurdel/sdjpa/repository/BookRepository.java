package com.blurdel.sdjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blurdel.sdjpa.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
