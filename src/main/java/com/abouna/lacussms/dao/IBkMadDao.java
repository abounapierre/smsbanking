/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.dao;

import com.abouna.generic.dao.IDao;
import com.abouna.lacussms.entities.BkMad;
import java.util.Date;
import java.util.List;

/**
 *
 * @author SATELLITE
 */
public interface IBkMadDao extends IDao<BkMad, Integer>{
    public List<BkMad> getBkMadsByPeriode(Date d1, Date d2);
    public int supprimerBkMad(Date d1, Date d2);
    public List<BkMad> getbkMadsByCriteria(String val);
    public BkMad getBkMadByCriteria(String num,String ad1p,Date date);
    public Integer getMaxBkMad();
    public void supprimerAll();
    public BkMad getBkMadByClesec(String num);
    public List<BkMad> getBkMadByTraite(int traite);
    public List<BkMad> getBkMadByTraite();
    public List<BkMad> getBkMadByLimit(int limit);
}
