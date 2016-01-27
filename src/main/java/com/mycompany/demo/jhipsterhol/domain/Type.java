package com.mycompany.demo.jhipsterhol.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Type.
 */
@Entity
@Table(name = "type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Type implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;
    
    @ManyToMany(mappedBy = "types")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<HipsterPoi> hipsterPois = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public Set<HipsterPoi> getHipsterPois() {
        return hipsterPois;
    }

    public void setHipsterPois(Set<HipsterPoi> hipsterPois) {
        this.hipsterPois = hipsterPois;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Type type = (Type) o;
        if(type.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, type.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Type{" +
            "id=" + id +
            ", title='" + title + "'" +
            '}';
    }
}
