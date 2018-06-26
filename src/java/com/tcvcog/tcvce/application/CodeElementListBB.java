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
import com.tcvcog.tcvce.entities.CodeSet;
import com.tcvcog.tcvce.entities.CodeSource;
import com.tcvcog.tcvce.integration.CodeIntegrator;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Eric C. Darsow
 */
public class CodeElementListBB extends BackingBeanUtils implements Serializable {

    /**
     * Creates a new instance of CodeElementListBB
     */
    public CodeElementListBB() {
        System.out.println("CodeElementListBB.CodeElementListBB | const");
    }
    
    private CodeElement selectedElement;
    private ArrayList<CodeElement> codeElementList;
    
    private CodeSource activeCodeSource;
    
    
    public String updateSelectedCodeElement(){
        System.out.println("CodeElementListBB.moveToUpdateCodeElement | selectedElement: " + selectedElement.getOrdchapterTitle());
        if(selectedElement != null){
            getSessionBean().setActiveCodeElement(selectedElement);
            return "codeElementUpdate";
            
        } else {
            getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Hark--No elemented selected. Please click on a code element first.", ""));
            return null;
        }
        
        
    }
    
   
    
    public void deleteCodeElement(ActionEvent event){
        
        
    }
    
    

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
    public ArrayList<CodeElement> getCodeElementList() {
        
        CodeSource source = getSessionBean().getActiveCodeSource();
        CodeIntegrator codeIntegrator = getCodeIntegrator();
        try {
            codeElementList = codeIntegrator.getCodeElements(source.getSourceID());
        } catch (IntegrationException ex) {
            getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Unable to populate list of code elements, sorry!", 
                            "This error will require administrator attention"));
        }
        
        return codeElementList;
    }

    /**
     * @param codeElementList the codeElementList to set
     */
    public void setCodeElementList(ArrayList<CodeElement> codeElementList) {
        this.codeElementList = codeElementList;
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

}
