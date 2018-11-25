/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.dao.impl;

import com.abouna.generic.dao.impl.GenericDao;
import com.abouna.lacussms.dao.IEmployeeDao;
import com.abouna.lacussms.entities.Employee;
import org.springframework.stereotype.Repository;

/**
 *
 * @author SATELLITE
 */
@Repository
public class EmployeeDaoImpl extends GenericDao<Employee, Integer> implements IEmployeeDao{
    
}
