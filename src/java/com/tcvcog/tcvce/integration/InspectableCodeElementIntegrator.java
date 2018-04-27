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
import com.tcvcog.tcvce.occupancy.InspectableCodeElement;
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
public class InspectableCodeElementIntegrator extends BackingBeanUtils implements Serializable {
    
    public InspectableCodeElementIntegrator(){
        
    }

    public LinkedList<InspectableCodeElement> getInspectableCodeElementList() throws IntegrationException{

    String query = "SELECT inspectablecodelelementid, codeelementid, inspectionpriority, inspectiontips"
            + "  FROM public.inspectablecodelement";
    Connection con = getPostgresCon();
    ResultSet rs = null;
    PreparedStatement stmt = null;
    LinkedList<InspectableCodeElement> inspectableCodeElementList = new LinkedList();

    try {

        stmt = con.prepareStatement(query);
        //stmt.setInt(1, catID);
        rs = stmt.executeQuery();
        System.out.println("InspectableCodeElementIntegrator.getInspectableCodeElementList| SQL: " + stmt.toString());

        while(rs.next()){
            //inspectableCodeElementList.add(getInspectionGuidelines(rs.getString("inspection_guidelines")));
            /* see project notes, I will have to figure out how to align the CodeElement
            property of the InspectableCodeElement to have it fit in to the rs (ResultSet)
            and get returned based on foreign key alignment between inspectablecodeleement
            and codeelements...
            */
            inspectableCodeElementList.add(generateInspectablecodeElement(rs));
        }

    } catch (SQLException ex) {
        System.out.println(ex.toString());
        throw new IntegrationException("Cannot get event categry", ex);

    } finally{
         if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
         if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
         if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
    } // close finally

    return inspectableCodeElementList;
}

    
    
    public void insertInspectableCodeElement(InspectableCodeElement inspectableCodeElement) throws IntegrationException{
        String query = "INSERT INTO public.inspectablecodelement(\n" +
                "         inspectablecodelelementid, codeelementid, inspectionpriority, inspectiontips) \n" +
                "    VALUES (DEFAULT, ?, ?, ?)";
        
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement(query);
            //stmt.setInt(1, inspectableCodeElement.getInspectableCodeElementId());
            stmt.setInt(1, inspectableCodeElement.getCodeElementID());
            stmt.setBoolean(2, inspectableCodeElement.getInspectionPriority());
            stmt.setString(3, inspectableCodeElement.getInspectionTips());
            /*Previously had date as an attribute of ICE entity... 
            note that the timestamp is set by a call to postgres's now()
            //stmt.setInt(4, exercise.getLift_id());
            if(inspectableCodeElement.getIceDate() != null){
                //keep in  mind that we note place holder "4"' because it  points to the
                //fourth "?" symbol in our query...skips DEFAULT 
                stmt.setTimestamp(5, java.sql.Timestamp.valueOf(inspectableCodeElement.getIceDate()));
                
            } else {
                stmt.setNull(5, java.sql.Types.NULL);
            }
            */
            
            System.out.println("InspectableCodeElementIntegrator.insertInspectableCodeElement| sql: " + stmt.toString());
            stmt.execute();

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Cannot insert InspectableCodeElement", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
        
    }
    
    public void updateInspectableCodeElement (InspectableCodeElement ice) throws IntegrationException {
        String query = "UPDATE public.inspectablecodelement\n" +
                        "   SET codeelementid=?, inspectionpriority=?, \n" +
                        "       inspectiontips=?\n" +
                        " WHERE inspectablecodelelementid=?;";
        
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;
        
        try{
            stmt = con.prepareStatement(query);
            stmt.setInt(1, ice.getCodeElementID());
            stmt.setBoolean(2, ice.getInspectionPriority());
            stmt.setString(3, ice.getInspectionTips());
            stmt.setInt(4, ice.getInspectableCodeElementId());
            System.out.println("TRYING TO EXECUTE UPDATE METHOD");
            stmt.executeUpdate();
        } catch (SQLException ex){
            System.out.println(ex.toString());
            throw new IntegrationException("Unable to update Inspectable Code Element", ex);
        } finally {
            if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
            if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        }
        
    }
    
    public void deleteInspectableCodeElement(InspectableCodeElement ice) throws IntegrationException {
         String query = "DELETE FROM public.inspectablecodelement\n" +
                        " WHERE inspectablecodelelementid=?;";
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(query);
            stmt.setInt(1, ice.getInspectableCodeElementId());
            stmt.execute();

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Cannot delete inspectable code element--probably because another"
                    + "part of the database has a reference item.", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
    }
    
    private InspectableCodeElement generateInspectablecodeElement(ResultSet rs) throws IntegrationException{
        InspectableCodeElement newIce = new InspectableCodeElement();
        
        
        try {
            newIce.setCodeElementID(rs.getInt("codeelementid"));
            newIce.setInspectionTips(rs.getString("inspectiontips"));
            newIce.setInspectionPriority(rs.getBoolean("inspectionpriority"));
            //newIce.setHighImportance(rs.getBoolean("high_importance"));
            newIce.setInspectableCodeElementId(rs.getInt("inspectablecodelelementid"));
            //java.sql.Timestamp s = rs.getTimestamp("date");
            //if(s != null){
            //    newIce.setIceDate(s.toLocalDateTime());
            //} else {
            //    newIce.setIceDate(null);
            //}
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Error generating inspectable code element from ResultSet", ex);
        }
         return newIce;   
        
    }
}
