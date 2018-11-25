/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.dao.impl;

import com.abouna.generic.dao.impl.GenericDao;
import com.abouna.lacussms.dao.HolidayDao;
import com.abouna.lacussms.entities.Holiday;
import com.abouna.lacussms.entities.Holiday_;
import java.io.Serializable;
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
public class HolidayDaoImpl extends GenericDao<Holiday, Integer> implements HolidayDao{

    @Override
    public List<Holiday> getHolidaysByDate(String date) {
       CriteriaBuilder builder =  getManager().getCriteriaBuilder();
        CriteriaQuery<Holiday> cq = builder.createQuery(Holiday.class);
        Root<Holiday> bkEveRoot = cq.from(Holiday.class);
        cq.where(builder.equal(bkEveRoot.get(Holiday_.hDate), date));
        cq.select(bkEveRoot);
        return getManager().createQuery(cq).getResultList();
    }
    
}
