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
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.Serializable;
import com.tcvcog.tcvce.domain.ObjectNotFoundException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import com.tcvcog.tcvce.entities.User;
import com.tcvcog.tcvce.integration.UserIntegrator;

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
        System.out.println("UserCoordinator.geUser | given: " + loginName + " " + loginPassword);
        con = getPostgresCon();
        
        UserIntegrator ui = getUserIntegrator();
        
        
         
        return null;
        
    } // close getUser()
    
    
} // close class
