/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.dao.impl;

import com.abouna.generic.dao.impl.GenericDao;
import com.abouna.lacussms.dao.IMessageFormatDao;
import com.abouna.lacussms.entities.BkOpe;
import com.abouna.lacussms.entities.MessageFormat;
import com.abouna.lacussms.entities.MessageFormat_;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

/**
 *
 * @author SATELLITE
 */
@Repository
public class MessageFormatDaoImpl extends GenericDao<MessageFormat, Integer> implements IMessageFormatDao{

    @Override
    public List<MessageFormat> getMessageFormatByOpe(BkOpe bkOpe) {
        CriteriaBuilder builder =  getManager().getCriteriaBuilder();
        CriteriaQuery<MessageFormat> cq = builder.createQuery(MessageFormat.class);
        Root<MessageFormat> messageRoot = cq.from(MessageFormat.class);
        cq.where(builder.equal(messageRoot.get(MessageFormat_.ope), bkOpe));
        cq.select(messageRoot);
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public MessageFormat getMessageFormatByOpe(BkOpe bkOpe, String langue) {
        try {
            CriteriaBuilder builder =  getManager().getCriteriaBuilder();
        CriteriaQuery<MessageFormat> cq = builder.createQuery(MessageFormat.class);
        Root<MessageFormat> messageRoot = cq.from(MessageFormat.class);
        cq.where(builder.and(builder.equal(messageRoot.get(MessageFormat_.ope), bkOpe),
                builder.equal(messageRoot.get(MessageFormat_.langue), langue)));
        cq.select(messageRoot);
        return getManager().createQuery(cq).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
}
