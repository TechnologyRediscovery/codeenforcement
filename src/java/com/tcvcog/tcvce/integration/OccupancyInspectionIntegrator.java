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

import java.sql.*;
import com.tcvcog.tcvce.application.BackingBeanUtils;
import com.tcvcog.tcvce.domain.IntegrationException;
import com.tcvcog.tcvce.occupancy.OccupancyInspection;
import java.io.Serializable;
import java.util.LinkedList;

/**
 * This integrator creates our PostGres connection for DB manipulation
 * @author Adam Gutonski
 */
public class OccupancyInspectionIntegrator extends BackingBeanUtils implements Serializable {
    
    public OccupancyInspectionIntegrator(){
        
    }
    
    public void insertOccupanyInspection(OccupancyInspection occupancyInspection) throws IntegrationException{
        String query = "INSERT INTO public.occupancyinspection(\n" +
                "   inspectionid, propertyunitid, login_userid, firstinspectiondate, "
            +   "firstinspectionpass, secondinspectiondate, secondinspectionpass, "
            +   "resolved, totalfeepaid, notes) \n" 
            +   "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement(query);
            stmt.setInt(1, occupancyInspection.getPropertyUnitID());
            stmt.setInt(2, occupancyInspection.getLoginUserID());
            if(occupancyInspection.getFirstInspectionDate() != null){
                stmt.setTimestamp(3, java.sql.Timestamp.valueOf(occupancyInspection.getFirstInspectionDate()));
            } else {
                stmt.setNull(3, java.sql.Types.NULL);
            }
            stmt.setBoolean(4, occupancyInspection.isFirstInspectionPass());
            if(occupancyInspection.getSecondInspectionDate() != null){
                stmt.setTimestamp(5, java.sql.Timestamp.valueOf(occupancyInspection.getSecondInspectionDate()));
            } else {
                stmt.setNull(5, java.sql.Types.NULL);
            }
            stmt.setBoolean(6, occupancyInspection.isSecondInspectionPass());
            stmt.setBoolean(7, occupancyInspection.isResolved());
            stmt.setBoolean(8, occupancyInspection.isTotalFeePaid());
            stmt.setString(9, occupancyInspection.getOccupancyInspectionNotes());
            
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Cannot insert OccupancyInspection", ex);
        
        } finally {
            if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
            if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} } 
        }
                
                
    }
    
    public void updateOccupancyInspection(OccupancyInspection occInspection) throws IntegrationException {
        String query = "UPDATE public.occupancyinspection\n" +
                        "   SET propertyunitid=?, login_userid=?, firstinspectiondate=?, \n" +
                        "       firstinspectionpass=?, secondinspectiondate=?, secondinspectionpass=?, \n" +
                        "       resolved=?, totalfeepaid=?, notes=?\n" +
                        " WHERE inspectionid=?;";
        
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;
        
        try{
            stmt = con.prepareStatement(query);
            stmt.setInt(1, occInspection.getPropertyUnitID());
            stmt.setInt(2, occInspection.getLoginUserID());
            //update first inspection date
            if(occInspection.getFirstInspectionDate() != null){
                stmt.setTimestamp(3, java.sql.Timestamp.valueOf(occInspection.getFirstInspectionDate()));
                
            } else {
                stmt.setNull(3, java.sql.Types.NULL);
            }
            stmt.setBoolean(4, occInspection.isFirstInspectionPass());
            //update second inspection date
            if(occInspection.getSecondInspectionDate() != null){
                stmt.setTimestamp(5, java.sql.Timestamp.valueOf(occInspection.getSecondInspectionDate()));
                
            } else {
                stmt.setNull(5, java.sql.Types.NULL);
            }
            stmt.setBoolean(6, occInspection.isSecondInspectionPass());
            stmt.setBoolean(7, occInspection.isResolved());
            stmt.setBoolean(8, occInspection.isTotalFeePaid());
            stmt.setString(9, occInspection.getOccupancyInspectionNotes());
            stmt.setInt(10, occInspection.getInspectionID());
            System.out.println("TRYING TO EXECUTE UPDATE METHOD");
            stmt.executeUpdate();
        } catch (SQLException ex){
            System.out.println(ex.toString());
            throw new IntegrationException("Unable to update occupancy inspection record", ex);
        } finally {
            if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
            if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        }
        
    }
    
    public void deleteOccupancyInspection(OccupancyInspection occInspection) throws IntegrationException{
         String query = "DELETE FROM public.occupancyinspection\n" +
                        " WHERE inspectionid=?;";
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(query);
            stmt.setInt(1, occInspection.getInspectionID());
            stmt.execute();

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Cannot delete occupancy inspeciton--probably because another"
                    + "part of the database has a reference item.", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
    }
    
    private OccupancyInspection generateOccupancyInspection(ResultSet rs) throws IntegrationException{
        OccupancyInspection newInspection = new OccupancyInspection();
        
        try{
            newInspection.setInspectionID(rs.getInt("inspectionid"));
            newInspection.setPropertyUnitID(rs.getInt("propertyunitid"));
            newInspection.setLoginUserID(rs.getInt("login_userid"));
            java.sql.Timestamp s = rs.getTimestamp("firstinspectiondate");
            if(s != null){
                newInspection.setFirstInspectionDate(s.toLocalDateTime());
            } else {
                newInspection.setFirstInspectionDate(null);
            }
            newInspection.setFirstInspectionPass(rs.getBoolean("firstinspectionpass"));
            java.sql.Timestamp t = rs.getTimestamp("secondinspectiondate");
            if(t != null){
                newInspection.setSecondInspectionDate(t.toLocalDateTime());
            } else {
                newInspection.setSecondInspectionDate(null);
            }
            newInspection.setResolved(rs.getBoolean("resolved"));
            newInspection.setTotalFeePaid(rs.getBoolean("totalfeepaid"));
            newInspection.setOccupancyInspectionNotes(rs.getString("notes"));
            
        }catch (SQLException ex){
            System.out.println(ex.toString());
            throw new IntegrationException("Error generating occupancy inspection from ResultSet", ex);
        }
        return newInspection;
    }
    
    public LinkedList<OccupancyInspection> getOccupancyInspectionList() throws IntegrationException{
        String query = "SELECT inspectionid, propertyunitid, login_userid, firstinspectiondate, \n" +
                    "       firstinspectionpass, secondinspectiondate, secondinspectionpass, \n" +
                    "       resolved, totalfeepaid, notes\n" +
                    "  FROM public.occupancyinspection;";
    Connection con = getPostgresCon();
    ResultSet rs = null;
    PreparedStatement stmt = null;
    LinkedList<OccupancyInspection> occupancyInspectionList = new LinkedList();
    
    try{
        stmt = con.prepareStatement(query);
        rs = stmt.executeQuery();
        System.out.println("OccupancyInspectionIntegrator.getOccupancyInspectionList | SQL: " + stmt.toString());
        
        while(rs.next()){
            occupancyInspectionList.add(generateOccupancyInspection(rs));
        }
    } catch (SQLException ex){
        System.out.println(ex.toString());
        throw new IntegrationException("Cannot get occupancy inspection", ex);
    } finally {
        if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
        if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
    }
    return occupancyInspectionList;
    
    }
    
}
