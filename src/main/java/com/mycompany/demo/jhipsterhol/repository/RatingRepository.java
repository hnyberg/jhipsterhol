package com.mycompany.demo.jhipsterhol.repository;

import com.mycompany.demo.jhipsterhol.domain.Rating;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Rating entity.
 */
public interface RatingRepository extends JpaRepository<Rating,Long> {

    @Query("select rating from Rating rating where rating.user.login = ?#{principal.username}")
    List<Rating> findByUserIsCurrentUser();

}
