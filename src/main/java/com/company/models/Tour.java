package com.company.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tours")
@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tour_id")
    private int id;
    private String description;
    @ManyToOne
    @JoinColumn(name = "type_tour", referencedColumnName = "type_id")
    private Type type;
    @ManyToOne
    @JoinColumn(name = "hotel", referencedColumnName = "hotel_id")
    private Hotel hotel;
}
