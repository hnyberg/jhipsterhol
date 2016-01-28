package com.mycompany.demo.jhipsterhol.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.demo.jhipsterhol.domain.Type;
import com.mycompany.demo.jhipsterhol.repository.TypeRepository;
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
 * REST controller for managing Type.
 */
@RestController
@RequestMapping("/api")
public class TypeResource {

    private final Logger log = LoggerFactory.getLogger(TypeResource.class);
        
    @Inject
    private TypeRepository typeRepository;
    
    /**
     * POST  /types -> Create a new type.
     */
    @RequestMapping(value = "/types",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Type> createType(@Valid @RequestBody Type type) throws URISyntaxException {
        log.debug("REST request to save Type : {}", type);
        if (type.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("type", "idexists", "A new type cannot already have an ID")).body(null);
        }
        Type result = typeRepository.save(type);
        return ResponseEntity.created(new URI("/api/types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("type", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /types -> Updates an existing type.
     */
    @RequestMapping(value = "/types",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Type> updateType(@Valid @RequestBody Type type) throws URISyntaxException {
        log.debug("REST request to update Type : {}", type);
        if (type.getId() == null) {
            return createType(type);
        }
        Type result = typeRepository.save(type);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("type", type.getId().toString()))
            .body(result);
    }

    /**
     * GET  /types -> get all the types.
     */
    @RequestMapping(value = "/types",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Type> getAllTypes() {
        log.debug("REST request to get all Types");
        return typeRepository.findAll();
            }

    /**
     * GET  /types/:id -> get the "id" type.
     */
    @RequestMapping(value = "/types/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Type> getType(@PathVariable Long id) {
        log.debug("REST request to get Type : {}", id);
        Type type = typeRepository.findOne(id);
        return Optional.ofNullable(type)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /types/:id -> delete the "id" type.
     */
    @RequestMapping(value = "/types/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteType(@PathVariable Long id) {
        log.debug("REST request to delete Type : {}", id);
        typeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("type", id.toString())).build();
    }
}
