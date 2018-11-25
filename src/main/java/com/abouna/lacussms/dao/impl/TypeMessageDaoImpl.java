/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.dao.impl;

import com.abouna.generic.dao.impl.GenericDao;
import com.abouna.lacussms.dao.ITypeMessageDao;
import com.abouna.lacussms.entities.TypeMessage;
import org.springframework.stereotype.Repository;

/**
 *
 * @author SATELLITE
 */
@Repository
public class TypeMessageDaoImpl extends GenericDao<TypeMessage, Integer> implements ITypeMessageDao{
    
}
