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

import com.tcvcog.tcvce.coordinators.SessionCoordinator;
import com.tcvcog.tcvce.coordinators.UserCoordinator;
import com.tcvcog.tcvce.domain.DataStoreException;
import com.tcvcog.tcvce.domain.IntegrationException;
import com.tcvcog.tcvce.domain.ObjectNotFoundException;
import com.tcvcog.tcvce.entities.User;
import java.io.Serializable;
import java.util.Enumeration;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Eric C. Darsow
 */
public class MissionControlBB extends BackingBeanUtils implements Serializable {

  
    
    @ManagedProperty(value="#{SessionBean}")
    private SessionBean sessionBean;
    private User user;
    
    /**
     * Creates a new instance of InitiateSessionBB
     */
    public MissionControlBB() {
    }
    
    
   
    
    public String jumpToPublicPortal(){
        return "publicPortal";
    }
    
    public String loginToMissionControl(){
        
        return "startInitiationProcess";
    }
    
    public String logout(){
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
            System.out.println("MissionControlBB.logout | Session invalidated");

        } else {
            FacesContext facesContext = getFacesContext();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "ERROR: Unable to invalidate session.", "Your system administrator has been notified."));
        }
            return "logoutSequenceComplete";
    }

    /**
     * @return the sessionBean
     */
    public SessionBean getSessionBean() {
        return sessionBean;
    }

    /**
     * @param sessionBean the sessionBean to set
     */
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     * @return the user
     */
    public User getUser() {
        ExternalContext ec = getFacesContext().getExternalContext();
        user = (User) ec.getSessionMap().get("facesUser");
        System.out.println("MissionControlBB.getUser | facesUser: " + user.getFName());
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }
    
    

   
}
