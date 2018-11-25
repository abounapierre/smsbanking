/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.dao.impl;

import com.abouna.generic.dao.impl.GenericDao;
import com.abouna.lacussms.dao.ICutOffDao;
import com.abouna.lacussms.entities.CutOff;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author SATELLITE
 */
@Repository
public class CutOffDaoImpl extends GenericDao<CutOff, Integer> implements ICutOffDao{

    @Override
    public List<CutOff> getCutOffs() {
        return getManager().
                createQuery("SELECT c FROM CutOff c ORDER BY id DESC").getResultList();
    }

    @Override
    public CutOff getLastCutOff(Date dateDebut) {
        List<CutOff> cutOffs = getManager().
                createQuery("SELECT c FROM CutOff c WHERE c.cutDate >= :dateDeb ORDER BY id DESC")
                .setParameter("dateDeb", dateDebut).setMaxResults(1).getResultList();
        CutOff cutOff = null;
        if(!cutOffs.isEmpty()){
            cutOff = cutOffs.get(0);
        }
        return cutOff;
    }
    
}
