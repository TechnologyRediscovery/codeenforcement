/*
 * Copyright (C) 2018 Adam Gutonski 
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

import com.tcvcog.tcvce.application.BackingBeanUtils;
import com.tcvcog.tcvce.domain.IntegrationException;
import com.tcvcog.tcvce.entities.CEActionRequest;
import com.tcvcog.tcvce.entities.EventCategory;
import com.tcvcog.tcvce.entities.EventType;
import com.tcvcog.tcvce.occupancy.SpaceType;
import java.sql.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adam Gutonski
 */
public class SpaceTypeIntegrator extends BackingBeanUtils implements Serializable {
    
    public SpaceTypeIntegrator(){
        
    }
    
    
    public LinkedList<SpaceType> getSpaceTypeList() throws IntegrationException{

    String query = "SELECT spacetypeid, spacetitle, description" +
            "  FROM public.spacetype;";
    Connection con = getPostgresCon();
    ResultSet rs = null;
    PreparedStatement stmt = null;
    LinkedList<SpaceType> spaceTypeList = new LinkedList();

    try {

        stmt = con.prepareStatement(query);
        //stmt.setInt(1, catID);
        rs = stmt.executeQuery();
        System.out.println("SpaceTypeIntegrator.getSpaceTypeList| SQL: " + stmt.toString());

        while(rs.next()){
            //inspectableCodeElementList.add(getInspectionGuidelines(rs.getString("inspection_guidelines")));
            /* see project notes, I will have to figure out how to align the CodeElement
            property of the InspectableCodeElement to have it fit in to the rs (ResultSet)
            and get returned based on foreign key alignment between inspectablecodeleement
            and codeelements...
            */
            spaceTypeList.add(generateSpaceType(rs));
        }

    } catch (SQLException ex) {
        System.out.println(ex.toString());
        throw new IntegrationException("Cannot get space type", ex);

    } finally{
         if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
         if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
         if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
    } // close finally

    return spaceTypeList;
}
    
    private SpaceType generateSpaceType(ResultSet rs) throws IntegrationException{
     SpaceType newSpaceType = new SpaceType();


     try {
         newSpaceType.setSpaceTypeID(rs.getInt("spacetypeid"));
         newSpaceType.setSpaceTypeTitle(rs.getString("spacetitle"));
         newSpaceType.setSpaceTypeDescription(rs.getString("description"));
         //newIce.setHighImportance(rs.getBoolean("high_importance"));
         //newIce.setInspectableCodeElementId(rs.getInt("inspectablecodeelementid"));
         //java.sql.Timestamp s = rs.getTimestamp("date");
         //if(s != null){
         //    newIce.setIceDate(s.toLocalDateTime());
         //} else {
         //    newIce.setIceDate(null);
         //}
     } catch (SQLException ex) {
         System.out.println(ex.toString());
         throw new IntegrationException("Error generating space type from ResultSet", ex);
     }
      return newSpaceType;   

 }


}
