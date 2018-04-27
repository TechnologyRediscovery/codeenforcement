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
import com.tcvcog.tcvce.occupancy.OccupancyInspectionFee;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
/**
 *
 * @author Adam Gutonski
 */
public class OccupancyInspectionFeeIntegrator extends BackingBeanUtils implements Serializable {
    
    public void updateOccupancyInspectionFee(OccupancyInspectionFee oif) throws IntegrationException {
        String query = "UPDATE public.occinspectionfee\n" +
                    "   SET muni_municode=?, feename=?, feeamount=?, effectivedate=?, \n" +
                    "       expirydate=?, notes=? \n" +
                    " WHERE feeid=?;";
        
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;
        
        try{
            stmt = con.prepareStatement(query);
            stmt.setInt(1, oif.getMuni().getMuniCode());
            stmt.setString(2, oif.getOccupancyInspectionFeeName());
            stmt.setDouble(3, oif.getOccupancyInspectionFeeAmount());
            //update effective date
            if(oif.getOccupancyInspectionFeeEffDate() != null){
                stmt.setTimestamp(4, java.sql.Timestamp.valueOf(oif.getOccupancyInspectionFeeEffDate()));
                
            } else {
                stmt.setNull(4, java.sql.Types.NULL);
            }
            //update expiry date
            if(oif.getOccupancyInspectionFeeExpDate() != null){
                stmt.setTimestamp(5, java.sql.Timestamp.valueOf(oif.getOccupancyInspectionFeeExpDate()));
                
            } else {
                stmt.setNull(5, java.sql.Types.NULL);
            }
            stmt.setString(6, oif.getOccupancyInspectionFeeNotes());
            stmt.setInt(7, oif.getOccupancyInspectionFeeID());
            System.out.println("TRYING TO EXECUTE UPDATE METHOD");
            stmt.executeUpdate();
        } catch (SQLException ex){
            System.out.println(ex.toString());
            throw new IntegrationException("Unable to update occupancy inspection fee", ex);
        } finally {
            if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
            if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        }
        
    }
    
    
    public LinkedList<OccupancyInspectionFee> getOccupancyInspectionFeeList() throws IntegrationException {
            String query = "SELECT feeid, muni_municode, feename, feeamount, effectivedate, expirydate, \n" +
                            "       notes\n" +
                            "  FROM public.occinspectionfee";
            Connection con = getPostgresCon();
            ResultSet rs = null;
            PreparedStatement stmt = null;
            LinkedList<OccupancyInspectionFee> occupancyInspectionFeeList = new LinkedList();
        
        try {
            stmt = con.prepareStatement(query);
            System.out.println("");
            rs = stmt.executeQuery();
            System.out.println("OccupancyInspectionFeeIntegrator.getOccupancyInspectionFeeList | SQL: " + stmt.toString());
            while(rs.next()){
                occupancyInspectionFeeList.add(generateOccupancyInspectionFee(rs));
            }
            
            } catch (SQLException ex) {
                System.out.println(ex.toString());
                throw new IntegrationException("Cannot get Occupancy Inspection Fee List", ex);
            } finally {
                if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
                if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
                if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
            }
            return occupancyInspectionFeeList;   
    }
    
       
    public void insertOccupancyInspectionFee(OccupancyInspectionFee occupancyInspectionFee) throws IntegrationException {
        String query = "INSERT INTO public.occinspectionfee(\n" +
                        "            feeid, muni_municode, feename, feeamount, effectivedate, expirydate, \n" +
                        "            notes)\n" +
                        "    VALUES (DEFAULT, ?, ?, ?, ?, ?, \n" +
                        "            ?);";
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement(query);
            stmt.setInt(1, occupancyInspectionFee.getMuni().getMuniCode());
            stmt.setString(2, occupancyInspectionFee.getOccupancyInspectionFeeName());
            stmt.setDouble(3, occupancyInspectionFee.getOccupancyInspectionFeeAmount());
            if(occupancyInspectionFee.getOccupancyInspectionFeeEffDate() != null){
                stmt.setTimestamp(4, java.sql.Timestamp.valueOf(occupancyInspectionFee.getOccupancyInspectionFeeEffDate()));
                
            } else {
                stmt.setNull(4, java.sql.Types.NULL);
            }
            if(occupancyInspectionFee.getOccupancyInspectionFeeExpDate() != null){
                stmt.setTimestamp(5, java.sql.Timestamp.valueOf(occupancyInspectionFee.getOccupancyInspectionFeeExpDate()));
            } else {
                stmt.setNull(5, java.sql.Types.NULL);
            }
            stmt.setString(6, occupancyInspectionFee.getOccupancyInspectionFeeNotes());
            System.out.println("OccupancyInspectionFeeIntegrator.occupancyInspectionFeeIntegrator | sql: " + stmt.toString());
            System.out.println("TRYING TO EXECUTE INSERT METHOD");
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Cannot insert Occupancy Inspection Fee", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
        
    }
    
    public void deleteOccupancyInspectionFee(OccupancyInspectionFee oif) throws IntegrationException{
         String query = "DELETE FROM public.occinspectionfee\n" +
                " WHERE feeid= ?;";
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(query);
            stmt.setInt(1, oif.getOccupancyInspectionFeeID());
            stmt.execute();

        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Cannot delete occupancy inspeciton fee--probably because another"
                    + "part of the database has a reference item.", ex);

        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
    }
    
    
    private OccupancyInspectionFee generateOccupancyInspectionFee(ResultSet rs) throws IntegrationException {
        OccupancyInspectionFee newOif = new OccupancyInspectionFee();
        MunicipalityIntegrator mi = getMunicipalityIntegrator();
    
        try {
            newOif.setOccupancyInspectionFeeID(rs.getInt("feeid"));
            newOif.setMuni(mi.getMuniFromMuniCode(rs.getInt("muni_municode")));
            newOif.setOccupancyInspectionFeeName(rs.getString("feename"));
            newOif.setOccupancyInspectionFeeAmount(rs.getDouble("feeamount"));
            java.sql.Timestamp eff = rs.getTimestamp("effectivedate");
            //for effective date
            if(eff != null) {
                newOif.setOccupancyInspectionFeeEffDate(eff.toLocalDateTime());
            } else {
                newOif.setOccupancyInspectionFeeEffDate(null);
            }
            java.sql.Timestamp exp = rs.getTimestamp("expirydate");
            //for expiration date
            if(exp != null) {
                newOif.setOccupancyInspectionFeeExpDate(exp.toLocalDateTime());
            } else {
                newOif.setOccupancyInspectionFeeExpDate(null);
            }
            newOif.setOccupancyInspectionFeeNotes(rs.getString("notes"));
            
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Error generation OccInspectionFee from result set", ex);
        }
        
        return newOif;
    }
    
}
