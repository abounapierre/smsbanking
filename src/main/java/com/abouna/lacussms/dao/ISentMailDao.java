/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.dao;

import com.abouna.generic.dao.IDao;
import com.abouna.lacussms.entities.SentMail;
import java.util.Date;
import java.util.List;

/**
 *
 * @author SATELLITE
 */
public interface ISentMailDao extends IDao<SentMail, Integer>{
    public List<SentMail> getMailByDate(Date d);
}
