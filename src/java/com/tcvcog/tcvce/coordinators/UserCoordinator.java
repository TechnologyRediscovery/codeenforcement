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
import com.tcvcog.tcvce.domain.IntegrationException;
import java.sql.Connection;
import java.io.Serializable;
import com.tcvcog.tcvce.domain.ObjectNotFoundException;
import com.tcvcog.tcvce.entities.CECase;
import com.tcvcog.tcvce.entities.Property;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import com.tcvcog.tcvce.entities.User;
import com.tcvcog.tcvce.integration.UserIntegrator;
import javax.faces.application.FacesMessage;

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
     * @throws com.tcvcog.tcvce.domain.IntegrationException
     */
    public User getUser(String loginName, String loginPassword) 
            throws ObjectNotFoundException, DataStoreException, IntegrationException {
        System.out.println("UserCoordinator.geUser | given: " + loginName + " " + loginPassword);
        con = getPostgresCon();
        User authenticatedUser = null;
        UserIntegrator ui = getUserIntegrator();
        
        authenticatedUser = ui.getAuthenticatedUser(loginName, loginPassword);
        if (authenticatedUser != null){
            
            getSessionBean().setActiveUser(authenticatedUser);
            System.out.println("UserCoordinator.getUser | default code set: " 
                    + authenticatedUser.getDefaultCodeSet().getCodeSetID());
            getSessionBean().setActiveCodeSet(authenticatedUser.getDefaultCodeSet());
        }
         
        return authenticatedUser;
        
    } // close getUser()
    
    public User getUser(String loginName) throws IntegrationException{
        System.out.println("UserCoordinator.geUser | given: " + loginName );
        User authenticatedUser;
        UserIntegrator ui = getUserIntegrator();
                con = getPostgresCon();
        authenticatedUser = ui.getUser(loginName);
        return authenticatedUser;
    }
    
} // close class
