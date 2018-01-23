
import com.tcvcog.tcvce.application.BackingBeanUtils;
import com.tcvcog.tcvce.application.SessionManager;
import com.tcvcog.tcvce.entities.Property;
import java.io.Serializable;

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

/**
 *
 * @author sylvia
 */
public class PropertyProfileBB extends BackingBeanUtils implements Serializable{
    private Property currentProperty;

    /**
     * Creates a new instance of PropertyProfileBB
     */
    public PropertyProfileBB() {
    }
    
    
    public String createCase(){
        System.out.println("PropertyProfileBB.createCase");
        
        
        return "/ce/caseAdd.xhtml";
    }
    

    
    /**
     * @return the currentProperty
     */
    public Property getCurrentProperty() {
        SessionManager sm = getSessionManager();
        currentProperty = sm.getVisit().getActiveProp();
        return currentProperty;
    }

    /**
     * @param currentProperty the currentProperty to set
     */
    public void setCurrentProperty(Property currentProperty) {
        this.currentProperty = currentProperty;
    }
    
    public String updateProperty(){
        System.out.println("PropertyProfileBB.updateProperty");
        return "propertyUpdate";
        
    }
    
    
    
    
    
}
