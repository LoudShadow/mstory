package com.group1.mstory.connectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Configuration
@PropertySource("classpath:application.properties")
public class JdbcConnector {
    @Autowired
    Environment env;

    private Connection connection = null;
    private PreparedStatement ps = null;

    public JdbcConnector()
    {
        // beginConnection();
    }

    @Bean
    public Boolean beginConnection(){   
        System.out.println("Connecting to database..."); 
        String connectionUrl = env.getProperty("spring.datasource.url");
        String connectionUser = env.getProperty("spring.datasource.username");
        String connectionPassword = env.getProperty("spring.datasource.password");  
        System.out.println(connectionUrl);
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

    // public void setConnectionUrl(String newUlr){
    //     this.connectionUrl = newUlr;
    // }

    // public String getConnectionUrl(){
    //     return this.connectionUrl;
    // }

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
