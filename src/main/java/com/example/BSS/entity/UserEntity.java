package com.example.BSS.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "user")
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
    private String serviceUsed;

    @JsonProperty("create_at")
    private Instant createAt;

    @JsonProperty("update_at")
    private Instant updateAt;

    @JsonIgnore
    private int contractStatus;

    @Transient
    private String contractStatusText;

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

    @JsonProperty("document_end")
    private String documentEnd;

    @JsonProperty("tax_code")
    private String taxCode;

    @JsonProperty("email")
    private String email;

    @JsonIgnore
    private int userStatus;

    @Transient
    private String userStatusText;

    @JsonProperty("representative")
    private String representative;


    @JsonProperty("representative_address")
    private String representativeAddress;

    @JsonProperty("representative_phone")
    private String representativePhone;

    @JsonProperty("sex")
    private String sex;

    @JsonProperty("expiration_at")
    private Instant expirationAt;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @JsonProperty("role")
    private String role;


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getDocumentEnd() {
        return documentEnd;
    }

    public void setDocumentEnd(String documentEnd) {
        this.documentEnd = documentEnd;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setServiceUsed(String serviceUsed) {
        this.serviceUsed = serviceUsed;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    public void setUpdateAt(Instant updateAt) {
        this.updateAt = updateAt;
    }

    public void setContractStatus(int contractStatus) {
        this.contractStatus = contractStatus;
    }

    public void setFolk(String folk) {
        this.folk = folk;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public void setDocumentCreate(Instant documentCreate) {
        this.documentCreate = documentCreate;
    }

    public void setDocumentAddress(String documentAddress) {
        this.documentAddress = documentAddress;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContractStatusText() {
        if (contractStatus == 1) {
            return "Hoạt động";
        } else {
            return "Không hoạt động";
        }
    }

    public String getUserStatusText() {
        if (userStatus == 1) {
            return "Hoạt động";
        } else {
            return "Không hoạt động";
        }
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public void setRepresentative(String representative) {
        this.representative = representative;
    }

    public void setRepresentativeAddress(String representativeAddress) {
        this.representativeAddress = representativeAddress;
    }

    public void setRepresentativePhone(String representativePhone) {
        this.representativePhone = representativePhone;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getServiceUsed() {
        return serviceUsed;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

    public int getContractStatus() {
        return contractStatus;
    }

    public String getFolk() {
        return folk;
    }

    public String getNation() {
        return nation;
    }

    public String getJob() {
        return job;
    }

    public String getUserType() {
        return userType;
    }

    public String getDocumentType() {
        return documentType;
    }

    public Instant getDocumentCreate() {
        return documentCreate;
    }

    public String getDocumentAddress() {
        return documentAddress;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public String getEmail() {
        return email;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public String getRepresentative() {
        return representative;
    }

    public String getRepresentativeAddress() {
        return representativeAddress;
    }

    public String getRepresentativePhone() {
        return representativePhone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Instant getExpirationAt() {
        return expirationAt;
    }

    public void setExpirationAt(Instant expirationAt) {
        this.expirationAt = expirationAt;
    }
}
