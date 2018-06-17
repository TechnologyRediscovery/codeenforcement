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
package com.tcvcog.tcvce.coordinators;

import com.tcvcog.tcvce.application.Visit;
import com.tcvcog.tcvce.entities.CodeSource;
import com.tcvcog.tcvce.entities.User;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author sylvia
 */

@SessionScoped
public class SessionCoordinator implements Serializable{

    /**
     * Creates a new instance of SessionManager
     */
    public SessionCoordinator() {
        System.out.println("SessionCoordinator.Constructor");
        // don't make this here!
//        visit = new Visit();
        
    }
    
    private Visit visit;
    private CodeSource activeCodeSource;
    private User utilityUserToUpdate;
    
    /**
     * @return the visit
     */
    public Visit getVisit() {
        return visit;
    }

    /**
     * @param visit the visit to set
     */
    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    /**
     * @return the activeCodeSource
     */
    public CodeSource getActiveCodeSource() {
        return activeCodeSource;
    }

    /**
     * @param activeCodeSource the activeCodeSource to set
     */
    public void setActiveCodeSource(CodeSource activeCodeSource) {
        this.activeCodeSource = activeCodeSource;
    }

   


    /**
     * @return the utilityUserToUpdate
     */
    public User getUtilityUserToUpdate() {
        return utilityUserToUpdate;
    }

    /**
     * @param utilityUserToUpdate the utilityUserToUpdate to set
     */
    public void setUtilityUserToUpdate(User utilityUserToUpdate) {
        this.utilityUserToUpdate = utilityUserToUpdate;
    }
    
}
