/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abouna.lacussms.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author SATELLITE
 */
@Entity
@Table(name = "COMMAND")
public class Command implements Serializable {
@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "CONTENT")
    private String content;
    @Column(name = "STATUS")
    private Status status;
    @Column(name = "SEND_DATE")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date sendDate;
    @Column(name = "PROCESSED_DATE")
     @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date processedDate;
    @Column(name = "ERROR_DESCRIPTION")
    private String errorDescription;
    @Column(name = "MESSAGE")
    private String message;
    @Column(name = "OPERATION")
    private String ope;
    private String compte;
    private Double montant;

    public Command() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getProcessedDate() {
        return processedDate;
    }

    public void setProcessedDate(Date processedDate) {
        this.processedDate = processedDate;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOpe() {
        return ope;
    }

    public void setOpe(String ope) {
        this.ope = ope;
    }

    public String getCompte() {
        return compte;
    }

    public void setCompte(String compte) {
        this.compte = compte;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    @Override
    public String toString() {
        return "Command{" + "id=" + id + ", phone=" + phone + ", content=" + content + ", status=" + status + ", sendDate=" + sendDate + ", processedDate=" + processedDate + ", errorDescription=" + errorDescription + ", message=" + message + ", ope=" + ope + ", compte=" + compte + '}';
    }


}
