package com.mycompany.demo.jhipsterhol.repository;

import com.mycompany.demo.jhipsterhol.domain.HipsterPoi;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the HipsterPoi entity.
 */
public interface HipsterPoiRepository extends JpaRepository<HipsterPoi,Long> {

    @Query("select hipsterPoi from HipsterPoi hipsterPoi where hipsterPoi.user.login = ?#{principal.username}")
    List<HipsterPoi> findByUserIsCurrentUser();

    @Query("select distinct hipsterPoi from HipsterPoi hipsterPoi left join fetch hipsterPoi.types")
    List<HipsterPoi> findAllWithEagerRelationships();

    @Query("select hipsterPoi from HipsterPoi hipsterPoi left join fetch hipsterPoi.types where hipsterPoi.id =:id")
    HipsterPoi findOneWithEagerRelationships(@Param("id") Long id);

}
