/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author SATELLITE
 */
@Entity
public class Config implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false)
    private boolean mandat = false;
    private boolean event = false;
    private boolean bkmpai = false;
    private boolean bkmac = false;
    //private boolean requete = false;

    public Config() {
    }

    public Config( boolean mandat, boolean event, boolean bkmpai, boolean bkmac) {
        this.mandat = mandat;
        this.event = event;
        this.bkmpai = bkmpai;
        this.bkmac = bkmac;
    }

    public Config(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isMandat() {
        return mandat;
    }

    public void setMandat(boolean mandat) {
        this.mandat = mandat;
    }

    public boolean isEvent() {
        return event;
    }

    public void setEvent(boolean event) {
        this.event = event;
    }

    public boolean isBkmpai() {
        return bkmpai;
    }

    public void setBkmpai(boolean bkmpai) {
        this.bkmpai = bkmpai;
    }

    public boolean isBkmac() {
        return bkmac;
    }

    public void setBkmac(boolean bkmac) {
        this.bkmac = bkmac;
    }

//    public boolean isRequete() {
//        return requete;
//    }
//
//    public void setRequete(boolean requete) {
//        this.requete = requete;
//    }

    @Override
    public String toString() {
        return "Config{" + "id=" + id + ", mandat=" + mandat + ", event=" + event + ", bkmpai=" + bkmpai + ", bkmac=" + bkmac + '}';
    }
    
    
}
