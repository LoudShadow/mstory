package com.group1.mstory.connectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class JdbcConnector {

    private String connectionUrl = "";
    private String connectionPassword = "";
    private String connectionUser = "";
    private Connection connection = null;
    private PreparedStatement ps = null;

    public JdbcConnector(String connectionUrl, 
        String connectionUser, 
        String connectionPassword)
    {
        this.connectionUrl = connectionUrl;
        this.connectionPassword = connectionPassword;
        this.connectionUser = connectionUser;
        beginConnection();
    }


    public Boolean beginConnection(){
        try {
            this.connection = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
            return true;
        } catch (Exception E){
            E.printStackTrace();
            return false;
        }
    }

    public ResultSet prepareAndExecuteStatement(String query) throws SQLException{
        ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        return rs;
    }


    public void setConnectionUrl(String newUlr){
        this.connectionUrl = newUlr;
    }

    public String getConnectionUrl(){
        return this.connectionUrl;
    }



}
