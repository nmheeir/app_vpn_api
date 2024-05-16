package com.example.vpn.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "payment_record")
public class PaymentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "txn_ref")
    private String txnRef;

    @Column(name = "username")
    private String username;

    @Column(name = "pay_date")
    private String createDate;

    public PaymentRecord(String _txnRef, String _username, String _createDate) {
        this.txnRef = _txnRef;
        this.username = _username;
        this.createDate = _createDate;
    }

    public PaymentRecord() {

    }
}
