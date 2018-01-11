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

import com.tcvcog.tcvce.entities.EnforcableCodeElement;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author sylvia
 */

public class CodeSetElementBB extends BackingBeanUtils implements Serializable{

    /**
     * Creates a new instance of CodeSetElementBB
     */
    public CodeSetElementBB() {
    }
    
    @ManagedProperty("#{sessionManager}")
    private SessionManager sessionManager;
    
    private EnforcableCodeElement selectedEce;

    /**
     * @return the sessionManager
     */
    public SessionManager getSessionManager() {
        return sessionManager;
    }

    /**
     * @param sessionManager the sessionManager to set
     */
    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    /**
     * @return the selectedEce
     */
    public EnforcableCodeElement getSelectedEce() {
        return selectedEce;
    }

    /**
     * @param selectedEce the selectedEce to set
     */
    public void setSelectedEce(EnforcableCodeElement selectedEce) {
        this.selectedEce = selectedEce;
    }
    
    
    
}
