/*
 * Copyright (C) 2017 Turtle Creek Valley
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
package com.tcvcog.tcvce.integration;

import com.tcvcog.tcvce.application.BackingBeanUtils;
import com.tcvcog.tcvce.domain.IntegrationException;
import java.io.Serializable;
import com.tcvcog.tcvce.entities.CodeSource;
import com.tcvcog.tcvce.entities.CodeElement;
import com.tcvcog.tcvce.entities.CodeElementType;
import com.tcvcog.tcvce.entities.CodeSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.ActionSource;

/**
 *
 * @author sylvia
 */
public class CodeIntegrator extends BackingBeanUtils implements Serializable {

    /**
     * Creates a new instance of CodeIntegrator
     */
    public CodeIntegrator() {
        System.out.println("CodeIntegrator.CodeIntegrator");
        
    }
    
    public void getCodeSourceDetails(ActionSource as){
        
    }
    
    public CodeSource populateCodeSourceFromRS(ResultSet rs, CodeSource source) throws SQLException{
        
        
        source.setSourceID(rs.getInt(1));
        source.setSourceName(rs.getString(2));
        source.setSourceYear(rs.getInt(3));
        source.setSourceDescription(rs.getString(4));
        source.setIsActive(rs.getBoolean(5));
        source.setURL(rs.getString(6));
        source.setSourceNotes(rs.getString(7));
        return source;
        
    }
    
    /**
     * Retrieves code source information given a sourceID
     * @param sourceID the unique ID number of the code source in the DB
     * @return the CodeSource object built from the DB read
     * @throws com.tcvcog.tcvce.domain.IntegrationException
     */
    public CodeSource getCodeSourceBySourceID(int sourceID) throws IntegrationException{
        System.out.println("CodeIntegrator.getCodeSourceBySourceID");
        CodeSource source = new CodeSource();

        String query = "SELECT sourceid, name, year, "
                + "description, isactive, url, "
                + "notes\n" 
                + " FROM public.codesource WHERE sourceid = ?;";
        
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs;
        
        try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            stmt.setInt(1, sourceID);
            rs = stmt.executeQuery();
            while(rs.next()){
                populateCodeSourceFromRS(rs, source);

            }
            
        } catch (SQLException ex) { 
             System.out.println(ex.toString());
             throw new IntegrationException("Exception in CodeIntegrator", ex);
        } finally{
            if (stmt != null){
                try {
                    stmt.close();
                } catch (SQLException ex) { /* ignored */ }
                
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) { /* ignored */}
             }
        } // close finally
        
        
        return source;
    }
    
    /**
     * Takes in a CodeSource object and inserts into the DB
     * @param sourceToInsert
     */
    public void insertCodeSource(CodeSource sourceToInsert){
        String query = "INSERT INTO public.codesource(\n" +
            "sourceid, name, year, description, isactive, url, notes)\n" +
            "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?);";

        // create sql statement up here
        Connection con = null;
        PreparedStatement stmt = null;

         try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            stmt.setString(1, sourceToInsert.getSourceName());
            stmt.setInt(2, sourceToInsert.getSourceYear());
            stmt.setString(3, sourceToInsert.getSourceDescription());
            stmt.setBoolean(4, sourceToInsert.isIsActive());
            stmt.setString(5, sourceToInsert.getURL());
            stmt.setString(6, sourceToInsert.getSourceNotes());
            stmt.executeUpdate();
            System.out.println("CodeIntegrator.insertCodeSource: executed update with SQL - " + query);
             
        } catch (SQLException ex) { 
             System.out.println(ex.toString());
             getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Business Object (Case, Violation, ...) Database Problem", 
                            "SQLException Caught by Integreator"));
        } finally{
            if (stmt != null){
                try {
                    stmt.close();
                } catch (SQLException ex) { /* ignored */ }
                
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) { /* ignored */}
             }
        } // close finally
    } 
    
    /**
     * Updates fields in the DB for a given CodeSource objects
     * @param sourceToUpdate
     */
    public void updateCodeSource(CodeSource sourceToUpdate){
        System.out.println("CodeIntegreator.updateCodeSource");
         String query = "UPDATE public.codesource\n" +
                "   SET name=?, year=?, description=?, isactive=?, url=?, \n" +
                "       notes=?\n" +
                " WHERE sourceid=?;";

        // create sql statement up here
        Connection con = null;
        PreparedStatement stmt = null;

         try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            stmt.setString(1, sourceToUpdate.getSourceName());
            stmt.setInt(2, sourceToUpdate.getSourceYear());
            stmt.setString(3, sourceToUpdate.getSourceDescription());
            stmt.setBoolean(4, sourceToUpdate.isIsActive());
            stmt.setString(5, sourceToUpdate.getURL());
            stmt.setString(6, sourceToUpdate.getSourceNotes());
            stmt.setInt(7, sourceToUpdate.getSourceID());
            stmt.execute();
            
            
             
             
        } catch (SQLException ex) { 
             System.out.println(ex.toString());
             getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Business Object (Case, Violation, ...) Database Problem", 
                            "SQLException Caught by Integreator"));
        } finally{
            if (stmt != null){
                try {
                    stmt.close();
                } catch (SQLException ex) { /* ignored */ }
                
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) { /* ignored */}
             }
        } // close finally
    }
    
    /**
     * Removes the provided code source from the DB
     * @param source the CodeSource to remove from the DB
     */
    public void deleteCodeSource(CodeSource source){
        // TODO: Write guts
    }
    
    public LinkedList getCompleteCodeSourceList() throws IntegrationException{
        String query = "SELECT sourceid, name, year, "
              + "description, isactive, url, "
              + "notes\n" 
              + " FROM public.codesource;";
        
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs;
        LinkedList<CodeSource> codeSources = new LinkedList();
        CodeSource source = new CodeSource();
        
        try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            while(rs.next()){
                source = populateCodeSourceFromRS(rs, new CodeSource());
                if(source != null){
                    // figure out about this possible dereferencing warning
                    codeSources.add(source);
                }
            }
            
        } catch (SQLException ex) { 
             System.out.println(ex.toString());
             throw new IntegrationException("Exception in CodeIntegrator", ex);
        } finally{
            if (stmt != null){
                try {
                    stmt.close();
                } catch (SQLException ex) { /* ignored */ }
                
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) { /* ignored */}
             }
        } // close finally
        
        return codeSources;
    }
    
    public CodeSet getCodeSetBySetID(int setID){
        return new CodeSet();
        
    }
    
    public void addCodeElementToCodeSet(CodeElement element){
        
        
    }
    
    public void removeCodeElementFromCodeSet(CodeElement element ){
        
        
    }
    
    public int createCodeSet(String setName){
        
        return 0;
    }
    
    public void deleteCodeSet(int setID){
        
        
    }
    public LinkedList getCodeElementsBySourceID(int sourceID) throws IntegrationException{
        String query = "SELECT elementid, codeelementtype.cdelTypeID as codeelementtypeid, codeelementtype.name as codeelementtypename, \n" +
            "	codesource_sourceid, codesource.name as codesourcename, codesource.year as codesourceyear, \n" +
            "	ordchapterno, \n" +
            "	ordchaptertitle, ordsecnum, ordsectitle, \n" +
            "	ordsubsecnum, ordsubsectitle, ordtechnicaltext, \n" +
            "	ordhumanfriendlytext, defaultpenalty, codeelement.isactive, \n" +
            "	isenforcementpriority, defaultdaystocomply, resourceurl, \n" +
            "	inspectiontips, datecreated\n" +
            "       FROM public.codeelement 	INNER JOIN public.codeelementtype ON cdeltypeid = codeelementtype_cdelTypeID \n" +
            "    				INNER JOIN public.codesource ON sourceid = codesource_sourceID;" +
                "  WHERE codesource_sourceid = ?;";
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        CodeElement el = new CodeElement();
        LinkedList<CodeElement> elementList = new LinkedList();
         try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            
            stmt.setInt(1, sourceID);
            
            rs = stmt.executeQuery();
            
            el.setElementID(rs.getInt(1));
            el.setCodeElementTypeID(rs.getInt(2));
            el.setCodeElementTypeName(rs.getString(3));
            
            el.setSourceID(rs.getInt(4));
            el.setSourceName(rs.getString(5)); 
            el.setSourceYear(rs.getInt(6));
            
            el.setOrdchapterNo(rs.getInt(7));
            
            el.setOrdchapterTitle(rs.getString(8));
            el.setOrdSecNum(rs.getString(9));
            el.setOrdsecTitle(rs.getString(10));
            
            el.setOrdSubSecNum(rs.getString(11));
            el.setOrdSubSecTitle(rs.getString(12));
            el.setOrdTechnicalText(rs.getString(13));
            
            el.setOrdHumanFriendlyText(rs.getString(14));
            el.setDefaultPenalty(rs.getInt(15));
            el.setIsActive(rs.getBoolean(16));
            
            el.setIsEnforcementPriority(rs.getBoolean(17));
            el.setDefaultDaysToComply(rs.getInt(18));
            el.setResourceURL(rs.getString(19));
            
            el.setInspectionTips(rs.getString(20));
            el.setDateCreated(rs.getTimestamp(21).toLocalDateTime());
            elementList.add(el);
            
             
        } catch (SQLException ex) { 
             System.out.println(ex.toString());
             throw new IntegrationException("Error Retrieving code element list", ex);
        } finally{
            if (stmt != null){
                try {
                    stmt.close();
                } catch (SQLException ex) { /* ignored */ }
                
            }
            if (rs != null){
                try {
                    rs.close();
                } catch (SQLException ex) { /* ignored */ }
                
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) { /* ignored */}
             }
        } // close finally
        
         return elementList;
    } // close getCodeElementsBySourceID
    
    public int insertCodeElement(CodeElement element) throws IntegrationException{
         String query = "INSERT INTO public.codeelement(\n" +
"            elementid, codeelementtype_cdeltypeid, codesource_sourceid, ordchapterno, \n" +
"            ordchaptertitle, ordsecnum, ordsectitle, ordsubsecnum, ordsubsectitle, \n" +
"            ordtechnicaltext, ordhumanfriendlytext, defaultpenalty, isactive, \n" +
"            isenforcementpriority, defaultdaystocomply, resourceurl, inspectiontips, \n" +
"            datecreated)\n" +
        "    VALUES (?, ?, ?, ?, \n" +
        "            ?, ?, ?, ?, ?, \n" +
        "            ?, ?, ?, ?, \n" +
        "            ?, ?, ?, ?, \n" +
        "            ?);";

        // create sql statement up here
        Connection con = null;
        PreparedStatement stmt = null;

         try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            
            stmt.executeUpdate();
             
        } catch (SQLException ex) { 
             System.out.println(ex.toString());
             throw new IntegrationException("Error inserting code element", ex);
        } finally{
            if (stmt != null){
                try {
                    stmt.close();
                } catch (SQLException ex) { /* ignored */ }
                
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) { /* ignored */}
             }
        } // close finally
        
        
        return 0;
        
    }
    
    public int updateCodeElement(CodeElement element) throws IntegrationException{
        String query = null;
        Connection con = null;
        PreparedStatement stmt = null;

         try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            
            stmt.executeUpdate();
             
        } catch (SQLException ex) { 
             System.out.println(ex.toString());
             throw new IntegrationException("Error inserting code element", ex);
        } finally{
            if (stmt != null){
                try {
                    stmt.close();
                } catch (SQLException ex) { /* ignored */ }
                
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) { /* ignored */}
             }
        } // close finally
        return 0;
    }
    
    public void deleteCodeElement(CodeElement element) throws IntegrationException{
        String query = null;
        Connection con = null;
        PreparedStatement stmt = null;

         try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            
            stmt.executeUpdate();
             
        } catch (SQLException ex) { 
             System.out.println(ex.toString());
             throw new IntegrationException("Error inserting code element", ex);
        } finally{
            if (stmt != null){
                try {
                    stmt.close();
                } catch (SQLException ex) { /* ignored */ }
                
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) { /* ignored */}
             }
        } // close finally
    }
    
    public void markCodeElementAsInactive(CodeElement element) throws IntegrationException{
        String query = null;
        Connection con = null;
        PreparedStatement stmt = null;

         try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            
            stmt.executeUpdate();
             
        } catch (SQLException ex) { 
             System.out.println(ex.toString());
             throw new IntegrationException("Error inserting code element", ex);
        } finally{
            if (stmt != null){
                try {
                    stmt.close();
                } catch (SQLException ex) { /* ignored */ }
                
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) { /* ignored */}
             }
        } // close finally
    }
    
    public void markCodeElementAsPriority(CodeElement element) throws IntegrationException{
        String query = null;
        Connection con = null;
        PreparedStatement stmt = null;

         try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            
            stmt.executeUpdate();
             
        } catch (SQLException ex) { 
             System.out.println(ex.toString());
             throw new IntegrationException("Error inserting code element", ex);
        } finally{
            if (stmt != null){
                try {
                    stmt.close();
                } catch (SQLException ex) { /* ignored */ }
                
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) { /* ignored */}
             }
        } // close finally
    }
    
    public void insertNewCodeElementType(CodeElementType cdelType) throws IntegrationException{
        System.out.println("CodeIntegreator.insertNewCodeElementType");
        String query = "INSERT INTO public.codeelementtype(\n" +
"            cdeltypeid, name, description)\n" +
"            VALUES (DEFAULT, ?, ?);\n";
        
        Connection con = null;
        PreparedStatement stmt = null;

         try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            stmt.setString(1, cdelType.getName());
            stmt.setString(2, cdelType.getDescription());
            stmt.executeUpdate();
             
        } catch (SQLException ex) { 
             System.out.println(ex.toString());
             throw new IntegrationException("Error inserting code element type", ex);
        } finally{
            if (stmt != null){
                try {
                    stmt.close();
                } catch (SQLException ex) { /* ignored */ }
                
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) { /* ignored */}
             }
        } // close finally
    }
    
    public void updateCodeElementType(CodeElementType cdelType) throws IntegrationException{
        String query = "UPDATE public.codeelementtype\n" +
                    "   SET name=?, description=?\n" +
                    " WHERE cdeltypeid = ?;";
        Connection con = null;
        PreparedStatement stmt = null;

         try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            stmt.setString(1, cdelType.getName());
            stmt.setString(2, cdelType.getDescription());
            stmt.setInt(3, cdelType.getCdelTypeID());
            System.out.println("CodeIntegreator.updateCodeElementType: " + stmt.toString());
            stmt.execute();
             
        } catch (SQLException ex) { 
             System.out.println(ex.toString());
             throw new IntegrationException("Error inserting code element", ex);
        } finally{
            if (stmt != null){
                try {
                    stmt.close();
                } catch (SQLException ex) { /* ignored */ }
                
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) { /* ignored */}
             }
        } // close finally
        
    }
    
    public void deleteCodeElementType(CodeElementType cdelType) throws IntegrationException{
        String query = "DELETE FROM public.codeelementtype\n" +
                    " WHERE cdeltypeid = ?;";
        Connection con = null;
        PreparedStatement stmt = null;

         try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            stmt.setInt(1, cdelType.getCdelTypeID());
            stmt.executeUpdate();
             
        } catch (SQLException ex) { 
             System.out.println(ex.toString());
             throw new IntegrationException("Error inserting code element", ex);
        } finally{
            if (stmt != null){
                try {
                    stmt.close();
                } catch (SQLException ex) { /* ignored */ }
                
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) { /* ignored */}
             }
        } // close finally
        
    }
    
    public HashMap getCodeElementTypesMap() throws IntegrationException{
        System.out.println("CodeIntegrator.getCodeElementTypesMap");
        String query = "SELECT cdeltypeid, name, description\n" +
                        "  FROM public.codeelementtype;";
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        HashMap<String, Integer> map = new HashMap();

         try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            while(rs.next()){
                CodeElementType cdelType = new CodeElementType();
                cdelType.setCdelTypeID(rs.getInt(1));
                cdelType.setName(rs.getString(2));
                cdelType.setDescription(rs.getString(3));
                map.put(cdelType.getName(), cdelType.getCdelTypeID());
            }
             
        } catch (SQLException ex) { 
             System.out.println(ex.toString());
             throw new IntegrationException("Error retrieving code element types map", ex);
        } finally{
            if (stmt != null){
                try {
                    stmt.close();
                } catch (SQLException ex) { /* ignored */ }
            }
            if (rs != null ){
                try{
                    rs.close();
                } catch (SQLException ex) { /* ignored */ }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) { /* ignored */}
             }
        } // close finally
        return map;
    }
    
    public CodeElementType getCodeElementTypeByID(int typeId) throws IntegrationException{
        String query = "SELECT cdeltypeid, name, description\n" +
                        "  FROM public.codeelementtype WHERE cdeltypeid=?;";
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        HashMap<String, Integer> map = new HashMap();
        CodeElementType cdelType = null;
         try {
            con = getPostgresCon();
            stmt = con.prepareStatement(query);
            stmt.setInt(1, typeId);
            rs = stmt.executeQuery();
            while(rs.next()){
                cdelType = new CodeElementType();
                cdelType.setCdelTypeID(rs.getInt(1));
                cdelType.setName(rs.getString(2));
                cdelType.setDescription(rs.getString(3));
            }
             
        } catch (SQLException ex) { 
             System.out.println(ex.toString());
             throw new IntegrationException("Error retrieving code element types map", ex);
        } finally{
            if (stmt != null){
                try {
                    stmt.close();
                } catch (SQLException ex) { /* ignored */ }
            }
            if (rs != null ){
                try{
                    rs.close();
                } catch (SQLException ex) { /* ignored */ }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) { /* ignored */}
             }
        } // close finally
         
         return cdelType;
    }
} // close class
