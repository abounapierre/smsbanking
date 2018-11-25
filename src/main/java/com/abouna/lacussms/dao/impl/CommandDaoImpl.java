/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.dao.impl;

import com.abouna.generic.dao.impl.GenericDao;
import com.abouna.lacussms.dao.ICommandDao;
import com.abouna.lacussms.entities.Command;
import com.abouna.lacussms.entities.Status;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author SATELLITE
 */
@Repository
public class CommandDaoImpl extends GenericDao<Command, Integer>implements ICommandDao{

    @Override
    public List<Command> getCommandByStatus(Status status) {
        return getManager().createQuery("SELECT c FROM Command c WHERE c.status = :status")
                .setParameter("status", status).getResultList();
    }

    @Override
    public List<Command> getCommandByDate(Date dateDebut, Date dateFin, Status status) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT c FROM Command c WHERE c.processedDate >= : ddebut ");
        builder.append("AND c.status = :status");
        return getManager().createQuery(builder.toString()).setParameter("ddebut", dateDebut)
                .setParameter("status", status).getResultList();
    }
    
}
