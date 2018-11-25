/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author SATELLITE
 */
@Entity
public class BkMad implements Serializable{
    @Id
    private Integer id;
    private String eve;
    private String ctr;
    private String dco;
    private String ad1p;
    private String ad2p;
    private String dbd;
    @ManyToOne
    private BkOpe ope;
    @ManyToOne
    private BkAgence age;
    private String mnt;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateEnvoie;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateRetrait;
    private boolean sent;
    private String clesec;
    private int traite;

    public int getTraite() {
        return traite;
    }

    public void setTraite(int traite) {
        this.traite = traite;
    }

    public String getClesec() {
        return clesec;
    }

    public void setClesec(String clesec) {
        this.clesec = clesec;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public String getEve() {
        return eve;
    }

    public void setEve(String eve) {
        this.eve = eve;
    }

    public String getCtr() {
        return ctr;
    }

    public void setCtr(String ctr) {
        this.ctr = ctr;
    }

    public String getDco() {
        return dco;
    }

    public void setDco(String dco) {
        this.dco = dco;
    }

    public String getAd1p() {
        return ad1p;
    }

    public void setAd1p(String ad1p) {
        this.ad1p = ad1p;
    }

    public String getAd2p() {
        return ad2p;
    }

    public void setAd2p(String ad2p) {
        this.ad2p = ad2p;
    }

    public String getDbd() {
        return dbd;
    }

    public void setDbd(String dbd) {
        this.dbd = dbd;
    }

    public BkOpe getOpe() {
        return ope;
    }

    public void setOpe(BkOpe ope) {
        this.ope = ope;
    }

    public BkAgence getAge() {
        return age;
    }

    public void setAge(BkAgence age) {
        this.age = age;
    }

    public String getMnt() {
        return mnt;
    }

    public void setMnt(String mnt) {
        this.mnt = mnt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateEnvoie() {
        return dateEnvoie;
    }

    public void setDateEnvoie(Date dateEnvoie) {
        this.dateEnvoie = dateEnvoie;
    }

    public Date getDateRetrait() {
        return dateRetrait;
    }

    public void setDateRetrait(Date dateRetrait) {
        this.dateRetrait = dateRetrait;
    }
}
