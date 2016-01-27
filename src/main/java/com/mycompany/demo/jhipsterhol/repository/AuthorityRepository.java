package com.mycompany.demo.jhipsterhol.repository;

import com.mycompany.demo.jhipsterhol.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
