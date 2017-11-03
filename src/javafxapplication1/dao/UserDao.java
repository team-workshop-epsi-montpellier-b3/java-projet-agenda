/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafxapplication1.entity.User;

/**
 *
 * @author Guillaume
 */
public class UserDao {
    
    private final ConnecteurBdd connector;
   
    public UserDao() throws ClassNotFoundException, SQLException
    {
        this.connector = new ConnecteurBdd();
    }
    
    public void Inscription(User u) throws SQLException 
    {
        this.connector.ecrire(
                 "INSERT INTO user (login, name, firstname)" +
                 "VALUES ("
                         + u.getLogin() +
                         ", " 
                         + u.getName() +
                         ", " 
                         + u.getFirstName() +
                         ");"
        );
    }
    
    public void Connexion(User u) throws SQLException 
    {
        ResultSet result = this.connector.lire(
                "SELECT *"
                + "FROM user"
                + "WHERE login = " + u.getLogin()
                + "AND password = " + u.getPassword()
        );
    }

    
}
