package com.mycompany.demo.jhipsterhol.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.demo.jhipsterhol.domain.HipsterPoi;
import com.mycompany.demo.jhipsterhol.repository.HipsterPoiRepository;
import com.mycompany.demo.jhipsterhol.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing HipsterPoi.
 */
@RestController
@RequestMapping("/api")
public class HipsterPoiResource {

    private final Logger log = LoggerFactory.getLogger(HipsterPoiResource.class);
        
    @Inject
    private HipsterPoiRepository hipsterPoiRepository;
    
    /**
     * POST  /hipster-pois : Create a new hipsterPoi.
     *
     * @param hipsterPoi the hipsterPoi to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hipsterPoi, or with status 400 (Bad Request) if the hipsterPoi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/hipster-pois",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HipsterPoi> createHipsterPoi(@Valid @RequestBody HipsterPoi hipsterPoi) throws URISyntaxException {
        log.debug("REST request to save HipsterPoi : {}", hipsterPoi);
        if (hipsterPoi.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hipsterPoi", "idexists", "A new hipsterPoi cannot already have an ID")).body(null);
        }
        HipsterPoi result = hipsterPoiRepository.save(hipsterPoi);
        return ResponseEntity.created(new URI("/api/hipster-pois/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hipsterPoi", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hipster-pois : Updates an existing hipsterPoi.
     *
     * @param hipsterPoi the hipsterPoi to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hipsterPoi,
     * or with status 400 (Bad Request) if the hipsterPoi is not valid,
     * or with status 500 (Internal Server Error) if the hipsterPoi couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/hipster-pois",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HipsterPoi> updateHipsterPoi(@Valid @RequestBody HipsterPoi hipsterPoi) throws URISyntaxException {
        log.debug("REST request to update HipsterPoi : {}", hipsterPoi);
        if (hipsterPoi.getId() == null) {
            return createHipsterPoi(hipsterPoi);
        }
        HipsterPoi result = hipsterPoiRepository.save(hipsterPoi);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hipsterPoi", hipsterPoi.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hipster-pois : get all the hipsterPois.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of hipsterPois in body
     */
    @RequestMapping(value = "/hipster-pois",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HipsterPoi> getAllHipsterPois() {
        log.debug("REST request to get all HipsterPois");
        List<HipsterPoi> hipsterPois = hipsterPoiRepository.findAllWithEagerRelationships();
        return hipsterPois;
    }

    /**
     * GET  /hipster-pois/:id : get the "id" hipsterPoi.
     *
     * @param id the id of the hipsterPoi to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hipsterPoi, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/hipster-pois/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HipsterPoi> getHipsterPoi(@PathVariable Long id) {
        log.debug("REST request to get HipsterPoi : {}", id);
        HipsterPoi hipsterPoi = hipsterPoiRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(hipsterPoi)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hipster-pois/:id : delete the "id" hipsterPoi.
     *
     * @param id the id of the hipsterPoi to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/hipster-pois/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHipsterPoi(@PathVariable Long id) {
        log.debug("REST request to delete HipsterPoi : {}", id);
        hipsterPoiRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hipsterPoi", id.toString())).build();
    }

}
