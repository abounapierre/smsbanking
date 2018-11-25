/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abouna.lacussms.dao.impl;

import com.abouna.generic.dao.impl.GenericDao;
import com.abouna.lacussms.dao.IBkEtatOpDao;
import com.abouna.lacussms.entities.BkEtatOp;
import com.abouna.lacussms.entities.BkEtatOp_;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ABOUNA
 */
@Repository
public class BkEtatOpDaoImpl extends GenericDao<BkEtatOp, Integer> implements IBkEtatOpDao{

    @Override
    public List<BkEtatOp> getListBkEtatOp(boolean actif) {
        CriteriaBuilder builder =  getManager().getCriteriaBuilder();
        CriteriaQuery<BkEtatOp> cq = builder.createQuery(BkEtatOp.class);
        Root<BkEtatOp> etatRoot = cq.from(BkEtatOp.class);
        cq.where(builder.equal(etatRoot.get(BkEtatOp_.actif), actif));
        cq.select(etatRoot);
        return getManager().createQuery(cq).getResultList();
    }
    
}
