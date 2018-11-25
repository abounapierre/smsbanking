/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.dao.impl;

import com.abouna.generic.dao.impl.GenericDao;
import com.abouna.lacussms.dao.ILicenceDao;
import com.abouna.lacussms.entities.Licence;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author SATELLITE
 */
@Repository
public class LicenceDaoImpl extends GenericDao<Licence, Integer> implements ILicenceDao{

    @Override
    public boolean vider() {
        try {
            Query q1 = getManager().createQuery("DELETE FROM Licence");
            q1.executeUpdate();
            return true;
        } catch (Exception ex) {
            return false;
        }

    }
    
}
