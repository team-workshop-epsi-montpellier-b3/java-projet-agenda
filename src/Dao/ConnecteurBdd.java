/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Maxime
 */
public class ConnecteurBdd {

    Connection connexion = null;
    Statement statement = null;
    ResultSet resultat = null;
    boolean flag = false;

    public ConnecteurBdd(String log, String pwd) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        ConnexionBdd(log, pwd);
    }

    public boolean ConnexionBdd(String utilisateur, String motDePasse) throws SQLException {
        
        String url = "jdbc:mysql://localhost:3306/maserre";
        connexion = (Connection) DriverManager.getConnection(url, utilisateur, motDePasse);
        statement = (Statement) connexion.createStatement();
        return false;
    }

    public ResultSet lire(String requete) throws SQLException {
        resultat = statement.executeQuery(requete);
        return resultat;
    }

    public boolean ecrire(String requete) throws SQLException {
        return statement.execute(requete);
    }
    
    public ConnecteurBdd() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        ConnexionBdd("root", "");
    }
    
    
    
    
    
    
    
    
    
}
