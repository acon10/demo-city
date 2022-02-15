package com.example.cities.domain;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Collection;

@Table(name = "city")
@Entity
@Data
public class City implements Serializable {

    @Id
    private Long id;
    private String name;

    @Column(name = "photo", length = 4000)
    private String photo;

}
