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
package com.tcvcog.tcvce.util;

import com.tcvcog.tcvce.entities.EventCategory;
import com.tcvcog.tcvce.entities.Municipality;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Converter for Municipality objects
 * @author Eric Darsow
 */

@FacesConverter(forClass=Municipality.class, value="muniConverter")
public class MuniConverter extends EntityConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String titleS) {
        System.out.println("MunicipalityConverter.getAsObject | titleS: " + titleS);
        if(titleS.isEmpty()) {
            return null; 
        }
        
        Municipality m = (Municipality) this.getViewMap(fc).get(titleS);
               
        return m;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        System.out.println("MunicipalityConverter.getAsString");
        
        if (o == null){
            return "";
        }
        
        Municipality m = (Municipality) o;
        String title = m.getMuniName();
        if (title != null){
            this.getViewMap(fc).put(title,o);
            System.out.println("MunicipalityConverter.getAsString | putKey: " + title);
            System.out.println("MunicipalityConverter.getAsString | putValue: " + o.toString());
            
            return title;
            
        } else {
            return "category error";
        }
               
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
