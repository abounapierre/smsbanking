/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.dao.impl;

import com.abouna.generic.dao.DataAccessException;
import com.abouna.generic.dao.impl.GenericDao;
import com.abouna.lacussms.dao.IBkEtatOpDao;
import com.abouna.lacussms.dao.IBkEveDao;
import com.abouna.lacussms.entities.BkCli_;

import com.abouna.lacussms.entities.BkEve;
import com.abouna.lacussms.entities.BkEve_;
import com.abouna.lacussms.entities.BkOpe_;
import com.abouna.lacussms.entities.TypeEvent;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author SATELLITE
 */
@Repository
public class BkEveDaoImpl extends GenericDao<BkEve, Integer> implements IBkEveDao{
    @Autowired
    private IBkEtatOpDao bkEtatOpDao;

    public IBkEtatOpDao getBkEtatOpDao() {
        return bkEtatOpDao;
    }

    public void setBkEtatOpDao(IBkEtatOpDao bkEtatOpDao) {
        this.bkEtatOpDao = bkEtatOpDao;
    }
    
    @Override
    public List<BkEve> getBkEvesByEtat(String etat,Date date) {
        CriteriaBuilder builder =  getManager().getCriteriaBuilder();
        CriteriaQuery<BkEve> cq = builder.createQuery(BkEve.class);
        Root<BkEve> bkEveRoot = cq.from(BkEve.class);
        cq.where(builder.and(builder.equal(bkEveRoot.get(BkEve_.etat), etat),
                builder.greaterThanOrEqualTo(bkEveRoot.get(BkEve_.eventDate), date)));
        cq.select(bkEveRoot).orderBy(builder.desc(bkEveRoot.get(BkEve_.eventDate)));
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public List<BkEve> getBkEveMaxDate() {
        CriteriaBuilder builder =  getManager().getCriteriaBuilder();
        CriteriaQuery<BkEve> cq = builder.createQuery(BkEve.class);
        Root<BkEve> bkEveRoot = cq.from(BkEve.class);
        cq.where(builder.or(builder.equal(bkEveRoot.get(BkEve_.etat), "VA"),
                builder.equal(bkEveRoot.get(BkEve_.etat), "VF")));
        cq.select(bkEveRoot).orderBy(builder.desc(bkEveRoot.get(BkEve_.eventDate)));
        return getManager().createQuery(cq).setMaxResults(1).getResultList();
    }

    @Override
    public List<BkEve> getBkEveByDate(Date date) {
       CriteriaBuilder builder =  getManager().getCriteriaBuilder();
        CriteriaQuery<BkEve> cq = builder.createQuery(BkEve.class);
        Root<BkEve> bkEveRoot = cq.from(BkEve.class);
        cq.where(builder.and(builder.or(builder.equal(bkEveRoot.get(BkEve_.etat), "VA"),
                builder.equal(bkEveRoot.get(BkEve_.etat), "VF")),
                builder.greaterThanOrEqualTo(bkEveRoot.get(BkEve_.eventDate),date)));
        cq.select(bkEveRoot);
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public List<BkEve> getBkEveBySendParam(boolean send) {
         CriteriaBuilder builder =  getManager().getCriteriaBuilder();
        CriteriaQuery<BkEve> cq = builder.createQuery(BkEve.class);
        Root<BkEve> bkEveRoot = cq.from(BkEve.class);
        cq.where(builder.and(builder.or(builder.equal(bkEveRoot.get(BkEve_.etat), "VA"),
                builder.equal(bkEveRoot.get(BkEve_.etat), "VF")),
                builder.equal(bkEveRoot.get(BkEve_.sent),send)));
        cq.select(bkEveRoot);
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public Integer getMaxIndexBkEve() {
        CriteriaBuilder builder =  getManager().getCriteriaBuilder();
        CriteriaQuery<Number> cq = builder.createQuery(Number.class);
        Root<BkEve> bkEveRoot = cq.from(BkEve.class);
        cq.select(builder.max(bkEveRoot.get(BkEve_.id)));
        return (Integer) getManager().createQuery(cq).getSingleResult();
    }

    @Override
    public List<BkEve> getBkEveByPeriode(Date d1, Date d2) {
        CriteriaBuilder builder =  getManager().getCriteriaBuilder();
        CriteriaQuery<BkEve> cq = builder.createQuery(BkEve.class);
        Root<BkEve> bkEveRoot = cq.from(BkEve.class);
        cq.where(builder.and(builder.greaterThanOrEqualTo(bkEveRoot.get(BkEve_.eventDate), d1),
                builder.lessThanOrEqualTo(bkEveRoot.get(BkEve_.eventDate), d2)));
        cq.select(bkEveRoot);
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public List<BkEve> getBkEveBySendParam(boolean send, List<String> list) {
       CriteriaBuilder builder =  getManager().getCriteriaBuilder();
        CriteriaQuery<BkEve> cq = builder.createQuery(BkEve.class);
        Root<BkEve> bkEveRoot = cq.from(BkEve.class);
        Expression<String> exp = bkEveRoot.get(BkEve_.etat);
        Predicate predicate = exp.in(list);
        cq.where(builder.and(builder.or(predicate),
                builder.equal(bkEveRoot.get(BkEve_.sent),send)));
        cq.select(bkEveRoot).orderBy();
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public List<BkEve> getBkEveByCriteria(String code, String date, String compte) {
       CriteriaBuilder builder =  getManager().getCriteriaBuilder();
        CriteriaQuery<BkEve> cq = builder.createQuery(BkEve.class);
        Root<BkEve> bkEveRoot = cq.from(BkEve.class);
        cq.where(builder.and(builder.equal(bkEveRoot.get(BkEve_.numEve), code),
                builder.equal(bkEveRoot.get(BkEve_.DVAB), date),
                builder.equal(bkEveRoot.get(BkEve_.compte), compte)));
        cq.select(bkEveRoot);
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public List<BkEve> getBkEveByCriteria(String code) {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<BkEve> cq = cb.createQuery(BkEve.class);
        Root<BkEve> bkEveRoot = cq.from(BkEve.class);
        cq.where(cb.or(cb.like(bkEveRoot.get(BkEve_.compte),"%"+code+"%"),
                cb.like(bkEveRoot.get(BkEve_.etat),"%"+code+"%"),
                cb.like(bkEveRoot.get(BkEve_.numEve),"%"+code+"%"),
                cb.like(bkEveRoot.get(BkEve_.DVAB),"%"+code+"%"),
                cb.like(bkEveRoot.get(BkEve_.hsai),"%"+code+"%"),
                cb.like(bkEveRoot.get(BkEve_.ope).get(BkOpe_.ope),"%"+code+"%"),
                cb.like(bkEveRoot.get(BkEve_.ope).get(BkOpe_.lib),"%"+code+"%"),
                cb.like(bkEveRoot.get(BkEve_.cli).get(BkCli_.nom),"%"+code+"%"),
                cb.like(bkEveRoot.get(BkEve_.cli).get(BkCli_.prenom),"%"+code+"%")));
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public int supprimerParPeriode(String date1, String date2) {
        for(BkEve eve : getBkEveByCriteria(date1, date2)){
            try {
                delete(findById(eve.getId()));
            } catch (DataAccessException ex) {
                Logger.getLogger(BkEveDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 0;
    }

    @Override
    public List<BkEve> getBkEveByCriteria(String date1, String date2) {
       CriteriaBuilder builder =  getManager().getCriteriaBuilder();
        CriteriaQuery<BkEve> cq = builder.createQuery(BkEve.class);
        Root<BkEve> bkEveRoot = cq.from(BkEve.class);
        cq.where(builder.and(builder.greaterThanOrEqualTo(bkEveRoot.get(BkEve_.DVAB), date1),
                builder.lessThanOrEqualTo(bkEveRoot.get(BkEve_.DVAB), date2)));
        cq.select(bkEveRoot);
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public int supprimerParPeriode(Date date1, Date date2) {
        int i =  0;
        for(BkEve eve : getBkEveByPeriode(date1, date2)){
            try {
                delete(findById(eve.getId()));
                i++;
            } catch (DataAccessException ex) {
                Logger.getLogger(BkEveDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return i;
    }

    @Override
    public Integer getMaxIndexBkEve(TypeEvent type) {
        try {
            CriteriaBuilder builder = getManager().getCriteriaBuilder();
            CriteriaQuery<Number> cq = builder.createQuery(Number.class);
            Root<BkEve> bkEveRoot = cq.from(BkEve.class);
            cq.where(builder.equal(bkEveRoot.get(BkEve_.type), type));
            cq.select(builder.max(bkEveRoot.get(BkEve_.id)));
            return (Integer) getManager().createQuery(cq).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

    @Override
    public List<BkEve> getBkEveBySendParam(boolean send, List<String> list, TypeEvent type) {
       CriteriaBuilder builder =  getManager().getCriteriaBuilder();
        CriteriaQuery<BkEve> cq = builder.createQuery(BkEve.class);
        Root<BkEve> bkEveRoot = cq.from(BkEve.class);
        Expression<String> exp = bkEveRoot.get(BkEve_.etat);
        Predicate predicate = exp.in(list);
        cq.where(builder.and(builder.or(predicate),
                builder.equal(bkEveRoot.get(BkEve_.sent),send),
                builder.equal(bkEveRoot.get(BkEve_.type),type)));
        cq.select(bkEveRoot);
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public List<BkEve> getBkEveByCriteria(String code, Date date, String compte) {
        CriteriaBuilder builder =  getManager().getCriteriaBuilder();
        CriteriaQuery<BkEve> cq = builder.createQuery(BkEve.class);
        Root<BkEve> bkEveRoot = cq.from(BkEve.class);
        cq.where(builder.and(builder.equal(bkEveRoot.get(BkEve_.numEve), code),
                builder.equal(bkEveRoot.get(BkEve_.eventDate), date),
                builder.equal(bkEveRoot.get(BkEve_.compte), compte)));
        cq.select(bkEveRoot);
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public List<BkEve> getBkEveByLimit(int limit) {
        CriteriaBuilder builder =  getManager().getCriteriaBuilder();
        CriteriaQuery<BkEve> cq = builder.createQuery(BkEve.class);
        Root<BkEve> bkEveRoot = cq.from(BkEve.class);
        cq.select(bkEveRoot);
        cq.orderBy(builder.desc(bkEveRoot.get(BkEve_.id)));
        return getManager().createQuery(cq).setMaxResults(limit).getResultList();
    }

    @Override
    public List<BkEve> getBkEveByCriteria2(String code, Date date, String compte) {
        CriteriaBuilder builder =  getManager().getCriteriaBuilder();
        CriteriaQuery<BkEve> cq = builder.createQuery(BkEve.class);
        Root<BkEve> bkEveRoot = cq.from(BkEve.class);
        cq.where(builder.and(builder.equal(bkEveRoot.get(BkEve_.numEve), code),
                builder.equal(bkEveRoot.get(BkEve_.DCO), date),
                builder.equal(bkEveRoot.get(BkEve_.compte), compte)));
        cq.select(bkEveRoot);
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public List<BkEve> getBkEveByCriteria(String code, String compte, String heure, String montant) {
        CriteriaBuilder builder =  getManager().getCriteriaBuilder();
        CriteriaQuery<BkEve> cq = builder.createQuery(BkEve.class);
        Root<BkEve> bkEveRoot = cq.from(BkEve.class);
        cq.where(builder.and(builder.equal(bkEveRoot.get(BkEve_.numEve), code),
                builder.equal(bkEveRoot.get(BkEve_.hsai), heure),
                builder.equal(bkEveRoot.get(BkEve_.montant), montant),
                builder.equal(bkEveRoot.get(BkEve_.compte), compte)));
        cq.select(bkEveRoot);
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public List<BkEve> getBkEveByCriteriaMontant(String code, String compte, String montant) {
        CriteriaBuilder builder =  getManager().getCriteriaBuilder();
        CriteriaQuery<BkEve> cq = builder.createQuery(BkEve.class);
        Root<BkEve> bkEveRoot = cq.from(BkEve.class);
        cq.where(builder.and(builder.equal(bkEveRoot.get(BkEve_.numEve), code),
                builder.equal(bkEveRoot.get(BkEve_.montant), montant),
                builder.equal(bkEveRoot.get(BkEve_.compte), compte)));
        cq.select(bkEveRoot);
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public List<BkEve> getBkEveByPeriode(String code, String compte, Date date1, Date date2) {
        CriteriaBuilder builder =  getManager().getCriteriaBuilder();
        CriteriaQuery<BkEve> cq = builder.createQuery(BkEve.class);
        Root<BkEve> bkEveRoot = cq.from(BkEve.class);
        cq.where(builder.and(builder.equal(bkEveRoot.get(BkEve_.numEve), code),
                builder.greaterThanOrEqualTo(bkEveRoot.get(BkEve_.eventDate), date1),
                builder.lessThanOrEqualTo(bkEveRoot.get(BkEve_.eventDate), date2),
                builder.equal(bkEveRoot.get(BkEve_.compte), compte)));
        cq.select(bkEveRoot);
        return getManager().createQuery(cq).getResultList();
    }
        
}
