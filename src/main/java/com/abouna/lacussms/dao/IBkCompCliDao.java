/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.dao;

import com.abouna.generic.dao.DataAccessException;
import com.abouna.generic.dao.IDao;
import com.abouna.lacussms.entities.BkCli;
import com.abouna.lacussms.entities.BkCompCli;
import java.util.List;

/**
 *
 * @author SATELLITE
 */
public interface IBkCompCliDao extends IDao<BkCompCli, String>{
    public List<BkCompCli> getBkCompCliByCli(BkCli cli) throws DataAccessException;
    public List<BkCompCli> getBkCompCliByCli(BkCli cli,boolean actif) throws DataAccessException;
    public List<BkCompCli> getBkCompCliByCli(BkCli cli,String compte,boolean actif) throws DataAccessException;
    public BkCompCli getBkCompCliByCriteria(BkCli cli,String compte,boolean actif);
    public List<BkCompCli> getBkCompCliByCli(String compte) throws DataAccessException;
    public List<BkCompCli> getBkCompCliLimit(int limit) throws DataAccessException;
    public BkCli getBkCli(String numc) throws DataAccessException;
}
