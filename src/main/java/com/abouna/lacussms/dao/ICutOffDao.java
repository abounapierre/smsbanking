/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.dao;

import com.abouna.generic.dao.IDao;
import com.abouna.lacussms.entities.CutOff;
import java.util.Date;
import java.util.List;

/**
 *
 * @author SATELLITE
 */
public interface ICutOffDao extends IDao<CutOff, Integer>{
    public List<CutOff> getCutOffs();
    
    public CutOff getLastCutOff(Date dateDebut);
}
