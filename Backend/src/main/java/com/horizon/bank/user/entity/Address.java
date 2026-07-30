package com.horizon.bank.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name="addresses")
public class Address {
    @Id
    private String id;
    private String current_address_line;
    private String current_city;
    private String current_state;
    private String current_country;
    private Integer current_pincode;
    private String permanent_address_line;
    private String permanent_city;
    private String permanent_state;
    private String permanent_country;
    private Integer permanent_pincode;

}
