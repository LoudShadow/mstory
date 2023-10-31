package com.group1.mstory.connectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:db.properties")
public class JdbcConnector {
    @Autowired
    Environment env;

    Logger logger = LogManager.getLogger(JdbcConnector.class);

    private Connection connection = null;
    private PreparedStatement ps = null;

    @Bean
    public Boolean beginConnection(){  
        logger.info("Connecting to database..."); 
        String connectionUrl = env.getProperty("spring.datasource.url");
        String connectionUser = env.getProperty("spring.datasource.username");
        String connectionPassword = env.getProperty("spring.datasource.password");  
        try {
            this.connection = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
            return true;
        } catch (Exception E){
            return false;
        }
    }

    public PreparedStatement prepareStatement(String query) throws SQLException{
        return connection.prepareStatement(query);
    }

    public ResultSet prepareAndExecuteQuery(String query) throws SQLException{
        ps = connection.prepareStatement(query);
        return ps.executeQuery();
    }

    public void prepareAndExecuteUpdate(String query) throws SQLException {
        ps = connection.prepareStatement(query);
        ps.executeUpdate();
    }

    public int getId(String query) throws SQLException{
        ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt("id");
    }

    public Connection getConnection(){
        return this.connection;
    }

    public int getLastInsertId() throws SQLException{
        String sql = "SELECT LAST_INSERT_ID() AS id";
        ResultSet rs = prepareAndExecuteQuery(sql);
        rs.next();
        return rs.getInt("id");
    }



}
