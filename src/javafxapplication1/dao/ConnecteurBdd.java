/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1.dao;


import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maxime
 */
public class ConnecteurBdd {
    String dbName = "derbyDB";
    String framework = "embedded";
    String protocol = "jdbc:derby:";
    
    Properties props = new Properties();
    String userName = "user1";
    String userPw = "user1";
    
    public ArrayList<java.sql.Statement> statements = new ArrayList<java.sql.Statement>(); 
    PreparedStatement psInsert;
    PreparedStatement psUpdate;
    java.sql.Statement s;
    ResultSet rs = null;
    Connection conn = null;
    Connection connexion = null;
    Statement statement = null;
    ResultSet resultat = null;
    boolean flag = false;

    public ConnecteurBdd(){
//        Class.forName("com.mysql.jdbc.Driver");
//        ConnexionBdd("root", "");
        props.put("user", userName);
        props.put("password", userName);
        firstConnexionBdd();
    }

    void connecterBdd(){
        try {
            System.out.println("Connexion en mode " + framework + ".");
            // create=true => créer la base si elle n'existe pas.
            conn = DriverManager.getConnection(protocol + dbName + ";create=true", props);
            System.out.println("Connecté à la base " + dbName + ".");
        } catch (SQLException ex) {
            Logger.getLogger(ConnecteurBdd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void deconnecterBdd(){
        if (framework.equals("embedded")){
            try{
                // Pour arrêter Derby.
                DriverManager.getConnection("jdbc:derby:;shutdown=true");

                // Pour fermer une base en particulier:
                //DriverManager.getConnection("jdbc:derby:" + dbName + ";shutdown=true");

            }catch (SQLException se){
                if (( (se.getErrorCode() == 50000)
                        && ("XJ015".equals(se.getSQLState()) ))) {
                    // we got the expected exception
                    System.out.println("Derby shut down normally");
                    // Note that for single database shutdown, the expected
                    // SQL state is "08006", and the error code is 45000.
                } else {
                    // if the error code or SQLState is different, we have
                    // an unexpected exception (shutdown failed)
                    System.err.println("Derby did not shut down normally: " + se);
                }
            } finally {
                // release all open resources to avoid unnecessary memory usage

                // ResultSet
                try {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                } catch (SQLException sqle) {
                    System.err.println(sqle);
                }

                // Statements and PreparedStatements
                int i = 0;
                while (!statements.isEmpty()) {
                    // PreparedStatement extend Statement
                    java.sql.Statement st = (java.sql.Statement)statements.remove(i);
                    try {
                        if (st != null) {
                            st.close();
                            st = null;
                        }
                    } catch (SQLException sqle) {
                        System.err.println(sqle);
                    }
                }

                //Connection
                try {
                    if (conn != null) {
                        conn.close();
                        conn = null;
                    }
                } catch (SQLException sqle) {
                    System.err.println(sqle);
                }
            }
        }
    }
    
    void firstConnexionBdd(){
        connecterBdd();
        if(!checkTables()){
            creerTables();
        }
        deconnecterBdd();
    }
    
    boolean checkTables(){
        // TODO: vérifier que TOUTES les tables existent avec les bonnes colonnes.
        boolean exists = false;
        try {
            DatabaseMetaData md = conn.getMetaData();
            rs = md.getTables(null, userName.toUpperCase(), "%", null);
            
            if(!rs.next()){
                System.out.println("Les tables de la base de données n'existent pas.");
                exists = false;
            }else{
                System.out.println("Les tables de la base de données existent.");
                exists = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnecteurBdd.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exists;
    }
    
    void creerTables(){
        try {
            s = conn.createStatement();
            statements.add(s);
            s.execute(CreateTables.createUsers);
            
            s = conn.createStatement();
            statements.add(s);
            s.execute(CreateTables.createEvents);
            
            s = conn.createStatement();
            statements.add(s);
            s.execute(CreateTables.createComments);
            
        } catch (SQLException ex) {
            Logger.getLogger(ConnecteurBdd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void deconnexionBdd(){
        if (framework.equals("embedded")){
            try{
                // Pour arrêter Derby.
                DriverManager.getConnection("jdbc:derby:;shutdown=true");

                // Pour fermer une base en particulier:
                //DriverManager.getConnection("jdbc:derby:" + dbName + ";shutdown=true");

            }catch (SQLException se){
                if (( (se.getErrorCode() == 50000)
                        && ("XJ015".equals(se.getSQLState()) ))) {
                    // we got the expected exception
                    System.out.println("Derby shut down normally");
                    // Note that for single database shutdown, the expected
                    // SQL state is "08006", and the error code is 45000.
                } else {
                    // if the error code or SQLState is different, we have
                    // an unexpected exception (shutdown failed)
                    System.err.println("Derby did not shut down normally: " + se);
                }
            } finally {
                // release all open resources to avoid unnecessary memory usage

                // ResultSet
                try {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                } catch (SQLException sqle) {
                    System.err.println(sqle);
                }

                // Statements and PreparedStatements
                int i = 0;
                while (!statements.isEmpty()) {
                    // PreparedStatement extend Statement
                    java.sql.Statement st = (java.sql.Statement)statements.remove(i);
                    try {
                        if (st != null) {
                            st.close();
                            st = null;
                        }
                    } catch (SQLException sqle) {
                        System.err.println(sqle);
                    }
                }

                //Connection
                try {
                    if (conn != null) {
                        conn.close();
                        conn = null;
                    }
                } catch (SQLException sqle) {
                    System.err.println(sqle);
                }
            }
        }
    }
    
    ResultSet lire(String requete) throws SQLException {
        connecterBdd();
        resultat = statement.executeQuery(requete);
        deconnecterBdd();
        
        return resultat;
    }

    boolean ecrire(String requete) throws SQLException {
        connecterBdd();
        boolean retour = statement.execute(requete);
        deconnecterBdd();
        
        return retour;
    }
    
    
//    public boolean ConnexionBdd(String utilisateur, String motDePasse) throws SQLException {
//        String url = "jdbc:mysql://localhost:3306/maserre";
//        connexion = (Connection) DriverManager.getConnection(url, utilisateur, motDePasse);
//        statement = (Statement) connexion.createStatement();
//        return false;
//    }
    
//    public ConnecteurBdd(String log, String pwd) throws ClassNotFoundException, SQLException {
//        
//        Class.forName("com.mysql.jdbc.Driver");
//        
//        ConnexionBdd(log, pwd);
//    }
    
}
