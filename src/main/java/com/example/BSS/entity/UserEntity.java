package com.example.BSS.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Table(name = "user")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("service_code")
    private String serviceCode;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("address")
    private String address;

    @JsonProperty("service_used")
    private Instant serviceUsed;

    @JsonProperty("create_at")
    private Instant createAt;

    @JsonProperty("update_at")
    private Instant updateAt;

    @JsonProperty("contract_status")
    private int contractStatus;

    @JsonProperty("folk")
    private String folk;

    @JsonProperty("nation")
    private String nation;

    @JsonProperty("job")
    private String job;

    @JsonProperty("user_type")
    private String userType;

    @JsonProperty("document_type")
    private String documentType;

    @JsonProperty("document_create")
    private Instant documentCreate;

    @JsonProperty("document_address")
    private String documentAddress;

    @JsonProperty("tax_code")
    private String taxCode;

    @JsonProperty("email")
    private String email;

    @JsonProperty("user_status")
    private int userStatus;

    @JsonProperty("representative")
    private String representative;

    @JsonProperty("representative_address")
    private String representativeAddress;

    @JsonProperty("representative_phone")
    private String representativePhone;
}
