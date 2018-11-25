/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.abouna.lacussms.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author SATELLITE
 */
public class OracleConnect {
    public Connection connection;
    public OracleConnect() throws ClassNotFoundException, SQLException{
       Class.forName("oracle.jdbc.driver.OracleDriver");
        connection = DriverManager.
                getConnection("jdbc:oracle:thin:@localhost:1521:SID","username","password");
    }
    
    public OracleConnect(String url,String user,String pwd) throws ClassNotFoundException, SQLException{
       Class.forName("oracle.jdbc.driver.OracleDriver");
        connection = DriverManager.
                getConnection(url,user,pwd);
    }
    
    //public boolean testConnexion()

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
