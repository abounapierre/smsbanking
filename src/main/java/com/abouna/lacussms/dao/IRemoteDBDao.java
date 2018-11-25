/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.dao;

import com.abouna.generic.dao.IDao;
import com.abouna.lacussms.entities.RemoteDB;

/**
 *
 * @author SATELLITE
 */
public interface IRemoteDBDao extends IDao<RemoteDB, Integer>{
    public RemoteDB getDefaultRemoteDB(boolean b);
}
