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
 * A HipsterPoi.
 */
@Entity
@Table(name = "hipster_poi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HipsterPoi implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "adress")
    private String adress;
    
    @Column(name = "latitude")
    private Double latitude;
    
    @Column(name = "longitude")
    private Double longitude;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "hipsterPoi")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Rating> ratings = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "hipster_poi_type",
               joinColumns = @JoinColumn(name="hipster_pois_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="types_id", referencedColumnName="ID"))
    private Set<Type> types = new HashSet<>();

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

    public String getAdress() {
        return adress;
    }
    
    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Double getLatitude() {
        return latitude;
    }
    
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }

    public Set<Type> getTypes() {
        return types;
    }

    public void setTypes(Set<Type> types) {
        this.types = types;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HipsterPoi hipsterPoi = (HipsterPoi) o;
        if(hipsterPoi.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, hipsterPoi.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HipsterPoi{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", adress='" + adress + "'" +
            ", latitude='" + latitude + "'" +
            ", longitude='" + longitude + "'" +
            '}';
    }
}
