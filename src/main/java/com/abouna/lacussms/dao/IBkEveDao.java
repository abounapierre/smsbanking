/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.dao;

import com.abouna.generic.dao.IDao;
import com.abouna.lacussms.entities.BkEve;
import com.abouna.lacussms.entities.TypeEvent;
import java.util.Date;
import java.util.List;

/**
 *
 * @author SATELLITE
 */
public interface IBkEveDao extends IDao<BkEve, Integer>{
    public List<BkEve> getBkEvesByEtat(String etat,Date date);
    public List<BkEve> getBkEveMaxDate();
    public List<BkEve> getBkEveByDate(Date date);
    public List<BkEve> getBkEveBySendParam(boolean send);
    //public List<BkEve> getBkEveBySendParam(boolean send);
    public Integer getMaxIndexBkEve();
    public Integer getMaxIndexBkEve(TypeEvent type);
    public List<BkEve> getBkEveByPeriode(Date d1,Date d2);
    public List<BkEve> getBkEveBySendParam(boolean send,List<String> list);
    public List<BkEve> getBkEveBySendParam(boolean send,List<String> list,TypeEvent type);
    public List<BkEve> getBkEveByCriteria(String code,String date,String compte);
    public List<BkEve> getBkEveByCriteria(String code,Date date,String compte);
    public List<BkEve> getBkEveByCriteria2(String code,Date date,String compte);
    public List<BkEve> getBkEveByCriteria(String code);
    public int supprimerParPeriode(String date1,String date2);
    public int supprimerParPeriode(Date date1,Date date2);
    public List<BkEve> getBkEveByCriteria(String date1,String date2);
    public List<BkEve> getBkEveByLimit(int limit);
    public List<BkEve> getBkEveByCriteria(String code,String compte,String heure,String montant);
    public List<BkEve> getBkEveByCriteriaMontant(String code,String compte,String montant);

    public List<BkEve> getBkEveByPeriode(String code, String compte, Date date1, Date date2);
    
}
