/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author SATELLITE
 */
@Entity
public class BkRepCode implements Serializable{
    @Id
    private String code;
    private String description;
    private BkTypeRep type;

    public BkRepCode() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BkTypeRep getType() {
        return type;
    }

    public void setType(BkTypeRep type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "BkRepCode{" + "code=" + code + ", description=" + description + ", type=" + type + '}';
    }
    
    
}
