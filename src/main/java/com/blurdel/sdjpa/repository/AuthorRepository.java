package com.blurdel.sdjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blurdel.sdjpa.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
