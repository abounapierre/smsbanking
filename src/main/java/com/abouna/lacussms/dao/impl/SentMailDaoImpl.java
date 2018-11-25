/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.dao.impl;

import com.abouna.generic.dao.impl.GenericDao;
import com.abouna.lacussms.dao.ISentMailDao;
import com.abouna.lacussms.entities.SentMail;
import com.abouna.lacussms.entities.SentMail_;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

/**
 *
 * @author SATELLITE
 */
@Repository
public class SentMailDaoImpl extends GenericDao<SentMail, Integer> implements ISentMailDao{

    @Override
    public List<SentMail> getMailByDate(Date d) {
        CriteriaBuilder builder =  getManager().getCriteriaBuilder();
        CriteriaQuery<SentMail> cq = builder.createQuery(SentMail.class);
        Root<SentMail> mailRoot = cq.from(SentMail.class);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        cq.where(builder.equal(mailRoot.get(SentMail_.date2), format.format(d)));
        cq.select(mailRoot);//.orderBy(builder.desc(bkEveRoot.get(SentMail_.sentDate)));
        return getManager().createQuery(cq).getResultList();
    }
    
}
