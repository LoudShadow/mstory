package com.group1.mstory.connectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import org.springframework.stereotype.Component;

@Component
public class JdbcConnector {
    private String connectionUrl = "jdbc:mysql://192.168.1.106:3310/MStorey";
    private String connectionUser = "root";
    private String connectionPassword = "password";
    private Connection connection = null;
    private PreparedStatement ps = null;

    public JdbcConnector()
    {
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

    public PreparedStatement prepareStatement(String query) throws SQLException{
        return connection.prepareStatement(query);
    }

    public ResultSet prepareAndExecuteQuery(String query) throws SQLException{
        ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    // This must have the id column named as "id"
    public int prepareExecuteReturnId(String query) throws SQLException{
        ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt("id");
    }

    public void prepareAndExecuteUpdate(String query) throws SQLException {
        ps = connection.prepareStatement(query);
        ps.executeUpdate();
    }

    public int getId(String query) throws SQLException{
        ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int id = rs.getInt("id");
        return id;
    }

    public void setConnectionUrl(String newUlr){
        this.connectionUrl = newUlr;
    }

    public String getConnectionUrl(){
        return this.connectionUrl;
    }

    public int getLastInsertId(){
        String sql = "SELECT LAST_INSERT_ID() AS id";
        try{
            ResultSet rs = prepareAndExecuteQuery(sql);
            rs.next();
            return rs.getInt("id");
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return -1;

    }



}
