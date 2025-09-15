package com.example.BSS.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "promotions")
public class PromotionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @JsonProperty("voucher_code")
    private String voucherCode;

    @JsonProperty("user_apply")
    private String userApply;

    @JsonProperty("count")
    private int count;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("status")
    private int status;

    @JsonProperty("txn_min_amount")
    private float txnMinAmount;

    @JsonProperty("discount_amount")
    private float discountAmount;

    @JsonProperty("expiration_at_voucher")
    private String expirationAtVoucher;

    @JsonProperty("type_customer")
    private String typeCustomer;

    @JsonProperty("service_type")
    private String serviceType;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public String getUserApply() {
        return userApply;
    }

    public void setUserApply(String userApply) {
        this.userApply = userApply;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getTxnMinAmount() {
        return txnMinAmount;
    }

    public void setTxnMinAmount(float txnMinAmount) {
        this.txnMinAmount = txnMinAmount;
    }

    public float getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(float discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getExpirationAtVoucher() {
        return expirationAtVoucher;
    }

    public void setExpirationAtVoucher(String expirationAtVoucher) {
        this.expirationAtVoucher = expirationAtVoucher;
    }

    public String getTypeCustomer() {
        return typeCustomer;
    }

    public void setTypeCustomer(String typeCustomer) {
        this.typeCustomer = typeCustomer;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}
