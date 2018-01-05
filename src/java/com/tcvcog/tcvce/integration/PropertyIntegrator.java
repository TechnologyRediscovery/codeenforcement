/*
 * Copyright (C) 2017 Turtle Creek Valley Council of Governments
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
package com.tcvcog.tcvce.integration;

import com.tcvcog.tcvce.entities.Property;
import com.tcvcog.tcvce.application.BackingBeanUtils;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

/**
 *
 * @author Eric Darsow
 */
public class PropertyIntegrator extends BackingBeanUtils implements Serializable {
    

    /**
     * Creates a new instance of PropertyIntegrator
     */
    public PropertyIntegrator() {
        System.out.println("PropertyIntegrator.constructor");
    }
    
    public LinkedList<Property> searchForProperties(String addrPart, int muniID){
        
        
        System.out.println("PropertyIntegrator.searchForProperties");
        
        
        
        String query = "SELECT propertyid, parid, address FROM property"
                + " WHERE address LIKE '%" + addrPart + "%' AND municipality_municipalityID=" 
                + muniID + ";";
        System.out.println("PropertyIntegrator.searchForProperties - query: " + query);
        
        Connection con = getPostgresCon();
        ResultSet rs = null;
            
        LinkedList<Property> propList = new LinkedList<>();
 
 
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()){
                Property p = new Property();
                p.setPropertyID(rs.getInt("propertyid"));
                p.setParID(rs.getString("parid"));
                p.setAddress(rs.getString("address"));
                propList.add(p);
                System.out.println("PropertyIntegrator.searchForProperties - con close");
                con.close();
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } finally{
             if (rs != null) {
                try {
                    System.out.println("PropertyIntegrator.searchForProperties - con close");
                    rs.close();
                } catch (SQLException e) { /* ignored */}
             }
            if (con != null) {
                try {
                    System.out.println("PropertyIntegrator.searchForProperties - con close");
                    con.close();
                } catch (SQLException e) { /* ignored */}
             }
        } // close finally
        
        return propList;
        
    }
    
    
    
}
