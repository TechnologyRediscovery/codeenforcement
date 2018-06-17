/*
 * Copyright (C) 2018 Turtle Creek Valley
Council of Governments, PA
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
import com.tcvcog.tcvce.domain.IntegrationException;
import com.tcvcog.tcvce.entities.User;
import java.io.Serializable;
import java.util.Enumeration;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sylvia
 */
public class SessionInitializer extends BackingBeanUtils implements Serializable {

    private String glassfishUser;
    private String sessionID;
    private User retrievedCOGUser;
    
    /**
     * Creates a new instance of SessionInitializer
     */
    public SessionInitializer() {
    }
    
     public String initiateInternalSession(){
        System.out.println("SessionInitializer.initiateInternalSession");
        FacesContext facesContext = getFacesContext();
        UserCoordinator uc = getUserCoordinator();
        
        try {
            User extractedUser = uc.getUser(getGlassfishUser());
            if(extractedUser != null){
                ExternalContext ec = facesContext.getExternalContext();
                ec.getSessionMap().put("facesUser", extractedUser);
                System.out.println("SessionInitializer.initiateInternalSession "
                        + "| facesUserFromDB: " + extractedUser.getLName());
            
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Good morning, " + extractedUser.getFName() + "!", ""));
            }
        
        } catch (IntegrationException ex) {
            System.out.println("SessionInitializer.intitiateInternalSession | error getting facesUser");
            System.out.println(ex);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, 
                    "Integration module error. Unable to connect your server user to the COG system user.", 
                    "Please contact system administrator Eric Darsow at 412.923.9907"));
            return "failure";
        }
        // creating and setting the new session manager who lives in BeackingBeanUtils
//        setSessionManager(new SessionCoordinator());
        
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

    /**
     * @return the glassfishUser
     */
    public String getGlassfishUser() {
        
        FacesContext fc = getFacesContext();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        String sessionUserName = request.getRemoteUser();
        System.out.println("SessionInitializer.getGlassfishUserName | " + sessionUserName);
        glassfishUser = sessionUserName;
        return glassfishUser;
    }

    /**
     * @return the sessionID
     */
    public String getSessionID() {
        
        FacesContext fc = getFacesContext();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
//        Enumeration e = session.getAttributeNames();
//        System.out.println("SessionInitailzier.getSessionID | Dumping lots of attrs");
//        while (e.hasMoreElements())
//        {
//          String attr = (String)e.nextElement();
//          System.out.println("      attr  = "+ attr);
//          Object value = session.getValue(attr);
//          System.out.println("      value = "+ value);
//        }
        sessionID = session.getId();
        return sessionID;
    }

    /**
     * @return the retrievedCOGUser
     */
    public User getRetrievedCOGUser() {
        return retrievedCOGUser;
    }

    /**
     * @param glassfishUser the glassfishUser to set
     */
    public void setGlassfishUser(String glassfishUser) {
        this.glassfishUser = glassfishUser;
    }

    /**
     * @param sessionID the sessionID to set
     */
    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    /**
     * @param retrievedCOGUser the retrievedCOGUser to set
     */
    public void setRetrievedCOGUser(User retrievedCOGUser) {
        this.retrievedCOGUser = retrievedCOGUser;
    }
    
}
