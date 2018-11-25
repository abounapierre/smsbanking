/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.dao.impl;

import com.abouna.generic.dao.impl.GenericDao;
import com.abouna.lacussms.dao.IRemoteDBDao;
import com.abouna.lacussms.entities.RemoteDB;
import com.abouna.lacussms.entities.RemoteDB_;
import com.abouna.lacussms.main.App;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 *
 * @author SATELLITE
 */
@Repository
public class RemoteDBDaoImpl extends GenericDao<RemoteDB, Integer> implements IRemoteDBDao{
    final static Logger logger = Logger.getLogger(RemoteDBDaoImpl.class);

    @Override
    public RemoteDB getDefaultRemoteDB(boolean b) {
        try {
            CriteriaBuilder builder = getManager().getCriteriaBuilder();
            CriteriaQuery<RemoteDB> cq = builder.createQuery(RemoteDB.class);
            Root<RemoteDB> remoteRoot = cq.from(RemoteDB.class);
            cq.where(builder.equal(remoteRoot.get(RemoteDB_.parDefault), b));
            cq.select(remoteRoot);
            return getManager().createQuery(cq).getSingleResult();
        } catch (NoResultException ex) {
            logger.info("aucun resultat trouvé");
            return null;
        }
    }
    
}
