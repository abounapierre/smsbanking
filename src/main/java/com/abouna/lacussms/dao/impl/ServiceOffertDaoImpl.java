/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.dao.impl;

import com.abouna.generic.dao.impl.GenericDao;
import com.abouna.lacussms.dao.ServiceOffertDao;
import com.abouna.lacussms.entities.ServiceOffert;
import java.util.List;
import javax.persistence.NoResultException;
import org.springframework.stereotype.Repository;

/**
 *
 * @author SATELLITE
 */
@Repository
public class ServiceOffertDaoImpl extends GenericDao<ServiceOffert,Integer> implements ServiceOffertDao{

    @Override
    public List<ServiceOffert> getServiceOfferts() {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT s FROM ServiceOffert s WHERE actif = :actif");
        return getManager().createQuery(builder.toString()).
                setParameter("actif", true).getResultList();
    }

    @Override
    public ServiceOffert findServiceByCode(String code) {
        try {
            StringBuilder builder = new StringBuilder();
            builder.append("SELECT s FROM ServiceOffert s WHERE code = :code");
            return (ServiceOffert) getManager().createQuery(builder.toString()).
                    setParameter("code", code).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
    
}
