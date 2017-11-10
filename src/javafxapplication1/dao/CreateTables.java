/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1.dao;

/**
 *
 * @author maxim
 */
public class CreateTables {
    public static String createUsers = ""
            + "CREATE TABLE USERS "
            + "(UserId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
            + "Login VARCHAR(40) NOT NULL, "
            + "Password VARCHAR(40) NOT NULL, "
            + "Name VARCHAR(40) NOT NULL, "
            + "FirstName VARCHAR(40) NOT NULL, "
            + "CONSTRAINT primary_key PRIMARY KEY (UserId))";
    
    public static final String createComments = ""
            + "CREATE TABLE COMMENTS "
            + "(CommentId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
            + "Comment INT NULL, "
            + "UserId INT NOT NULL, "
            + "EventId NOT NULL, "
            + "FirstName VARCHAR(40) NOT NULL, "
            + "CONSTRAINT primary_key PRIMARY KEY (CommentId))";
    
    public static final String createEvents = ""
            + "CREATE TABLE EVENTS "
            + "(EventId INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
            + "Title VARCHAR(40) NOT NULL, "
            + "Description VARCHAR(500) NULL, "
            + "DateStart TIMESTAMP NOT NULL, "
            + "DateEnd TIMESTAMP NOT NULL, "
            + "DateCreate TIMESTAMP NOT NULL, "
            + "CONSTRAINT primary_key PRIMARY KEY (EventId))";
    
    
    
}
