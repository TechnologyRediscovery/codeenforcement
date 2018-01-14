/*
 * Copyright (C) 2018 Turtle Creek Valley
 * Council of Governments, PA
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
import com.tcvcog.tcvce.entities.Municipality;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 *
 * @author sylvia
 */
public class MunicipalityIntegrator extends BackingBeanUtils implements Serializable {

    private HashMap municipalityMap;
    /**
     * Creates a new instance of MunicipalityIntegrator
     */
    public MunicipalityIntegrator() {
        
        
    }
    
    public Municipality getMuniFromMuniCode(int muniCode) throws IntegrationException{
        Municipality muni = new Municipality();
        PreparedStatement stmt = null;
        Connection con = null;
        // note that muniCode is not returned in this query since it is specified in the WHERE
        String query = "SELECT muniname, address_street, address_city, "
                + "address_state, address_zip, phone, "
                + "fax, email, managername, "
                + "managerphone, population, activeinprogram\n" +
                "FROM public.municipality WHERE municode = ?;";
        ResultSet rs = null;
 
        try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            stmt.setInt(1, muniCode);
            //System.out.println("MunicipalityIntegrator.getMuniFromMuniCode | query: " + stmt.toString());
            rs = stmt.executeQuery();
            
            while(rs.next()){
                muni.setMuniName(rs.getString(1));
                muni.setAddress_street(rs.getString(2));
                muni.setAddress_city(rs.getString(3));
                
                muni.setAddress_state(rs.getString(4));
                muni.setAddress_zip(rs.getString(5));
                muni.setPhone(rs.getString(6));
                
                muni.setFax(rs.getString(7));
                muni.setEmail(rs.getString(8));
                muni.setManagerName(rs.getString(9));
                
                muni.setManagerPhone(rs.getString(10));
                muni.setPopulation(rs.getInt(11));
                muni.setActiveInProgram(rs.getBoolean(12));             
            }
        } catch (SQLException ex) {
            System.out.println("MunicipalityIntegrator.getMuniFromMuniCode | " + ex.toString());
            throw new IntegrationException("Exception in MunicipalityIntegrator.getMuniFromMuniCode", ex);
        } finally{
           if (stmt != null){ try { stmt.close(); } catch (SQLException ex) {/* ignored */ } }
           if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
           if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
        
        return muni;
        
    }
    
    public void generateCompleteMuniNameIDMap() throws IntegrationException{
        HashMap<String, Integer> muniMap = new HashMap<>();
        
        Connection con = getPostgresCon();
        String query = "SELECT muniCode, muniName FROM municipality;";
        ResultSet rs = null;
        Statement stmt = null;
 
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()){
                muniMap.put(rs.getString("muniName"), rs.getInt("muniCode"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Exception in MunicipalityIntegrator.generateCompleteMuniNameIDMap", ex);

        } finally{
           if (stmt != null){ try { stmt.close(); } catch (SQLException ex) {/* ignored */ } }
           if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
           if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
        
        municipalityMap = muniMap;
    }
    
    public void updateMuni(Municipality muni){
        
        
    }
   

    /**
     * @return the municipalityMap
     * @throws com.tcvcog.tcvce.domain.IntegrationException
     */
    public HashMap getMunicipalityMap() throws IntegrationException{
        generateCompleteMuniNameIDMap();
        return municipalityMap;
    }

    /**
     * @param municipalityMap the municipalityMap to set
     */
    public void setMunicipalityMap(HashMap municipalityMap) {
        this.municipalityMap = municipalityMap;
    }
    
}
