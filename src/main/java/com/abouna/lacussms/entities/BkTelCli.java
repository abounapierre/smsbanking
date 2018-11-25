/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abouna.lacussms.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author ABOUNA
 */
@Entity
public class BkTelCli implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "cli")
    private BkCli bkCli;
    private long numTel;
    private String typeNum;
    private boolean pardefault = false;

    public boolean isPardefault() {
        return pardefault;
    }

    public void setPardefault(boolean pardefault) {
        this.pardefault = pardefault;
    }

    public BkTelCli() {
    }

    public BkTelCli(BkCli bkCli, long numTel, String typeNum) {
        this.bkCli = bkCli;
        this.numTel = numTel;
        this.typeNum = typeNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BkCli getBkCli() {
        return bkCli;
    }

    public void setBkCli(BkCli bkCli) {
        this.bkCli = bkCli;
    }

    public long getNumTel() {
        return numTel;
    }

    public void setNumTel(long numTel) {
        this.numTel = numTel;
    }

    public String getTypeNum() {
        return typeNum;
    }

    public void setTypeNum(String typeNum) {
        this.typeNum = typeNum;
    }
    
    
}
