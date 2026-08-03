package com.horizon.bank.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    private String id;

    @Column(name = "current_address_line")
    private String currentAddressLine;

    @Column(name = "current_city")
    private String currentCity;

    @Column(name = "current_state")
    private String currentState;

    @Column(name = "current_country")
    private String currentCountry;

    @Column(name = "current_pincode")
    private Integer currentPincode;

    @Column(name = "permanent_address_line")
    private String permanentAddressLine;

    @Column(name = "permanent_city")
    private String permanentCity;

    @Column(name = "permanent_state")
    private String permanentState;

    @Column(name = "permanent_country")
    private String permanentCountry;

    @Column(name = "permanent_pincode")
    private Integer permanentPincode;
}