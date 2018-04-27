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
import com.tcvcog.tcvce.occupancy.OccupancyPermitType;
import java.io.Serializable;
import java.sql.*;
import java.util.LinkedList;



/**
 *
 * @author Adam Gutonski
 */
public class OccupancyPermitTypeIntegrator extends BackingBeanUtils implements Serializable {
    
    public void updateOccupancyPermitType(OccupancyPermitType opt) throws IntegrationException {
        String query = "UPDATE public.occpermittype\n" +
                    "   SET typename=?, typedescription=?\n" +
                    " WHERE typeid=?;";
        
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;
        
        try{
            stmt = con.prepareStatement(query);
            //stmt.setInt(1, opt.getOccupancyPermitTypeID());
            //stmt.setInt(2, opt.getOccupancyPermitTypeMuniCodeID());
            stmt.setString(1, opt.getOccupancyPermitTypeName());
            stmt.setString(2, opt.getOccupancyPermitTypeDescription());
            stmt.setInt(3, opt.getOccupancyPermitTypeID());
            stmt.executeUpdate();
        } catch (SQLException ex){
            System.out.println(ex.toString());
            throw new IntegrationException("Unable to update occupancy permit type", ex);
        } finally {
            if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
            if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        }
        
    }
    
    public void deleteOccupancyPermitType(OccupancyPermitType opt) throws IntegrationException{
         String query = "DELETE FROM public.occpermittype\n" +
                        " WHERE typeid=?;";
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(query);
            stmt.setInt(1, opt.getOccupancyPermitTypeID());
            stmt.execute();

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Cannot delete occupancy permit type--probably because another"
                    + "part of the database has a reference item.", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
    }
    
    
    public LinkedList<OccupancyPermitType> getOccupancyPermitTypeList() throws IntegrationException{
        String query = "SELECT typeid, muni_municode, typename, typedescription\n" +
                       "  FROM public.occpermittype";
        Connection con = getPostgresCon();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        LinkedList<OccupancyPermitType> occupancyPermitTypeList = new LinkedList();
        
        try {
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            System.out.println("OccupancyPermitTypeIntegrator.getOccupancyPermitTypeList | SQL: " + stmt.toString());
            while(rs.next()){
                occupancyPermitTypeList.add(generateOccupancyPermitType(rs));
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Cannot get OccupancyPermitType", ex);
        } finally {
            if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
            if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
            if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        }
        return occupancyPermitTypeList;        
    }
    
    
    public void insertOccupancyPermitType(OccupancyPermitType occupancyPermitType) throws IntegrationException{
        String query = "INSERT INTO public.occpermittype(\n" +
                    "  typeid, muni_municode, typename, typedescription)\n" +
                    "  VALUES (DEFAULT, ?, ?, ?)";
    
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;
        
        try{
            stmt = con.prepareStatement(query);
            //stmt.setInt(1, occupancyPermitType.getOccupancyPermitTypeMuniCodeID());
            stmt.setInt(1, occupancyPermitType.getMuni().getMuniCode());
            stmt.setString(2, occupancyPermitType.getOccupancyPermitTypeName());
            stmt.setString(3, occupancyPermitType.getOccupancyPermitTypeDescription());
            System.out.println("OccupancyPermitTypeIntegrator.occupancyPermitTypeIntegrator | sql: " + stmt.toString());
            stmt.execute();
        } catch (SQLException ex){
            System.out.println(ex.toString());
            throw new IntegrationException("Cannot insert OccupancyPermitType ", ex);
        } finally {
            if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
            if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        }
        
    }
    
    
    
    private OccupancyPermitType generateOccupancyPermitType(ResultSet rs) throws IntegrationException{
        OccupancyPermitType newOpt = new OccupancyPermitType();
        MunicipalityIntegrator mi = getMunicipalityIntegrator();
        
        try{
            newOpt.setOccupancyPermitTypeID(rs.getInt("typeid"));
            newOpt.setMuni(mi.getMuniFromMuniCode(rs.getInt("muni_municode")));
            newOpt.setOccupancyPermitTypeName(rs.getString("typename"));
            newOpt.setOccupancyPermitTypeDescription(rs.getString("typedescription"));
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Error generating OccPermitType from ResultSet", ex);
        }
        
         return newOpt;
    }
    
}
