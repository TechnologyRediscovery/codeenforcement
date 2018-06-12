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

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sylvia
 */
public class SessionInfoBB extends BackingBeanUtils implements Serializable{

    private String sessionID;
    private String sessionUserName;
    
    /**
     * Creates a new instance of SessionInfoBB
     */
    public SessionInfoBB() {
    }

    /**
     * @return the sessionID
     */
    public String getSessionID() {
        FacesContext fc = getFacesContext();
        ExternalContext ec = fc.getExternalContext();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        Enumeration e = session.getAttributeNames();
        while (e.hasMoreElements())
        {
          String attr = (String)e.nextElement();
          System.out.println("      attr  = "+ attr);
          Object value = session.getValue(attr);
          System.out.println("      value = "+ value);
        }
        sessionID = session.getId();
        
        return sessionID;
    }

    /**
     * @return the sessionUserName
     */
    public String getSessionUserName() {
        FacesContext fc = getFacesContext();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        sessionUserName = request.getRemoteUser();
        return sessionUserName;
    }

    /**
     * @param sessionID the sessionID to set
     */
    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    /**
     * @param sessionUserName the sessionUserName to set
     */
    public void setSessionUserName(String sessionUserName) {
        this.sessionUserName = sessionUserName;
    }
    
    
    
}
