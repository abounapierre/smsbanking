/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abouna.lacussms.dao.impl;

import com.abouna.generic.dao.impl.GenericDao;
import com.abouna.lacussms.dao.IUrlMessageDao;
import com.abouna.lacussms.entities.UrlMessage;
import com.abouna.lacussms.entities.UrlMessage_;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ABOUNA
 */
@Repository
public class UrlMessageDaoImpl extends GenericDao<UrlMessage, Integer> implements IUrlMessageDao{

    @Override
    public UrlMessage getDefaultUrlMessage() {
        try{
        CriteriaBuilder builder =  getManager().getCriteriaBuilder();
        CriteriaQuery<UrlMessage> cq = builder.createQuery(UrlMessage.class);
        Root<UrlMessage> urlRoot = cq.from(UrlMessage.class);
        cq.where(builder.equal(urlRoot.get(UrlMessage_.defaultUrl), true));
        cq.select(urlRoot);
        return getManager().createQuery(cq).getSingleResult();
        }catch(NoResultException ex){
           return null;
        }
        
    }
    
}
