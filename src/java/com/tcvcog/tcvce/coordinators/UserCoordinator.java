/*
 * Copyright (C) 2017 cedba
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.tcvcog.tcvce.coordinators;

import com.tcvcog.tcvce.application.BackingBeanUtils;
import com.tcvcog.tcvce.domain.DataStoreException;
import com.tcvcog.tcvce.domain.ObjectNotFoundException;
import com.tcvcog.tcvce.entities.Property;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import org.postgresql.jdbc3.Jdbc3PoolingDataSource;
import java.sql.SQLException;
import java.io.Serializable;
import com.tcvcog.tcvce.domain.ObjectNotFoundException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import com.tcvcog.tcvce.entities.User;
import com.tcvcog.tcvce.integration.PostgresConnectionFactory;
import com.tcvcog.tcvce.util.Constants;

/**
 *
 * @author cedba
 */
@ApplicationScoped
@Named("userCoordinator")
public class UserCoordinator extends BackingBeanUtils implements Serializable {

     Connection con = null;
    
    /**
     * Creates a new instance of UserCoordinator
     */
    public UserCoordinator(){
        System.out.println("Initializing userCoordinator class");
        
    }
    
    /**
     * Responds to login requests by taking the loginName and loginPassword
     * and searching the database for a registered user. If a user is found
     * in the DB, a User object is created and returned, allow the user to progress 
     * pass the authentication screen.
     * 
     * @param loginName the login name entered by the user
     * @param loginPassword the password entered by the user
     * 
     * @return a User object loaded up with various attributes of the user, 
     * including the user's role in the system.
     * 
     * @throws com.tcvcog.tcvce.domain.ObjectNotFoundException
     * @throws com.tcvcog.tcvce.domain.DataStoreException
     */
    public User getUser(String loginName, String loginPassword) throws ObjectNotFoundException, DataStoreException {
        System.out.println("Inside getUser method inside Usercoordinator Class");
        con = getPostgresCon();
        
        
         String query = "SELECT username, password FROM login"
                + " WHERE username='"+ loginName + "' AND password='" + loginPassword +"';";
        ResultSet rs;
        
        // login is successful if the result set has any rows in it
        // TODO: create value comparison check as a backup to avoid SQL injection risks
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            
            // ACCESS CONTROL: ONLY CREATE USER IF THE USER EXISTS IN THE SYSTEM
            if(rs.next()){
                // TODO: Don't construct a User here--figure out how to
                // get the mbcf to work
                
                User user = new User();
                
            
                con.close();
                return user;
            } else {
                throw new ObjectNotFoundException("No User found with those credentials. Try again, please.");
            }
            
   
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return null;
        
    } // close getUser()
    
    
} // close class
