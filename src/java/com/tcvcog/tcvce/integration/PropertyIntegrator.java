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
import com.tcvcog.tcvce.domain.IntegrationException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
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
    }
    
    /** 
     * Utility method for property search methods whose individual
     * SQL statements implement various search features. These methods
     * can send properly configured (i.e. cursor positioned) ResultSet
     * objects to this method and get back a populated Property object
     * 
     * @param rs
     * @return the fully baked Property with all fields set from DB data
     */
    private Property generatePropertyFromRS(ResultSet rs) throws IntegrationException{
        
        MunicipalityIntegrator mi = getMunicipalityIntegrator();
        
        Property p = new Property();
        
        try{
            p.setPropertyID(rs.getInt("propertyid"));
            p.setMuni(mi.getMuniFromMuniCode(rs.getInt("municipality_muniCode")));
            p.setMuniCode(rs.getInt("municipality_muniCode"));
            p.setParID(rs.getString("parid"));
            
            p.setLotAndBlock(rs.getString("lotandblock"));
            p.setAddress(rs.getString("address"));
            p.setPropertyUseTypeName(rs.getString("name"));  //use type name
            p.setPropertyUseTypeID(rs.getInt("propertyUseType_UseID"));
            
            p.setRental(rs.getBoolean("rental"));
            p.setMultiUnit(rs.getBoolean("multiunit"));
            
            p.setUseGroup(rs.getString("usegroup"));
            p.setConstructionType(rs.getString("constructiontype"));
            p.setCountyCode(rs.getString("countycode"));
            p.setNotes(rs.getString("notes"));
            //p.setNotes(rs.getString("notes"));
            
            
        } catch (SQLException ex){
            System.out.println(ex);
            throw new IntegrationException("Error generating Property from ResultSet", ex);
        }
        return p;
    }
    
    public LinkedList<Property> searchForProperties(String addrPart) throws IntegrationException{
    
        String query = "select * from property LEFT OUTER JOIN propertyusetype ON propertyusetype.propertyUseTypeID = public.property.propertyUseType_UseID"
                + " WHERE address ILIKE '%" + addrPart + "%';";
        
        System.out.println("PropertyIntegrator.searchForPropertiesAddOnly - query: " + query);
        
        Connection con = getPostgresCon();
        ResultSet rs = null;
        Statement stmt = null;
            
        LinkedList<Property> propList = new LinkedList<>();
 
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()){
                propList.add(generatePropertyFromRS(rs));
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally
        
        return propList;
        
    }
    
    public LinkedList<Property> searchForProperties(String addrPart, int muniID) throws IntegrationException{
        
        
        String query = "select * from property LEFT OUTER JOIN propertyusetype ON public.propertyusetype.propertyUseTypeID = public.property.propertyUseType_UseID "
                + " WHERE address ILIKE '%" + addrPart + "%' AND municipality_muniCode=" 
                + muniID + ";";
        System.out.println("PropertyIntegrator.searchForProperties - with muni | sql: " + query);
        
        Connection con = getPostgresCon();
        ResultSet rs = null;
        Statement stmt = null;
            
        LinkedList<Property> propList = new LinkedList<>();
 
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()){
                propList.add(generatePropertyFromRS(rs));
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Error searching for properties", ex);
            
        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally
        
        return propList;
        
    }
    
    public HashMap getPropertyUseTypesMap() throws IntegrationException{
        
        String query = "SELECT * FROM public.propertyusetype;";
        
        Connection con = getPostgresCon();
        ResultSet rs = null;
        Statement stmt = null;
            
        HashMap<String, Integer> propertyUseTypeMap = new HashMap();
 
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            System.out.println("PropertyIntegrator.getPropertyUseTypesMap | sql: " + query);
            while(rs.next()){
                propertyUseTypeMap.put(rs.getString("name"),rs.getInt("propertyUseTypeID"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Unable to build propertyUseTypesMap", ex);
        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally
        
        return propertyUseTypeMap;
        
    }
    
    public String updateProperty(Property propToUpdate) throws IntegrationException{
        String query = "UPDATE public.property\n" +
                "   SET parid=?, lotandblock=?, \n" +
                "       address=?, propertyUseType_UseID=?, rental=?, multiunit=?, \n" +
                "       usegroup=?, constructiontype=?, countycode=?, notes=?" +
                " WHERE propertyid = ?;";
        
        Connection con = getPostgresCon();
        PreparedStatement stmt = null;
            
        HashMap<String, Integer> propertyUseTypeMap = new HashMap();
 
        try {
            stmt = con.prepareStatement(query);
            stmt.setString(1, propToUpdate.getParID());
            stmt.setString(2, propToUpdate.getLotAndBlock());
            
            stmt.setString(3, propToUpdate.getAddress());
            stmt.setInt(4, propToUpdate.getPropertyUseTypeID());
            stmt.setBoolean(5, propToUpdate.isRental());
            stmt.setBoolean(6, propToUpdate.isMultiUnit());
            
            stmt.setString(7, propToUpdate.getUseGroup());
            stmt.setString(8, propToUpdate.getConstructionType());
            stmt.setString(9, propToUpdate.getCountyCode());
            stmt.setString(10, propToUpdate.getNotes());
            
            stmt.setInt(11, propToUpdate.getPropertyID());
            
            stmt.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("Unable to build propertyUseTypesMap", ex);
        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
        } // close finally
        
        return "propertyProfile";
        
    }
    
    public Property getProperty(int propertyID) throws IntegrationException{
        Property p = new Property();
         String query = "SELECT * from property LEFT OUTER JOIN propertyusetype ON public.propertyusetype.propertyUseTypeID = public.property.propertyusetype_useid "
                + " WHERE propertyid = ?;";
        
        Connection con = getPostgresCon();
        ResultSet rs = null;
        PreparedStatement stmt = null;
 
        try {
            stmt = con.prepareStatement(query);
            stmt.setInt(1, propertyID);
            System.out.println("PropertyIntegrator.getProperty | sql: " + stmt.toString());
            rs = stmt.executeQuery();
            while(rs.next()){
                p = generatePropertyFromRS(rs);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new IntegrationException("PropertyIntegrator.getProperty | Unable to retrieve property by ID number", ex);
        } finally{
             if (con != null) { try { con.close(); } catch (SQLException e) { /* ignored */} }
             if (stmt != null) { try { stmt.close(); } catch (SQLException e) { /* ignored */} }
             if (rs != null) { try { rs.close(); } catch (SQLException ex) { /* ignored */ } }
        } // close finally
        
        return p;
        
    } // close getProperty()
    
    
    
} // close class
