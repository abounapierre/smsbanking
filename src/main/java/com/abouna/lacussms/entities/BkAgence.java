/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author SATELLITE
 */
@Entity
@Table(name = "bkagence")
public class BkAgence implements Serializable{
    @Id
    private String numa;
    private String noma;
    private String addra;

    public BkAgence() {
    }

    public BkAgence(String numa, String noma, String addra) {
        this.numa = numa;
        this.noma = noma;
        this.addra = addra;
    }

    public String getNuma() {
        return numa;
    }

    public void setNuma(String numa) {
        this.numa = numa;
    }

    public String getNoma() {
        return noma;
    }

    public void setNoma(String noma) {
        this.noma = noma;
    }

    public String getAddra() {
        return addra;
    }

    public void setAddra(String addra) {
        this.addra = addra;
    }

    @Override
    public String toString() {
        return  noma ;
    }
    
    
}
