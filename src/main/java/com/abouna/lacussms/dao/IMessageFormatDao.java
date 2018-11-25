/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.dao;

import com.abouna.generic.dao.IDao;
import com.abouna.lacussms.entities.BkOpe;
import com.abouna.lacussms.entities.MessageFormat;
import java.util.List;

/**
 *
 * @author SATELLITE
 */
public interface IMessageFormatDao extends IDao<MessageFormat, Integer>{
   public List<MessageFormat> getMessageFormatByOpe(BkOpe bkOpe);
   public MessageFormat getMessageFormatByOpe(BkOpe bkOpe,String langue);
}
