/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.dao;

import com.abouna.generic.dao.IDao;
import com.abouna.lacussms.entities.ServiceOffert;
import java.util.List;

/**
 *
 * @author SATELLITE
 */
public interface ServiceOffertDao extends IDao<ServiceOffert, Integer>{
    public List<ServiceOffert> getServiceOfferts();
    public ServiceOffert findServiceByCode(String code);
}
