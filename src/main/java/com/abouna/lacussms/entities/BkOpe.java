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
@Table(name = "bkope")
public class BkOpe implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    private String ope;
    private String lib;

    public BkOpe() {
    }

    public BkOpe(String ope, String lib) {
        this.ope = ope;
        this.lib = lib;
    }

    public String getOpe() {
        return ope;
    }

    public void setOpe(String ope) {
        this.ope = ope;
    }

    public String getLib() {
        return lib;
    }

    public void setLib(String lib) {
        this.lib = lib;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.ope != null ? this.ope.hashCode() : 0);
        hash = 41 * hash + (this.lib != null ? this.lib.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BkOpe other = (BkOpe) obj;
        if ((this.ope == null) ? (other.ope != null) : !this.ope.equals(other.ope)) {
            return false;
        }
        if ((this.lib == null) ? (other.lib != null) : !this.lib.equals(other.lib)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  lib ;
    }
    
}
