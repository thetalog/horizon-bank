package com.horizon.bank.user.entity;
import com.horizon.bank.user.entity.enums.DocumentType;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="KYC")
public class KYC {
    @Id
    private String id;
    private DocumentType document_type;
    private String document_number;
}
