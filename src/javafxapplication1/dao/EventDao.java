/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafxapplication1.entity.Event;
import javafxapplication1.entity.User;

/**
 *
 * @author Guillaume
 */
public class EventDao {
    
    private final ConnecteurBdd connector;
   
    public EventDao() throws ClassNotFoundException, SQLException
    {
        this.connector = new ConnecteurBdd();
    }
    
    public void eventByUser(User u) throws SQLException 
    {
        ResultSet result = this.connector.lire(
                "SELECT *"
                + "FROM event"
                + "WHERE idLogin = " + u.getId()
        );
    }
        
    public void addEvent(User u, Event e) throws SQLException
    {
        this.connector.ecrire(
                 "INSERT INTO event (id_user, title, description, date_start, date_end, date_create)" +
                 "VALUES ("
                         + u.getId() +
                         ", " 
                         + e.getTitle() +
                         ", " 
                         + e.getDescription() +
                         ", " 
                         + e.getDateStart() +
                         ", " 
                         + e.getDateEnd() +
                          ", " 
                         + e.getDateCreate() +                    
                         ");"
        );
    }
    
}
