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
package com.tcvcog.tcvce.application;

import com.tcvcog.tcvce.coordinators.UserCoordinator;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import com.tcvcog.tcvce.entities.User;

import com.tcvcog.tcvce.domain.*;
import com.tcvcog.tcvce.entities.CodeSet;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author cedba
 */
public class AuthenticationBB extends BackingBeanUtils implements Serializable{
    
   
    private String loginName;
    private String loginPassword;
    
//    Commented out for testing of session-scoped beans and getting login
    // system down to something manageable
//    @ManagedProperty("#{sessionManager}")
//    private SessionManager sessionManager;

    /**
     * Creates a new instance of Authentication
     */
    public AuthenticationBB() {
        System.out.println("Creating Authentication Bean");
        
    }
    
    public String login() {
        System.out.println("AuthenticationBB.login");
        User newUser = null;
        FacesContext facesContext = getFacesContext();
        UserCoordinator uc = getUserCoordinator();
        System.out.println("AuthenticationBB.login | userCoordinator: " + uc);
        
        try {
            newUser = uc.getUser(loginName, loginPassword);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Good morning, " + newUser.getFName() + "!", ""));
            
        } catch(ObjectNotFoundException e){
            
            System.out.println(e);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Incorrect Username or Password; Please try again", 
                    "If problem persists, please verify your inputs with the issuer of your credentials"));
            return "failure";
            
        } catch(DataStoreException e){
            
            System.out.println(e);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, 
                    "Database access error--not a username/password issue", 
                    "Please contact system administrator Eric Darsow: 412.923.9907"));
            return "failure";
            
        } catch (IntegrationException ex) {
            System.out.println(ex);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, 
                    "Integration module error. Unable to authenticate users.", 
                    "Please contact system administrator Eric Darsow: 412.923.9907"));
            return "failure";
        }
        // creating and setting the new session manager who lives in BeackingBeanUtils
        setSessionManager(new SessionManager());
        
//        Visit visit = new Visit();
//        CodeSet cs = new CodeSet();
//        
//        cs.setCodeSetName("I have a name!");
//        visit.setActiveCodeSet(cs);
//        System.out.println("AuthenticationBB.login | setting active code set in session manager");
//        sessionManager.setVisit(visit);
//        
  
        return "success";
        
        
    }
    
    public String logout(){
        
        System.out.println("AuthenticationBB.logout");
        
        FacesContext context = getFacesContext();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        
        if (session != null) {

//            session.removeAttribute("dBConnection");
//            session.removeAttribute("codeCoordinator");
//            session.removeAttribute("codeIntegrator");
//            session.removeAttribute("municipalitygrator");
//            session.removeAttribute("personIntegrator");
//            session.removeAttribute("propertyIntegrator");
//            session.removeAttribute("cEActionRequestIntegrator");
//            session.removeAttribute("userIntegrator");
            session.invalidate();

            FacesContext facesContext = getFacesContext();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Logout Successful", ""));
            System.out.println("AuthenticationBB.logout | Session invalidated");

        } else {
            FacesContext facesContext = getFacesContext();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "ERROR: Unable to invalidate session.", "Your system administrator has been notified."));
        }
            return "logoutSequenceComplete";
    }
    

    /**
     * @return the loginName
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * @param loginName the loginName to set
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * @return the loginPassword
     */
    public String getLoginPassword() {
        return loginPassword;
    }

    /**
     * @param loginPassword the loginPassword to set
     */
    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

   
   
    
    
} // close class