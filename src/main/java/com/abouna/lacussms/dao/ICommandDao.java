/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.dao;

import com.abouna.generic.dao.IDao;
import com.abouna.lacussms.entities.Command;
import com.abouna.lacussms.entities.Status;
import java.util.Date;
import java.util.List;

/**
 *
 * @author SATELLITE
 */
public interface ICommandDao extends IDao<Command, Integer>{
    public List<Command> getCommandByStatus(Status status);
    
    public List<Command> getCommandByDate(Date dateDebut, Date dateFin, Status status);
}
