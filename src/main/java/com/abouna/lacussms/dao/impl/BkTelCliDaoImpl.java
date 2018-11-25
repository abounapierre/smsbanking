/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abouna.lacussms.dao.impl;

import com.abouna.generic.dao.impl.GenericDao;
import com.abouna.lacussms.dao.IBkTelCliDao;
import com.abouna.lacussms.entities.BkCli;
import com.abouna.lacussms.entities.BkTelCli;
import com.abouna.lacussms.entities.BkTelCli_;
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
public class BkTelCliDaoImpl extends GenericDao<BkTelCli, Integer> implements IBkTelCliDao{

    @Override
    public List<BkTelCli> getListBkTelByCli(BkCli b) {
         CriteriaBuilder builder =  getManager().getCriteriaBuilder();
        CriteriaQuery<BkTelCli> cq = builder.createQuery(BkTelCli.class);
        Root<BkTelCli> bkTelRoot = cq.from(BkTelCli.class);
        cq.where(builder.equal(bkTelRoot.get(BkTelCli_.bkCli), b));
        cq.select(bkTelRoot).orderBy(builder.desc(bkTelRoot.get(BkTelCli_.id)));
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public BkTelCli getBkTelCliDefault(BkCli b,boolean d) {
        CriteriaBuilder builder =  getManager().getCriteriaBuilder();
        CriteriaQuery<BkTelCli> cq = builder.createQuery(BkTelCli.class);
        Root<BkTelCli> bkTelRoot = cq.from(BkTelCli.class);
        cq.where(builder.and(builder.equal(bkTelRoot.get(BkTelCli_.bkCli), b),
                builder.equal(bkTelRoot.get(BkTelCli_.pardefault), d)));
        cq.select(bkTelRoot).orderBy(builder.desc(bkTelRoot.get(BkTelCli_.id)));
        return getManager().createQuery(cq).getSingleResult();
    }

    @Override
    public List<BkTelCli> getListBkTelByCli() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
