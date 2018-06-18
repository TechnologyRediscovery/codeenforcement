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
import com.tcvcog.tcvce.entities.CECase;
import com.tcvcog.tcvce.entities.Property;
import java.io.Serializable;
import javax.faces.application.FacesMessage;

/**
 *
 * @author Eric C. Darsow
 */
public class NavigationBB extends BackingBeanUtils implements Serializable {

    /**
     * Creates a new instance of NavigationBB
     */
    public NavigationBB() {
    }
    
    public String gotoPropertyProfile(){
        if(hasActiveProperty()){
            return "propertySearch";
        } else return "propertyProfile";
    }
    
    public String gotoCaseProfile(){
        if(hasActiveCase()){
            return "caseManage";
        } else{
            
             getFacesContext().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "No active case! Please select a case from the list below and re-attempt navigation",
                        ""));
            return "workflow";
        }
        
    }
    
    public String gotoCaseViolations(){
        if(hasActiveCase()){
            return "caseViolations";
        } else{
             getFacesContext().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "No active case! Please select a case from the list below and re-attempt navigation",
                        ""));
            return "workflow";
        }
    }
    
    public String gotoCaseNotices(){
        
        if(hasActiveCase()){
            return "caseNotices";
        } else return "workflow";
    }
    
    public String gotoCaseCitations(){
        if(hasActiveCase()){
            return "caseCitations";
        } else return "workflow";
        
    }
    
    public String gotoCaseEvents(){
        if(hasActiveCase()){
            return "caseEvents";
        } else return "workflow";
    }
    
    private boolean hasActiveCase(){
        SessionCoordinator sm = getSessionManager();
        CECase c = sm.getVisit().getActiveCase();
        if(c != null){
            return true;
        } 
        return false;
    }
    
    private boolean hasActiveProperty(){
        SessionCoordinator sm = getSessionManager();
        Property p = sm.getVisit().getActiveProp();
        if(p != null){
            return true;
        } 
        return false;
    }
    
    
    
    
    
    
    
}
