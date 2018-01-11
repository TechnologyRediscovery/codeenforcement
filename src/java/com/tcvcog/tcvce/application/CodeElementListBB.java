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

import com.tcvcog.tcvce.domain.IntegrationException;
import com.tcvcog.tcvce.entities.CodeElement;
import com.tcvcog.tcvce.entities.CodeSource;
import com.tcvcog.tcvce.integration.CodeIntegrator;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;

/**
 *
 * @author sylvia
 */
public class CodeElementListBB extends BackingBeanUtils implements Serializable {

    /**
     * Creates a new instance of CodeElementListBB
     */
    public CodeElementListBB() {
    }
    
    private CodeElement selectedElement;
    private LinkedList<CodeElement> codeElementList;

    /**
     * @return the selectedElement
     */
    public CodeElement getSelectedElement() {
        return selectedElement;
    }

    /**
     * @param selectedElement the selectedElement to set
     */
    public void setSelectedElement(CodeElement selectedElement) {
        this.selectedElement = selectedElement;
    }

    /**
     * @return the codeElementList
     */
    public LinkedList<CodeElement> getCodeElementList() {
        SessionManager sm = getSessionManager();
        CodeSource source = sm.getActiveCodeSource();
        CodeIntegrator codeIntegrator = getCodeIntegrator();
        try {
            codeElementList = codeIntegrator.getCodeElementsBySourceID(source.getSourceID());
        } catch (IntegrationException ex) {
            getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Unable to populate list of code elements", 
                            "This error will require administrator attention"));
            
        }
        
        return codeElementList;
    }

    /**
     * @param codeElementList the codeElementList to set
     */
    public void setCodeElementList(LinkedList<CodeElement> codeElementList) {
        this.codeElementList = codeElementList;
    }
    
    
}
